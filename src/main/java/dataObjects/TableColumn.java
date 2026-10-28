package dataObjects;

public enum TableColumn {
    // Success Book Ticket and My ticket
    DEPART_STATION("Depart Station"),
    ARRIVE_STATION("Arrive Station"),
    SEAT_TYPE("Seat Type"),
    DEPART_DATE("Depart Date"),
    BOOK_DATE("Book Date"),
    EXPIRED_DATE("Expired Date"),
    AMOUNT("Amount"),
    TOTAL_PRICE("Total Price"),

    // My ticket
    NO("No."),
    STATUS("Status"),
    OPERATION("Operation"),

    //Timetable
    CHECK_PRICE("Check Price"),
    BOOK_TICKET("Book ticket"),

    // Ticket price
    PRICE_VND("Price (VND)"),
    PRICE_SEAT_TYPE("Seat type");

    private final String text;

    TableColumn(String text) { this.text = text; }

    public String getName() { return text; }
}