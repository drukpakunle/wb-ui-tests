package ru.wildberries.elements.home.sliders;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.element.HtmlElement;

import java.util.List;

@Name("Слайдер")
@FindBy(xpath = ".")
public class Slider extends HtmlElement {

    @Name("Активный слайд")
    @FindBy(xpath = ".//div[contains(@class, 'swiper-slide-active')]")
    public SwipeSlide activeSwipeSlide;

    @Name("Следующий слайд")
    @FindBy(xpath = ".//div[contains(@class, 'swiper-slide-next')]")
    public SwipeSlide nextSwipeSlide;

    @Name("Предыдущий слайд")
    @FindBy(xpath = ".//div[contains(@class, 'swiper-slide-prev')]")
    public SwipeSlide previousSwipeSlide;

    @Name("Слайды")
    @FindBy(xpath = ".//div[contains(@class, 'swiper-slide')]")
    public List<SwipeSlide> slides;

}
