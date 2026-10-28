package dataObjects;

public enum TicketOperation {
    CANCEL("Cancel"),
    DELETE("Delete");

    private final String text;

    TicketOperation(String text) { this.text = text; }

    public String getName() { return text; }
}