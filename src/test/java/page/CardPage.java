package page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import data.CardInfo;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CardPage {
    private SelenideElement cardNumber = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement month = $("[placeholder='08']");
    private SelenideElement year = $("[placeholder='22']");
    private SelenideElement code = $("[placeholder='999']");
    private SelenideElement holder = $$(".input__control").get(3);
    private SelenideElement continueButton = $$("button").find(exactText("Продолжить"));

    public CardPage() {
        $$("button").filter(text("Купить")).first().click();
        if (!$$(".heading").find(text("Оплата по карте")).isDisplayed()){
            throw new RuntimeException("oops, heading not displayed");
        };
    }

    @Step("Отправка заполненной формы покупки по карте")
    public void sendFormPaymentByCard(CardInfo card) {
        cardNumber.setValue(card.getNumber());
        month.setValue(card.getMonth());
        year.setValue(card.getYear());
        holder.setValue(card.getHolder());
        code.setValue(card.getCode());
        continueButton.click();
    }

    @Step("Отправка пустой формы покупки по карте")
    public void sendEmptyFormPaymentByCard() {
        continueButton.click();
    }

    @Step("Проверка успешности операции")
    public void successCheck() {
        $(".notification__title").shouldHave(text("Успешно"));
        $(".notification__content").shouldHave(text("Операция одобрена Банком."));
    }

    @Step("Проверка ошибки операции")
    public void failureCheck() {
        $(".notification__title").shouldHave(text("Ошибка"));
        $(".notification__content").shouldHave(text("Ошибка! Банк отказал в проведении операции."));
    }

    @Step("Проверка появления уведомления о неверном сроке действия карты")
    public void durationCheck() {
        $$(".input__sub").first().shouldBe(visible, Duration.ofSeconds(10)).shouldHave(exactText("Неверно указан срок действия карты"));
    }

    @Step("Проверка появления уведомления о неверном значении владельца карты")
    public void holderCheck() {
        $$(".input__sub").first().shouldBe(visible, Duration.ofSeconds(10)).shouldHave(exactText("Неверно указан владелец карты"));
    }

    @Step("Проверка появления уведомления о неверном формате данных для номера карты")
    public void cardNumberFormatCheck() {
        $$(".input__sub").first().shouldBe(visible, Duration.ofSeconds(10)).shouldHave(exactText("Неверный формат"));
    }

}