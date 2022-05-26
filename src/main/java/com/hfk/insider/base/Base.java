package com.hfk.insider.base;

import org.openqa.selenium.support.PageFactory;

public class Base {
    public <TPage extends BasePage> TPage GetInstance(Class<TPage> page) {
        Object obj = PageFactory.initElements(LocalDriverContext.getWebDriver(), page);
        return page.cast(obj);
    }
}
