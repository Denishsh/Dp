package data;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataHelper {

    @Step("Получение номера APPROVED карты")
    public static String getApprovedCardNumber() {
        return "1111 2222 3333 4444";
    }

    @Step("Получение номера DECLINED карты")
    public static String getDeclinedCardNumber() {
        return "5555 6666 7777 8888";
    }

    @Step("Получение данных Approved карты")
    public static CardInfo getApprovedCard() {
        String validDate = getValidDate();
        return new CardInfo(
                getApprovedCardNumber(),
                getMonth(validDate),
                getYear(validDate),
                getValidHolder(),
                getValidCode());
    }

    @Step("Получение данных Declined карты")
    public static CardInfo getDeclinedCard() {
        String validDate = getValidDate();
        return new CardInfo(
                getDeclinedCardNumber(),
                getMonth(validDate),
                getYear(validDate),
                getValidHolder(),
                getValidCode());
    }

    @Step("Генерация владельца карты")
    public static String getValidHolder() {
        var faker = new Faker();
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    @Step("Получение номера месяца из даты")
    public static String getMonth(String Date) {
        String[] words = Date.split("\\.");
        String month = words[1];
        return month;
    }

    @Step("Получение года из даты")
    public static String getYear(String Date) {
        String[] words = Date.split("\\.");
        String year = words[2];
        return year;
    }

    @Step("Генерация кода 'CVC/CVV'")
    public static String getValidCode() {
        var faker = new Faker();
        return faker.number().digits(3);
    }

    @Step("Генерация валидного срока действия карты")
    public static String getValidDate() {
        var faker = new Faker();
        int months = faker.number().numberBetween(1, 12);
        int years = faker.number().numberBetween(0, 4);
        return LocalDate.now().plusMonths(months).plusYears(years).format(DateTimeFormatter.ofPattern("dd.MM.yy"));
    }

    @Step("Генерация случайного номера карты")
    public static String getRandomNumberCard() {
        var faker = new Faker();
        return faker.number().digits(16);
    }
}
