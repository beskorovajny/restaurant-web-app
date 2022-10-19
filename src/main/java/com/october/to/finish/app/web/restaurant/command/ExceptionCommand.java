package com.october.to.finish.app.web.restaurant.command;

import com.october.to.finish.app.web.restaurant.exceptions.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExceptionCommand implements AppCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        return "error.jsp";
    }
}
