package com.epam.uber.command;

import static com.epam.uber.utils.MessageManager.NONE_MESSAGE_KEY;

public class Page {

    /**
     * Common pages.
     */
    public static final String LOGIN_PAGE_PATH = "/pages/common/login.jsp";
    public static final String ERROR_PAGE_PATH = "/pages/common/error.jsp";
    public static final String MAIN_PAGE_PATH = "/pages/common/main.jsp";
    public static final String REGISTER_PAGE_PATH = "/pages/common/register.jsp";

    /**
     * taxi pages
     */
    public static final String PROFILE_TAXI_PAGE_PATH = "/pages/taxi/profile.jsp";
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
    public static final String ADD_TAXI_PAGE_PATH = "/pages/admin/addTaxi.jsp";

    private String pageUrl;
    private boolean isRedirect;
    private String messageKey;

    public Page(String pageUrl, boolean isRedirect) {
        this.pageUrl = pageUrl;
        this.isRedirect = isRedirect;
        this.messageKey = NONE_MESSAGE_KEY;
    }

    /**
     * Instantiates a new Page.
     */
    public Page() {
    }

    public Page(String pageUrl, boolean isRedirect, String messageKey) {
        this.pageUrl = pageUrl;
        this.isRedirect = isRedirect;
        this.messageKey = messageKey;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public boolean isRedirect() {
        return isRedirect;
    }

    public void setRedirect(boolean redirect) {
        isRedirect = redirect;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }
}
