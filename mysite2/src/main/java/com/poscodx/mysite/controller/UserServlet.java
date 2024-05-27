package com.poscodx.mysite.controller;

import com.poscodx.mysite.controller.action.main.MainAction;
import com.poscodx.mysite.controller.action.user.*;

import java.io.Serial;
import java.util.Map;

public class UserServlet extends ActionServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    private Map<String, Action> mapAction = Map.of(
            "joinform", new JoinFormAction(),
            "join", new JoinAction(),
            "joinsuccess", new JoinSuccess(),
            "loginform", new LoginFormAction(),
            "login", new LoginAction(),
            "logout", new LogoutAction(),
            "updateform", new UpdateFormAction(),
            "update", new UpdateAction()
    );

    @Override
    protected Action getAction(String actionName) {
        return mapAction.getOrDefault(actionName, new MainAction());
    }
}
