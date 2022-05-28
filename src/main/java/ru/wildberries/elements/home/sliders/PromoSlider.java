package ru.wildberries.elements.home.sliders;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;

@Name("Слайдер промо-акций на главной")
@FindBy(xpath = "(.//div[contains(@data-qa, 'promo-slider')])[1]")
public class PromoSlider extends Slider {

}
