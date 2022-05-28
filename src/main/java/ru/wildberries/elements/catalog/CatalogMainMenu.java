package ru.wildberries.elements.catalog;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.htmlelements.element.HtmlElement;

@Name("Элементы меню верхнего уровня каталога")
@FindBy(xpath = "//div[@data-qa='catalog-menu-list-item-level-0']/..")
public class CatalogMainMenu extends HtmlElement {

    @Name("Женщинам")
    @FindBy(xpath = ".//div[@data-qa='catalog-menu-list-item-level-0'][1]")
    private Button womenMenuItem;

    @Name("Мужчинам")
    @FindBy(xpath = ".//div[@data-qa='catalog-menu-list-item-level-0'][2]")
    private Button menMenuItem;

    @Name("Детям")
    @FindBy(xpath = ".//div[@data-qa='catalog-menu-list-item-level-0'][3]")
    private Button childrenMenuItem;

    @Name("Обувь")
    @FindBy(xpath = ".//div[@data-qa='catalog-menu-list-item-level-0'][4]")
    private Button shoesMenuItem;

    @Name("Аксессуары")
    @FindBy(xpath = ".//div[@data-qa='catalog-menu-list-item-level-0-show-all'][1]")
    private Button accessoriesMenuItem;

    @Name("Premium")
    @FindBy(xpath = ".//div[@data-qa='catalog-menu-list-item-level-0'][5]")
    private Button premiumMenuItem;

    @Name("Книги")
    @FindBy(xpath = ".//div[@data-qa='catalog-menu-list-item-level-0'][6]")
    private Button booksMenuItem;

    @Name("Канцтовары")
    @FindBy(xpath = ".//div[@data-qa='catalog-menu-list-item-level-0'][7]")
    private Button stationeryMenuItem;

    @Name("Зоотовары")
    @FindBy(xpath = ".//div[@data-qa='catalog-menu-list-item-level-0'][8]")
    private Button petsMenuItem;

    @Name("Спорт")
    @FindBy(xpath = ".//div[@data-qa='catalog-menu-list-item-level-0'][9]")
    private Button sportMenuItem;

    @Name("Красота")
    @FindBy(xpath = ".//div[@data-qa='catalog-menu-list-item-level-0'][10]")
    private Button beautyMenuItem;

    @Name("Игрушки")
    @FindBy(xpath = ".//div[@data-qa='catalog-menu-list-item-level-0'][11]")
    private Button toysMenuItem;

    @Name("Продукты")
    @FindBy(xpath = ".//div[@data-qa='catalog-menu-list-item-level-0'][12]")
    private Button productsMenuItem;

    @Name("Здоровье")
    @FindBy(xpath = ".//div[@data-qa='catalog-menu-list-item-level-0'][13]")
    private Button healthMenuItem;

    @Name("Электроника")
    @FindBy(xpath = ".//div[@data-qa='catalog-menu-list-item-level-0'][14]")
    private Button electronicsMenuItem;

    @Name("Бытовая техника")
    @FindBy(xpath = ".//div[@data-qa='catalog-menu-list-item-level-0'][15]")
    private Button appliancesMenuItem;

    @Name("Для ремонта")
    @FindBy(xpath = ".//div[@data-qa='catalog-menu-list-item-level-0'][16]")
    private Button forRepairMenuItem;

    @Name("Дом")
    @FindBy(xpath = ".//div[@data-qa='catalog-menu-list-item-level-0'][17]")
    private Button houseMenuItem;

    @Name("Автотовары")
    @FindBy(xpath = ".//div[@data-qa='catalog-menu-list-item-level-0'][18]")
    private Button autoMenuItem;

    @Name("Ювелирные изделия")
    @FindBy(xpath = ".//div[@data-qa='catalog-menu-list-item-level-0'][19]")
    private Button jewelryMenuItem;

    @Name("Подарки")
    @FindBy(xpath = ".//div[@data-qa='catalog-menu-list-item-level-0'][20]")
    private Button giftsMenuItem;

    @Name("Бренды")
    @FindBy(xpath = ".//div[@data-qa='catalog-menu-list-item-level-0-show-all'][2]")
    private Button brandsMenuItem;

    @Name("Сезонное предложение")
    @FindBy(xpath = ".//div[@data-qa='catalog-menu-list-item-level-0'][21]")
    private Button seasonalCollectionMenuItem;

    @Name("Акции")
    @FindBy(xpath = ".//div[@data-qa='catalog-menu-list-item-level-0'][22]")
    private Button stocksMenuItem;

    @Name("Авиабилеты")
    @FindBy(xpath = "//div[@data-qa='catalog-menu-list-item-level-0'][23]")
    private Button flightsMenuItem;

}
