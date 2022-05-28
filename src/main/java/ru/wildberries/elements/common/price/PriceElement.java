package ru.wildberries.elements.common.price;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.config.TestConfig;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.element.HtmlElement;
import ru.wildberries.models.Price;
import ru.wildberries.utils.parsers.PriceParser;

@Slf4j
@Name("Цена - сумма с валютой")
@FindBy(xpath = ".")
public class PriceElement extends HtmlElement {

    private String amountAndCurrencyAsText;

    public PriceElement() {
        // Default constructor is needed to implicitly initialize proxy elements
        super();
    }

    public PriceElement(String amountAndCurrencyAsText) {
        this.amountAndCurrencyAsText = amountAndCurrencyAsText;
    }

    public Price getPrice() {
        String amountAndCurrencyAsText = getAmountAndCurrencyAsText();
        return new PriceParser(TestConfig.environment.locale).parse(amountAndCurrencyAsText);
    }

    private String getAmountAndCurrencyAsText() {
        String amountAndCurrency = StringUtils.isNoneEmpty(amountAndCurrencyAsText)
                ? amountAndCurrencyAsText
                : this.getText();

        log.info("Parsed amount and currency: '{}'", amountAndCurrency);
        return amountAndCurrency.replaceAll("\\u00A0", "");
    }

}
