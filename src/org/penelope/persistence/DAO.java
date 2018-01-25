package org.penelope.persistence;

import org.penelope.di.AutoInject;
import org.penelope.meta.FieldFetcher;
import org.penelope.model.DomainObject;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class DAO<Model extends DomainObject> {
    private Class modelClass;
    private Table tableAnn;
    private FieldFetcher<DBField> dbFields;
    private AppConnection cnt;

    public void setConnection(AppConnection cnt) {
        this.cnt = cnt;
    }

    public DAO(Class modelClass) {
        this.modelClass = modelClass;
        tableAnn = (Table) modelClass.getAnnotation(Table.class);
        dbFields = new FieldFetcher<>(modelClass, DBField.class);
    }

    public void save(Model model) {
        Set<Map.Entry<Field, DBField>> entries = dbFields.get().entrySet();

        long fieldsNum = entries.size() - entries.stream().filter(e -> e.getValue().isPk()).count();
        String sql = "INSERT INTO " +
                tableAnn.tableName() +
                " (" +
                commaSepFields(entries, false) +
                ") VALUES (" +
                String.join(", ", Collections.nCopies((int) fieldsNum, "?")) +
                ");";

        executeWithBindings(model, sql, entries);
    }

    public boolean delete(int id) {
        Optional<Map.Entry<Field, DBField>> pk = dbFields.get().entrySet()
                .stream()
                .filter(e -> e.getValue().isPk())
                .findFirst();
        if (pk.isPresent()) {
            String sql = "DELETE FROM " + tableAnn.tableName()
                    + " WHERE " + getFieldName(pk.get()) + " = " + id;
            Statement stmt = null;
            try {
                stmt = cnt.get().createStatement();
                stmt.execute(sql);
                return stmt.getUpdateCount() > 0;
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }

    public List<Model> load() {
        Set<Map.Entry<Field, DBField>> entries = dbFields.get().entrySet();
        try {
            Statement stmt = cnt.get().createStatement();
            String sql = "SELECT " + commaSepFields(entries, true) + " FROM " + tableAnn.tableName();
            ResultSet rs = stmt.executeQuery(sql);

            List<Model> result = new LinkedList<>();
            /* resultSet starts before 1st data */
            while (rs.next()) {
                int columnIdx = 0;
                Model inst = (Model) modelClass.newInstance();
                for (Map.Entry<Field, DBField> entry : entries) {
                    if (entry.getValue().isPk()) {
                        entry.getKey().set(inst, rs.getInt(++columnIdx));
                    } else {
                        entry.getKey().set(inst, rs.getString(++columnIdx));
                    }
                }
                result.add(inst);
            }
            stmt.close();
            return result;
        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to create a LOAD statement");
        }
    }

    private String commaSepFields(Set<Map.Entry<Field, DBField>> entries, boolean withPk) {
        return entries
                .stream()
                .filter(e -> withPk || !e.getValue().isPk())
                .map(this::getFieldName)
                .collect(joining(", "));
    }

    private String getFieldName(Map.Entry<Field, DBField> entry) {
        if (entry.getValue().columnName().equals("")) {
            return entry.getKey().getName();
        }
        return entry.getValue().columnName();
    }

    private void executeWithBindings(Model model, String sql, Set<Map.Entry<Field, DBField>> entries) {
        try {
            PreparedStatement stmt = cnt.get().prepareStatement(sql);

            int idx = 0;
            for (Map.Entry<Field, DBField> entry : entries) {
                if (entry.getValue().isPk()) {
                    /* NEVER bind PK */
                    continue;
                }
                stmt.setString(++idx, (String) entry.getKey().get(model));
            }
            stmt.execute();
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to create a statement: " + sql);
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface DBField {
        String columnName() default "";

        boolean isPk() default false;
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Table {
        String tableName() default "";
    }
}
