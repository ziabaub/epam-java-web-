package com.epam.uber.command;

import static com.epam.uber.utils.MessageManager.NONE_MESSAGE_KEY;

public class Page {

    /**
     * Common pages.
     */
    public static final String LOGIN_PAGE_PATH = "/pages/common/login.jsp";
    public static final String MAIN_PAGE_PATH = "/pages/common/main.jsp";
    public static final String REGISTER_PAGE_PATH = "/pages/common/register.jsp";

    /**
     * taxi pages
     */
    public static final String DISPATCHER_PAGE_PATH = "/pages/taxi/dispatcher.jsp";
    public static final String HISTORY_PAGE_PATH = "/pages/taxi/history.jsp";

    /**
     *client pages
     */
    public static final String ACCEPTED_CLIENT_PAGE_PATH = "/pages/client/orderAccepted.jsp";

    /**
     *admin pages
     */
    public static final String TAXIS_LIST_PAGE_PATH = "/pages/admin/taxisList.jsp";
    public static final String EDIT_RATE_PAGE_PATH = "/pages/admin/editRate.jsp";
    public static final String RATE_HISTORY_PAGE_PATH = "/pages/admin/rateHistory.jsp";

    private final String pageUrl;
    private final boolean isRedirect;
    private final String messageKey;

    public Page(String pageUrl, boolean isRedirect) {
        this.pageUrl = pageUrl;
        this.isRedirect = isRedirect;
        this.messageKey = NONE_MESSAGE_KEY;
    }

    /**
     * Instantiates a new Page.
     */

    public Page(String pageUrl, boolean isRedirect, String messageKey) {
        this.pageUrl = pageUrl;
        this.isRedirect = isRedirect;
        this.messageKey = messageKey;
    }

    public String getPageUrl() {
        return pageUrl;
    }


    public boolean isRedirect() {
        return isRedirect;
    }

    public String getMessageKey() {
        return messageKey;
    }

}
