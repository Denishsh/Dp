package data;

public class CardInfo {
    private String number;
    private String month;
    private String year;
    private String holder;
    private String code;

    public CardInfo(String cardNumber, String month, String year, String holder, String code) {
        this.number = cardNumber;
        this.month = month;
        this.year = year;
        this.holder = holder;
        this.code = code;
    }

    public String getNumber() {
        return number;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public String getHolder() {
        return holder;
    }

    public String getCode() {
        return code;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
