package com.epam.uber.filter;

import com.epam.uber.command.CommandType;
import com.epam.uber.command.Page;
import com.epam.uber.entity.user.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumSet;

import static com.epam.uber.command.Command.USER_ATTRIBUTE;

public class CommandFilter implements Filter {
    private final EnumSet<CommandType> adminCommand = EnumSet.range(CommandType.SHOW_TAXIS, CommandType.RATE_HISTORY);
    private final EnumSet<CommandType> commonCommand = EnumSet.range(CommandType.COMMON_LOGIN, CommandType.COMMON_CHANGE_LANGUAGE);
    private final EnumSet<CommandType> taxiCommand = EnumSet.range(CommandType.DISPATCHER, CommandType.HISTORY);
    private final EnumSet<CommandType> clientCommand = EnumSet.range(CommandType.CLIENT_ORDER, CommandType.CANCEL_ORDER);

    @Override
    public void init(FilterConfig fConfig) {

    }

    @Override
    public void destroy() {

    }

    private boolean isAuthorized(HttpServletRequest req) {
        String command = req.getParameter("command");
        if (command == null){
            return true;
        }
        String commandTypeValue = command.toUpperCase();
        CommandType currentType = CommandType.getCommand(commandTypeValue);
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        if (commonCommand.contains(currentType)) {
            return true;
        }
        assert user != null;
        String role = user.getRole();
        if ("admin".equals(role) && adminCommand.contains(currentType)) {
            return true;
        }
        if ("taxi".equals(role) && taxiCommand.contains(currentType)) {
            return true;
        }
        return "client".equals(role) && clientCommand.contains(currentType);

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        if (isAuthorized(req)) {
            chain.doFilter(request, response);
        }else {
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.sendRedirect(req.getContextPath() + Page.MAIN_PAGE_PATH);
        }
    }
}
