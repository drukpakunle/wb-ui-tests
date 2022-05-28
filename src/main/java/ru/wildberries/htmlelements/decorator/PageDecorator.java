package ru.wildberries.htmlelements.decorator;

import ru.wildberries.htmlelements.utils.HtmlElementUtils;
import ru.wildberries.pages.interfaces.IPage;

public final class PageDecorator {

    public static Object decorate(Object pageObject) {
        if (pageObject instanceof IPage) {
            Class<IPage> pageClass = (Class<IPage>) pageObject.getClass();
            IPage page = (IPage) pageObject;

            String pageName = HtmlElementUtils.getPageName(pageClass);
            String locator = HtmlElementUtils.getRootLocator(pageClass);

            page.setName(pageName);
            page.setLocator(locator);
            return page;
        }
        return pageObject;
    }

}
