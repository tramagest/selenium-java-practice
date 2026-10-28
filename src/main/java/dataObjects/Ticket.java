package dataObjects;

public class Ticket {

    private final Station departStation;
    private final Station arriveStation;
    private final SeatType seatType;
    private final Integer amount;
    private final String date;

    public Ticket(Station departStation, Station arriveStation, SeatType seatType, Integer amount, String date) {
        this.departStation = departStation;
        this.arriveStation = arriveStation;
        this.seatType = seatType;
        this.amount = amount;
        this.date = date;
    }

    public Station getDepartStation() { return departStation; }
    public Station getArriveStation() { return arriveStation; }
    public SeatType getSeatType() { return seatType; }
    public Integer getAmount() { return amount; }
    public String getDepartDate() { return date; }

    public String getDepartStationName() { return departStation == null ? null : departStation.getName(); }
    public String getArriveStationName() { return arriveStation == null ? null : arriveStation.getName(); }
    public String getSeatTypeName() { return seatType == null ? null : seatType.getName(); }
}