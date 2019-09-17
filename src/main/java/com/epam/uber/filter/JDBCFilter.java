package com.epam.uber.filter;


import com.epam.uber.command.CommandType;
import com.epam.uber.pool.ConnectionManager;
import com.epam.uber.utils.HttpUtils;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.EnumSet;


public class JDBCFilter implements Filter {
    private EnumSet<CommandType> commandsNeedsConn;
    private static final Logger LOGGER = Logger.getLogger(JDBCFilter.class);

    @Override
    public void init(FilterConfig fConfig) {
        commandsNeedsConn = EnumSet.range(CommandType.SHOW_TAXIS, CommandType.CANCEL_ORDER);
    }

    @Override
    public void destroy() {
    }

    private boolean needJDBC(HttpServletRequest req) {
        String command = req.getParameter("command");

        if (command == null) {
            return false;
        }
        String commandTypeValue = command.toUpperCase();
        CommandType currentType = CommandType.getCommand(commandTypeValue);
        return commandsNeedsConn.contains(currentType);

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        if (needJDBC(req)) {
            ConnectionManager connectionManager = new ConnectionManager();
            try {
                connectionManager.startTransaction();
                HttpUtils.storeConnection(request, connectionManager.getConnection());
                chain.doFilter(request, response);
                connectionManager.commitTransaction();
            } catch (Exception e) {
                LOGGER.error("Filter Exception : " + e.getMessage(), e);
                connectionManager.rollbackTransaction();
                throw new ServletException();
            } finally {
                connectionManager.close();
            }
        } else {
            chain.doFilter(request, response);
        }

    }

}
