package dataObjects;

public enum TabName {
    HOME("Home"),
    FAQ("FAQ"),
    CONTACT("Contact"),
    TIMETABLE("Timetable"),
    TICKET_PRICE("Ticket price"),
    BOOK_TICKET("Book ticket"),
    MY_TICKET("My ticket"),
    CHANGE_PASSWORD("Change password"),
    LOGIN("Login"),
    REGISTER("Register"),
    LOG_OUT("Log out");

    private final String text;

    TabName(String text) { this.text = text; }

    public String getName() { return text; }
}