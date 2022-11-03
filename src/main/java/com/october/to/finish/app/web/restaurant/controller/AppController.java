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

@WebServlet(name = "appController", value = "/controller")
public class AppController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(AppController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = null;
        try {
            url = getUrl(request, response);
            LOGGER.info("[AppController-doGet] URL processed");
        } catch (CommandException | FatalApplicationException e) {
            LOGGER.error("An exception occurs: {}, req encoding: {}", e.getMessage(), request.getCharacterEncoding());
            response.sendError(500, "Can't process command");
        }
        request.getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = null;
        try {
            url = getUrl(request, response);
            LOGGER.info("[AppController-doPost] URL processed [{}], resp encoding: {}", url,
                    response.getCharacterEncoding());
        } catch (CommandException | FatalApplicationException e) {
            LOGGER.error("An exception occurs: {}", e.getMessage());
            response.sendError(500, "Can't process the command");
        }
        response.sendRedirect(url);
    }

    public String getUrl(HttpServletRequest req, HttpServletResponse resp) throws CommandException, FatalApplicationException {
        String commandName = req.getParameter("command");
        AppCommand appCommand = CommandContainer.getCommand(commandName);
        return appCommand.execute(req, resp);
    }
}