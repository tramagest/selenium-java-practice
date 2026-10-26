package dataObjects;

public class Ticket {

    private final String departStation;
    private final String arriveStation;
    private final String seatType;
    private final Integer amount;
    private final String date;

    public Ticket(String departStation, String arriveStation, String seatType, Integer amount, String date ) {
        this.departStation = departStation;
        this.arriveStation = arriveStation;
        this.seatType = seatType;
        this.amount = amount;
        this.date = date;
    }

    public String getDepartDate() { return date; }
    public String getDepartStation() { return departStation; }
    public String getArriveStation() { return arriveStation; }
    public String getSeatType() { return seatType; }
    public Integer getAmount() { return amount;}
}
