package com.october.to.finish.app.web.restaurant.controller;

import com.october.to.finish.app.web.restaurant.command.AppCommand;
import com.october.to.finish.app.web.restaurant.command.CommandContainer;
import com.october.to.finish.app.web.restaurant.exceptions.CommandException;
import com.october.to.finish.app.web.restaurant.exceptions.FatalApplicationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "appController", value = "/home")
public class AppController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(AppController.class);
    private CommandContainer commandContainer;
    @Override
    public void init(ServletConfig config) {
        commandContainer = (CommandContainer) config.getServletContext().getAttribute("commandContainer");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = null;
        try {
            url = getUrl(req, resp);
        } catch (CommandException | FatalApplicationException e) {
            LOGGER.error("An exception occurs: {}", e.getMessage());
            resp.sendError(500, "Can't process command");
        }
        req.getRequestDispatcher(url).forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = null;
        try {
            url = getUrl(req, resp);
        } catch (CommandException | FatalApplicationException e) {
            LOGGER.error("An exception occurs: {}", e.getMessage());
            resp.sendError(500, "Can`t process the command");
        }
        resp.sendRedirect(url);
    }

    public String getUrl(HttpServletRequest req, HttpServletResponse resp) throws CommandException, FatalApplicationException {
        String commandName = req.getParameter("command");
        AppCommand actionCommand = CommandContainer.getCommand(commandName);
        return actionCommand.execute(req, resp);
    }
}