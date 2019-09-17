package com.epam.uber.command.common;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.utils.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Locale;

import static com.epam.uber.utils.MessageManager.DEFAULT_LOCALE;

public class ChangeLanguageCommand implements Command {


    @Override
    public Page execute(HttpServletRequest request) {

        String localeValue = request.getParameter("locale");
        Locale locale;
        switch (localeValue) {
            case "ru":
                locale = new Locale("ru", "RU");
                break;
            case "en":
                locale = new Locale("en", "US");
                break;
            case "by":
                locale = new Locale("by", "BY");
                break;
            default:
                locale = DEFAULT_LOCALE;
        }
        Config.set(request.getSession(), Config.FMT_LOCALE, locale);
        MessageManager.changeLocale(locale);

        return new Page(Page.MAIN_PAGE_PATH, true);
    }
}
