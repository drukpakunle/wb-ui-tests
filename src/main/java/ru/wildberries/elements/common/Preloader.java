package ru.wildberries.elements.common;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.annotations.Timeout;
import ru.wildberries.htmlelements.element.HtmlElement;
import ru.wildberries.htmlelements.element.TypifiedElement;

@Timeout(waitSeconds = 0)
@Name("Preloader")
@FindBy(xpath = "//div[@data-qa='page-loader' and not(@class)]")
public class Preloader extends HtmlElement {

    @Timeout(waitSeconds = 0)
    @Name("Спиннер загрузки")
    @FindBy(xpath = "./*")
    private TypifiedElement spinner;

    public void waitStop() {
        spinner.waitDisappearance();
    }
}
