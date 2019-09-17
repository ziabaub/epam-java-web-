package com.epam.uber.command.common;


import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.entity.user.Author;
import com.epam.uber.entity.user.Taxi;
import com.epam.uber.entity.user.User;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.impl.AuthorizationServiceImpl;
import com.epam.uber.service.impl.TaxiServiceImpl;
import com.epam.uber.service.impl.UserServiceImpl;
import com.epam.uber.utils.HttpUtils;
import com.epam.uber.utils.PasswordEncoder;
import com.epam.uber.utils.UserDataValidator;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.Optional;

import static com.epam.uber.utils.MessageManager.*;

public class RegisterCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(RegisterCommand.class);
    private UserServiceImpl userService;
    private AuthorizationServiceImpl authorizationService;
    private TaxiServiceImpl taxiService;


    @Override
    public Page execute(HttpServletRequest request) {

        try {
            init(request);
            String firstName = request.getParameter("firstname");
            String lastName = request.getParameter("lastname");
            String login = request.getParameter(LOGIN_PARAMETER);
            String password = request.getParameter(PASSWORD_PARAMETER);
            String email = request.getParameter("email");
            String confirmationCode = request.getParameter("confirmation_code");

            boolean isUserDataValid = isUserDataValid(firstName, lastName, login, password, email);
            Optional<Author> authorization = authorizationService.contains(confirmationCode);
            boolean isUnique = userService.contains(login);

            if (!authorization.isPresent() || !isUnique || !isUserDataValid) {
                return new Page(Page.REGISTER_PAGE_PATH, false, REGISTRATION_UNSUCCESSFUL_MESSAGE_KEY);
            }
            boolean isAdmin = authorization.get().getRole();
            handleUser(firstName, lastName, login, password, email, isAdmin);
            authorizationService.delete(authorization.get().getId());
            return new Page(Page.LOGIN_PAGE_PATH, false, REGISTRATION_SUCCESSFUL_MESSAGE_KEY);
        } catch (ServiceException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return new Page(Page.REGISTER_PAGE_PATH,true,REGISTRATION_UNSUCCESSFUL_MESSAGE_KEY);
        }
    }

    private void handleUser(String firstName, String lastName, String login, String password, String email, boolean isAdmin) throws ServiceException {
        password = PasswordEncoder.encode(password);
        User newUser = new User(firstName, lastName, login, password, email, isAdmin);
        int id = userService.insert(newUser);
        if (!isAdmin) {
            handleTaxi(id);
        }
    }

    private void handleTaxi(int id) throws ServiceException {
        Taxi taxi = new Taxi();
        taxi.setId(id);
        taxi.setLocationId(-1);
        taxi.setStatus(false);
        taxiService.insert(taxi);
    }

    private boolean isUserDataValid(String firstName, String lastName, String login, String password, String email) {
        UserDataValidator userDataValidator = new UserDataValidator();
        return userDataValidator.checkData(firstName, lastName, login, password, email);
    }

    private void init(HttpServletRequest request) {
        Connection connection = HttpUtils.getStoredConnection(request);
        this.userService = new UserServiceImpl(connection);
        this.authorizationService = new AuthorizationServiceImpl(connection);
        this.taxiService = new TaxiServiceImpl(connection);
    }


}

