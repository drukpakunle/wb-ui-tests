package ru.wildberries.pages.home;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.elements.catalog.ProductGrid;
import ru.wildberries.elements.catalog.ProductGridItem;
import ru.wildberries.elements.common.StickyFooter;
import ru.wildberries.elements.home.sliders.BrandsSlider;
import ru.wildberries.elements.home.sliders.PromoSlider;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.builders.PageBuilder;
import ru.wildberries.htmlelements.builders.WebElementBuilder;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.models.catalog.CatalogItem;
import ru.wildberries.pages.ActionBarPage;
import ru.wildberries.pages.BrandsPage;
import ru.wildberries.pages.catalog.CatalogDetailPage;

import java.util.List;
import java.util.stream.Collectors;

@Name("Главная")
@FindBy(xpath = "//div[@data-qa='home-page']")
public class HomePage extends ActionBarPage {

    private PromoSlider promoSlider;

    @Name("Хиты продаж")
    @FindBy(xpath = ".//div[@data-qa='personal-goods-products-list']/div[1]")
    private ProductGrid bestsellers;

    @Name("Кнопка 'Прокрутка вверх'")
    @FindBy(xpath = "(//footer/preceding-sibling::div[1]//div[@role='button'])[1]")
    private Button scrollToTopButton;

    private BrandsSlider brandsSlider;
    private StickyFooter stickyFooter;

    @Override
    public boolean isPageOpen() {
        WebElement rootElement = WebElementBuilder.build(HomePage.class);
        return rootElement.isDisplayed();
    }

    @Step("Открыть первый товар из списка Хиты Продаж")
    public CatalogDetailPage openFirstBestsellersItem() {
        return bestsellers.scrollToElement().openFirstProduct();
    }

    @Step("Открыть товар №{productNumber} из списка Хиты Продаж")
    public CatalogDetailPage openBestsellersItem(int productNumber) {
        return bestsellers.openProduct(productNumber);
    }

    @Step("Получить коллекцию карточек товара блока Хиты Продаж")
    public List<ProductGridItem> getBestsellersProductList() {
        listElementsUtils().waitForListNotEmpty(bestsellers.productGridItems);
        return bestsellers.productGridItems;
    }

    @Step("Получить коллекцию карточек товара блока Хиты Продаж")
    public List<CatalogItem> getBestsellersCatalogItemList() {
        return getBestsellersProductList().stream()
                .map(ProductGridItem::getCatalogItem)
                .collect(Collectors.toList());
    }

    @Step("Перейти на страницу 'Популярные Бренды'")
    public BrandsPage navigateToBrandsPage() {
        brandsSlider.seeAllButton.click();
        return PageBuilder.build(BrandsPage.class);
    }

    @Step("Прокрутить страницу вверх (в начало)")
    public void scrollToTop() {
        scrollToTopButton.jsClick();
    }

    public boolean isScrollToTopButtonExist() {
        return scrollToTopButton.exists();
    }

}
