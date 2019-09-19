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
import com.epam.uber.utils.PasswordEncoder;
import com.epam.uber.utils.UserDataValidator;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.epam.uber.utils.MessageManager.*;

public class RegisterCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(RegisterCommand.class);


    @Override
    public Page execute(HttpServletRequest request) {

        AuthorizationServiceImpl authorizationService = new AuthorizationServiceImpl();
        try {
            String confirmationCode = request.getParameter("confirmation_code");
            Optional<Author> authorization = authorizationService.contains(confirmationCode);
            if (!authorization.isPresent()) {
                return new Page(Page.REGISTER_PAGE_PATH, false, REGISTRATION_UNSUCCESSFUL_MESSAGE_KEY);
            }

            Author author = authorization.get();
            Optional<User> user = buildUser(request, author);
            if (!user.isPresent()) {
                return new Page(Page.REGISTER_PAGE_PATH, false, REGISTRATION_UNSUCCESSFUL_MESSAGE_KEY);
            }

            User u = user.get();
            boolean isAdmin = u.getUserRole();
            if (!isAdmin) {
                createTaxi(u.getId());
            }

            authorizationService.delete(authorization.get().getId());
            return new Page(Page.LOGIN_PAGE_PATH, false, SUCCESS_MESSAGE_KEY);
        } catch (ServiceException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return new Page(Page.REGISTER_PAGE_PATH, true, REGISTRATION_UNSUCCESSFUL_MESSAGE_KEY);
        } finally {
            authorizationService.endService();
        }
    }

    private Optional<User> buildUser(HttpServletRequest request, Author author) throws ServiceException {
        UserServiceImpl userService = new UserServiceImpl();
        try {
            String firstName = request.getParameter("firstname");
            String lastName = request.getParameter("lastname");
            String login = request.getParameter(LOGIN_PARAMETER);
            String password = request.getParameter(PASSWORD_PARAMETER);
            String email = request.getParameter("email");
            boolean isAdmin = author.getRole();
            boolean isUserDataValid = isUserDataValid(firstName, lastName, login, password, email);
            boolean isUnique = userService.contains(login);
            if (!isUserDataValid || !isUnique) {
                return Optional.empty();
            }
            password = PasswordEncoder.encode(password);
            User newUser = new User(firstName, lastName, login, password, email, isAdmin);
            int id = userService.insert(newUser);
            newUser.setId(id);
            return Optional.of(newUser);
        } finally {
            userService.endService();
        }
    }

    private void createTaxi(int id) throws ServiceException {
        TaxiServiceImpl taxiService = new TaxiServiceImpl();
        try {
            Taxi taxi = new Taxi();
            taxi.setId(id);
            taxi.setLocationId(-1);
            taxi.setStatus(false);
            taxiService.insert(taxi);
        } finally {
            taxiService.endService();
        }
    }

    private boolean isUserDataValid(String firstName, String lastName, String login, String password, String email) {
        UserDataValidator userDataValidator = new UserDataValidator();
        return userDataValidator.checkData(firstName, lastName, login, password, email);
    }


}

