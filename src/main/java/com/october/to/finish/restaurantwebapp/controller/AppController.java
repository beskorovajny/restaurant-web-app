package com.october.to.finish.restaurantwebapp.controller;

import com.october.to.finish.restaurantwebapp.command.AppCommand;
import com.october.to.finish.restaurantwebapp.command.CommandContainer;
import com.october.to.finish.restaurantwebapp.exceptions.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "appController", value = "/home")
public class AppController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(AppController.class);
    private CommandContainer commands;

    @Override
    public void init(ServletConfig config) {
        commands = (CommandContainer) config.getServletContext().getAttribute("commandContainer");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = null;
        try {
            url = getUrl(req,resp);
        } catch (CommandException e) {
            LOGGER.debug("Error: {}", e);
            resp.sendError(500, "Can`t process the command");
        }
        req.getRequestDispatcher(url).forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String url = null;
        try {
            url = getUrl(req, resp);
        } catch (CommandException e) {
            LOGGER.error("[: {}]", e);
            resp.sendError(500, "Can`t process the command");
        }
        resp.sendRedirect(url);
    }

    public String getUrl(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        String commandName = req.getParameter("command");
        AppCommand actionCommand = CommandContainer.getCommand(commandName);
        return actionCommand.execute(req, resp);
    }
}