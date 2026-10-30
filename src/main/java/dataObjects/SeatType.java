package dataObjects;

public enum SeatType {
    HS("HS", "Hard seat"),
    SS("SS", "Soft seat"),
    SSC("SSC", "Soft seat with air conditioner"),
    HB("HB", "Hard bed"),
    SB("SB", "Soft bed"),
    SBC("SBC", "Soft bed with air conditioner");

    private final String code;
    private final String displayName;

    SeatType(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public String getCode() { return code; }
    public String getName() { return displayName; }

    public static SeatType fromName(String name) {
        for (SeatType s : values()) {
            if (s.getName().equalsIgnoreCase(name.trim())) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown seat type: " + name);
    }
}