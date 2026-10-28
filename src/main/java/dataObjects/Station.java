package dataObjects;

public enum Station {
    SAI_GON("Sài Gòn"),
    PHAN_THIET("Phan Thiết"),
    NHA_TRANG("Nha Trang"),
    DA_NANG("Đà Nẵng"),
    HUE("Huế"),
    QUANG_NGAI("Quảng Ngãi");

    private final String displayName;

    Station(String displayName) { this.displayName = displayName; }

    public String getName() { return displayName; }
}