import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.CardPage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.open;

public class BuyWithCardTest {

    @BeforeAll
    static void setupAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    @Step("Открытие сайта")
    void openURL() {
        open("http://localhost:8080");
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("Успешная оплата по разрешённой карте")
    public void approvedCardPaymentTest() {
        var card = DataHelper.getApprovedCard();
        var cardPage = new CardPage();
        cardPage.sendFormPaymentByCard(card);
        cardPage.successCheck();
    }

    // https://github.com/Denishsh/Dp/issues/2
    @Test
    @DisplayName("Ошибка при оплате по запрещённой карте")
    public void declinedCardPaymentTest() {
        var card = DataHelper.getDeclinedCard();
        var cardPage = new CardPage();
        cardPage.sendFormPaymentByCard(card);
        cardPage.failureCheck();
    }

    // https://github.com/Denishsh/Dp/issues/3
    @Test
    @DisplayName("Ошибка при оплате по карте с неправильным сроком действия")
    public void durationCardPaymentTest() {
        var card = DataHelper.getApprovedCard();
        var incorrectDate = LocalDate.now().plusMonths(61).format(DateTimeFormatter.ofPattern("dd.MM.yy"));
        card.setYear(DataHelper.getYear(incorrectDate));
        card.setMonth(DataHelper.getMonth(incorrectDate));
        var cardPage = new CardPage();
        cardPage.sendFormPaymentByCard(card);
        cardPage.durationCheck();
    }

    // https://github.com/Denishsh/Dp/issues/1
    @Test
    @DisplayName("Ошибка при оплате картой с неправильным владельцем")
    public void ownerCardPaymentTest() {
        var card = DataHelper.getApprovedCard();
        card.setHolder("12345");
        var cardPage = new CardPage();
        cardPage.sendFormPaymentByCard(card);
        cardPage.holderCheck();
    }

    @Test
    @DisplayName("Отправка пустой формы")
    void shouldSendEmptyForm() {
        var cardPage = new CardPage();
        cardPage.sendEmptyFormPaymentByCard();
        cardPage.cardNumberFormatCheck();
    }

}
