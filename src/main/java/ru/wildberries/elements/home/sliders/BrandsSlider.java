package ru.wildberries.elements.home.sliders;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.element.Button;

@Name("Слайдер 'Популярные Бренды'")
@FindBy(xpath = ".//div[@data-qa='brands-slider']")
public class BrandsSlider extends Slider {

    @Name("Кнопка 'Смотреть всё'")
    @FindBy(xpath = ".//a[@href='/brandlist/all']/parent::div")
    public Button seeAllButton;

}
