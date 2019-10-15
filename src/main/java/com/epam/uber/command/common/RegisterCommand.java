package com.epam.uber.command.common;


import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.entity.user.User;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.UserServiceImpl;
import com.epam.uber.utils.PasswordEncoder;
import com.epam.uber.utils.UserDataValidator;

import javax.servlet.http.HttpServletRequest;

import static com.epam.uber.command.Page.LOGIN_PAGE_PATH;
import static com.epam.uber.command.Page.REGISTER_PAGE_PATH;
import static com.epam.uber.utils.MessageManager.SUCCESS_MESSAGE_KEY;
import static com.epam.uber.utils.MessageManager.UNSUCCESSFUL_MESSAGE_KEY;

public class RegisterCommand implements Command {

    @Override
    public Page execute(HttpServletRequest request) throws ServiceException {
        UserServiceImpl userService = new UserServiceImpl();
        User user = buildUser(request);
        if (user == null) {
            return new Page(REGISTER_PAGE_PATH, false, UNSUCCESSFUL_MESSAGE_KEY);
        }

        boolean loginIsUnique = userService.isLoginAvailable(user.getLogin());
        if (!loginIsUnique) {
            return new Page(REGISTER_PAGE_PATH, false, "user already exist");
        }

        boolean emailIsUnique = userService.isEmailAvailable(user.getEmail());
        if (!emailIsUnique) {
            return new Page(REGISTER_PAGE_PATH, false, "email already registered");
        }

        boolean phoneIsUnique = userService.isPhoneAvailable(user.getPhone());
        if (!phoneIsUnique) {
            return new Page(REGISTER_PAGE_PATH, false, "phone already exist");
        }

        userService.registerUser(user);
        return new Page(LOGIN_PAGE_PATH, true, SUCCESS_MESSAGE_KEY);

    }

    private User buildUser(HttpServletRequest request) {
        User user = new User();
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String login = request.getParameter(LOGIN_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String role = request.getParameter("role");
        boolean isUserDataValid = isUserDataValid(firstName, lastName, login, password, email);
        if (!isUserDataValid) {
            return null;
        }
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setLogin(login);
        user.setPassword(PasswordEncoder.encode(password));
        user.setEmail(email);
        user.setPhone(phone);
        user.setRole(role);
        user.setStatus("not active");
        return user;
    }

    private boolean isUserDataValid(String firstName, String lastName, String login, String password, String email) {
        UserDataValidator userDataValidator = new UserDataValidator();
        return userDataValidator.checkData(firstName, lastName, login, password, email);
    }


}

