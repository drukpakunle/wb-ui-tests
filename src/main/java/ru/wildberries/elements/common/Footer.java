package ru.wildberries.elements.common;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.annotations.Timeout;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.htmlelements.element.HtmlElement;
import ru.wildberries.htmlelements.element.TextBlock;

@Name("Подвал")
@FindBy(xpath = "//footer")
public class Footer extends HtmlElement {

    @Name("Кнопка 'Основная версия сайта'")
    @FindBy(xpath = ".//div[@data-qa='footer-desktop-version']")
    public Button desktopSiteButton;

    @Name("Кнопка App Store")
    @FindBy(xpath = ".//a[@data-qa='footer-link-ios-app']")
    public Button appStoreButton;

    @Name("Кнопка Google Play")
    @FindBy(xpath = ".//a[@data-qa='footer-link-android-app']")
    public Button googlePlayButton;

    @Name("Кнопка Huawei App Gallery")
    @FindBy(xpath = ".//a[@data-qa='footer-link-huawei-app']")
    public Button huaweiAppGalleryButton;

    public FooterMenu footerMenu;

    @Name("Кнопка выбора города")
    @FindBy(xpath = ".//a[@data-qa='footer-geo-location-link']")
    public Button selectCityButton;

    @Name("Копирайт")
    @FindBy(xpath = ".//div[@data-qa='footer-copyright']")
    public TextBlock copyrightTextBlock;

}
