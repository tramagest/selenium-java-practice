package pageObjects.Railway;

import dataObjects.SeatType;
import dataObjects.Station;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TimetablePage extends BasePage {

    public TimetablePage(WebDriver driver) {
        super(driver);
    }

    private final String DEPART_STATION = "Depart Station";
    private final String ARRIVE_STATION = "Arrive Station";
    private final String CHECK_PRICE= "Check Price";
    private final String BOOK_TICKET= "Book ticket";


    private static final String BOOK_TICKET_BY_SEAT_XPATH =
            "//tr[td[normalize-space()='%s']]//a[normalize-space()='%s']";

    private String colIndex(String column) {
        return "count(//table[contains(@class,'MyTable')]//th[normalize-space()='" + column
                + "']/preceding-sibling::th)+1";
    }

    private String cell(String column) {
        return "td[" + colIndex(column) + "]";
    }

    private By linkLocator(Station depart, Station arrive, String actionColumn) {
        String xpath = "//table[contains(@class,'MyTable')]//tbody/tr["
                + cell(DEPART_STATION) + "[normalize-space()='" + depart.getName() + "'] and "
                + cell(ARRIVE_STATION) + "[normalize-space()='" + arrive.getName() + "']"
                + "]/" + cell(actionColumn) + "/a";
        return By.xpath(xpath);
    }

    public WebElement getCheckPriceLink(Station depart, Station arrive) {
        return find(linkLocator(depart, arrive, CHECK_PRICE));
    }

    public WebElement getBookTicketLink(Station depart, Station arrive) {
        return find(linkLocator(depart, arrive, BOOK_TICKET));
    }

    public void clickCheckPrice(Station depart, Station arrive) {
        click(linkLocator(depart, arrive, CHECK_PRICE));
    }

    public BookTicketPage clickBookTicket(Station depart, Station arrive) {
        click(linkLocator(depart, arrive, BOOK_TICKET));
        return new BookTicketPage(driver);
    }

    public void clickBookTicketBySeat(SeatType seatType) {
        click(By.xpath(String.format(BOOK_TICKET_BY_SEAT_XPATH,
                seatType.getName(), BOOK_TICKET)));
    }
}