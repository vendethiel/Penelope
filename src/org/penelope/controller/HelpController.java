package org.penelope.controller;

import org.penelope.component.IComponent;
import org.penelope.component.SayComponent;

public class HelpController extends AbstractController {
    @Override
    public IComponent perform() {
        return new SayComponent("Commands you can type:\n" +
                "help - Shows this help message.\n" +
                "{user|group|project|task|message} {create|list|delete} - Create/List/Delete a user/group/project/task.\n" +
        		"quit - Quit application.");
    }
}
