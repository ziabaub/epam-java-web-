
package com.epam.uber.controller;


import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.command.factory.CommandFactory;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.pool.ConnectionPool;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.uber.command.Command.MESSAGE_ATTRIBUTE;
import static com.epam.uber.utils.MessageManager.NONE_MESSAGE_KEY;

public class FrontController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(FrontController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        process(request, response);


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        process(request, response);
    }

    @Override
    public void destroy() {
        super.destroy();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        connectionPool.closePool();
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            CommandFactory commandFactory = new CommandFactory();
            Command command = commandFactory.getCommand(req);
            Page page = command.execute(req);
            boolean isRedirect = page.isRedirect();
            if (isRedirect) {
                redirect(page, req, resp);
            } else {
                forward(page, req, resp);
            }
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }


    }

    private void redirect(Page page, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = page.getPageUrl();
        response.sendRedirect(request.getContextPath() + url);
    }

    private void forward(Page page, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = page.getPageUrl();
        String messageKey = page.getMessageKey();
        if (!NONE_MESSAGE_KEY.equals(messageKey)) {
            request.setAttribute(MESSAGE_ATTRIBUTE, messageKey);
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
        requestDispatcher.forward(request, response);
    }
}
