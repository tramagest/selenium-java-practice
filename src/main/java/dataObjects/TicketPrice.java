package dataObjects;

public enum TicketPrice {
    HS(SeatType.HS, 310000),
    SS(SeatType.SS, 335000),
    SSC(SeatType.SSC, 360000),
    HB(SeatType.HB, 410000),
    SB(SeatType.SB, 460000),
    SBC(SeatType.SBC, 510000);

    private final SeatType seatType;
    private final Integer price;

    TicketPrice(SeatType seatType, Integer price) {
        this.seatType = seatType;
        this.price = price;
    }

    public SeatType getSeatType() { return seatType; }
    public Integer getPrice() { return price; }
}