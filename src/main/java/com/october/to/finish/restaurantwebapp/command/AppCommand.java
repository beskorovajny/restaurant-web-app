package com.october.to.finish.restaurantwebapp.command;

import com.october.to.finish.restaurantwebapp.exceptions.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AppCommand {
    String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;
}
