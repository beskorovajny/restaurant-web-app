package com.october.to.finish.restaurantwebapp.command;

import com.october.to.finish.restaurantwebapp.exceptions.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExceptionCommand implements AppCommand{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        return "errorPage.jsp";
    }
}
