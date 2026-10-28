package dataObjects;

public enum InvalidData {
    EMPTY(""),
    INVALID_PASSWORD("1234567890");

    private final String value;

    InvalidData(String value) { this.value = value; }

    public String getValue() { return value; }
}