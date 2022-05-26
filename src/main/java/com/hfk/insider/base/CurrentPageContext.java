package com.hfk.insider.base;

public class CurrentPageContext {
    private static final ThreadLocal<BasePage> localCurrentPage = new ThreadLocal<>();

    public static BasePage getCurrentPage() {
        return localCurrentPage.get();
    }

    public static void setCurrentPage(BasePage driverThreadLocal) {
        localCurrentPage.set(driverThreadLocal);
    }
}
