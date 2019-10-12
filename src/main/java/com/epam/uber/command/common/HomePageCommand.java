package com.epam.uber.command.common;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;

import javax.servlet.http.HttpServletRequest;

public class HomePageCommand implements Command {

    public Page execute(HttpServletRequest request) {
        System.out.println("hiso");
        return new Page(Page.MAIN_PAGE_PATH, false);
    }

}
