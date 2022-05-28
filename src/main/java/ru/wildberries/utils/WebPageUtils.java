package ru.wildberries.utils;

import org.openqa.selenium.WebDriver;
import ru.wildberries.config.env.DefaultUserEnvironmentPool;
import ru.wildberries.enums.site.SiteClient;
import ru.wildberries.enums.site.SiteCookie;
import ru.wildberries.exceptions.UnknownStateException;
import ru.wildberries.utils.strings.StringUtils;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public final class WebPageUtils {

    /**
     * get ClipBoard text
     *
     * @return ClipBoard text without BOM
     */
    public String getClipBoardText() {
        try {
            String text = (String) Toolkit.getDefaultToolkit()
                    .getSystemClipboard()
                    .getData(DataFlavor.stringFlavor);

            return StringUtils.clearBom(text);
        } catch (HeadlessException | UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * clean ClipBoard
     */
    public void cleanClipBoard() {
        StringSelection stringSelection = new StringSelection(StringUtils.EMPTY);
        Toolkit.getDefaultToolkit()
                .getSystemClipboard()
                .setContents(stringSelection, null);
    }

    /**
     * Switch to mobile version
     */
    public void switchToMobileVersion(WebDriver webDriver) {
        DefaultUserEnvironmentPool.getInstance().get().siteClient = SiteClient.MOBILE;
        new CookiesUtils().setCookie(SiteCookie.MOBILE_CLIENT.cookie, webDriver);
    }

    /**
     * Switch to desktop version
     */
    public void switchToDesktopVersion(WebDriver webDriver) {
        DefaultUserEnvironmentPool.getInstance().get().siteClient = SiteClient.DESKTOP;
        new CookiesUtils().setCookie(SiteCookie.DESKTOP_CLIENT.cookie, webDriver);
    }

    public void switchTo(SiteClient client, WebDriver webDriver) {
        switch (client) {
            case MOBILE:
                switchToMobileVersion(webDriver);
                break;
            case DESKTOP:
                switchToDesktopVersion(webDriver);
                break;
            default:
                throw new UnknownStateException("No SiteClient: " + client);
        }
    }

}
