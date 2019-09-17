package com.epam.uber.command.admin;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;

import javax.servlet.http.HttpServletRequest;

import static com.epam.uber.command.Page.ADD_TAXI_PAGE_PATH;

public class AddTaxiCommand implements Command {

    @Override
    public Page execute(HttpServletRequest request) {
        return  new Page(ADD_TAXI_PAGE_PATH , false);
    }

}
