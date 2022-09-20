package com.october.to.finish.restaurantwebapp.command;

import com.october.to.finish.restaurantwebapp.exceptions.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LanguageCommand implements AppCommand{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        session.setAttribute("locale", request.getParameter("locale"));
        return "home?command=" + request.getParameter("pageToTranslate");
    }
}
