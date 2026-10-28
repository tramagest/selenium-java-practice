package dataObjects;

public enum ValidData {
    PASSWORD("123456789"),
    EMAIL("hobepe1432@ensproxy.com"),
    PID("123456789");

    private final String value;

    ValidData(String value) { this.value = value; }

    public String getValue() { return value; }
}