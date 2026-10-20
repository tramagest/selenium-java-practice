package pageObjects.Railway;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TimetablePage extends BasePage{



    private static final String COL_DEPART = "Depart Station";
    private static final String COL_ARRIVE = "Arrive Station";
    private static final String COL_CHECK_PRICE = "Check Price";
    private static final String COL_BOOK_TICKET = "Book ticket";


    private String colIndex(String headerName) {
        return "count(//table[contains(@class,'MyTable')]//th[normalize-space()='" + headerName
                + "']/preceding-sibling::th)+1";
    }


    private String cell(String headerName) {
        return "td[" + colIndex(headerName) + "]";
    }


    private By linkLocator(String departStation, String arriveStation, String actionColumn) {
        String xpath = "//table[contains(@class,'MyTable')]//tbody/tr["
                + cell(COL_DEPART) + "[normalize-space()='" + departStation + "'] and "
                + cell(COL_ARRIVE) + "[normalize-space()='" + arriveStation + "']"
                + "]/" + cell(actionColumn) + "/a";
        return By.xpath(xpath);
    }

    public WebElement getCheckPriceLink(String departStation, String arriveStation) {
        return find(linkLocator(departStation, arriveStation, COL_CHECK_PRICE));
    }

    public WebElement getBookTicketLink(String departStation, String arriveStation) {
        return find(linkLocator(departStation, arriveStation, COL_BOOK_TICKET));
    }

    public void clickCheckPrice(String departStation, String arriveStation) {
        click(linkLocator(departStation, arriveStation, COL_CHECK_PRICE));
    }

    public BookTicketPage clickBookTicket(String departStation, String arriveStation) {
        click(linkLocator(departStation, arriveStation, COL_BOOK_TICKET));
        return new BookTicketPage();
    }

    private static final String BOOK_TICKET_BY_SEAT_XPATH =
            "//tr[td[normalize-space()='%s']]//a[normalize-space()='Book ticket']";

    private By bookTicketBySeatLocator(String seatType) {
        return By.xpath(String.format(BOOK_TICKET_BY_SEAT_XPATH, seatType));
    }

    public void clickBookTicketBySeat(String seatType) {
        click(bookTicketBySeatLocator(seatType));
    }
}
