package com.kakura.libraryproject.controller;

import com.kakura.libraryproject.controller.command.*;
import com.kakura.libraryproject.exception.CommandException;
import com.kakura.libraryproject.model.pool.ConnectionPool;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

@WebServlet(name = "controller", urlPatterns = {"/controller", "*.do"})
public class Controller extends HttpServlet {

    private static final Logger logger = LogManager.getLogger();

//    public void init() {
//        ConnectionPool.getInstance();
//        logger.info("----> Servlet Init: " + this.getServletInfo());
//    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String commandType = request.getParameter(RequestParameter.COMMAND);
            Command command = CommandType.defineCommand(commandType);
            Router router = null;
            router = command.execute(request);
            switch (router.getType()) {
                case FORWARD -> request.getRequestDispatcher(router.getPage()).forward(request, response);
                case REDIRECT -> response.sendRedirect(request.getContextPath() + router.getPage());
                default -> request.getRequestDispatcher(PagePath.ERROR_404).forward(request, response);
            }
        } catch (CommandException e) {
            logger.error("Internal error has occurred: ", e);
            response.sendError(SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

//    @Override
//    public void destroy() {
//        ConnectionPool.getInstance().destroyPool();
//        logger.info("----> Servlet Destroyed: " + this.getServletName());
//        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
//            try {
//                DriverManager.deregisterDriver(driver);
//            } catch (SQLException e) {
//                //todo log
//                e.printStackTrace();
//            }
//        });
//        logger.info("----> Drivers deregistered");
//    }
}