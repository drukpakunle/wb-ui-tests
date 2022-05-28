package ru.wildberries.elements.common;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.htmlelements.element.HtmlElement;
import ru.wildberries.htmlelements.element.Link;

@Name("Меню в подвале")
@FindBy(xpath = "(//footer/div)[2]")
public class FooterMenu extends HtmlElement {

    @Name("О компании")
    @FindBy(xpath = "")
    private Button aboutButton;

    @Name("О нас")
    @FindBy(xpath = "")
    private Link aboutLink;

    @Name("Наши скидки")
    @FindBy(xpath = "")
    private Link discountsLink;

    @Name("Контакты")
    @FindBy(xpath = "")
    private Link contactsLink;

    @Name("Вакансии")
    @FindBy(xpath = "")
    private Link vacanciesLink;

    @Name("Пункты самовывоза")
    @FindBy(xpath = "")
    private Link pickupPointsLink;

    @Name("Сервис и поддержка")
    @FindBy(xpath = "")
    private Button serviceButton;

    @Name("Как сделать заказ")
    @FindBy(xpath = "")
    private Link Link;

    @Name("Способы оплаты")
    @FindBy(xpath = "")
    private Link paymentMethodsLink;

    @Name("Доставка")
    @FindBy(xpath = "")
    private Link deliveryLink;

    @Name("Возврат товара")
    @FindBy(xpath = "")
    private Link purchaseReturnsLink;

    @Name("Правила продажи")
    @FindBy(xpath = "")
    private Link salesRulesLink;

    @Name("Публичная оферта")
    @FindBy(xpath = "")
    private Link publicOfferLink;

    @Name("Возврат средств")
    @FindBy(xpath = "")
    private Link refundLink;

    @Name("Проекты WILDBERRIES")
    @FindBy(xpath = "")
    private Button projectsButton;

    @Name("Партнерам")
    @FindBy(xpath = "")
    private Link toPartnersLink;

    @Name("ЗОЖ и спорт")
    @FindBy(xpath = "")
    private Link healthyLifestyleAndSportsLink;

    @Name("Детям")
    @FindBy(xpath = "")
    private Link childrenLink;

    @Name("On-line журнал")
    @FindBy(xpath = "")
    private Link onlineMagazineLink;

    @Name("Модный блог")
    @FindBy(xpath = "")
    private Link fashionBlogLink;

    @Name("Энциклопедия моды")
    @FindBy(xpath = "")
    private Link fashionEncyclopediaLink;

}
