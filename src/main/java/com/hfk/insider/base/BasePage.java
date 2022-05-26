package com.hfk.insider.base;

public abstract class BasePage extends Base {
    public <TPage extends com.hfk.insider.base.BasePage> TPage As(Class<TPage> pageInstance) {
        try {
            return (TPage) this;
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }
}
