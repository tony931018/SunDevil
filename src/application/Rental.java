package application;

public class Rental {
    private Equipment equipment;
    private String renter;
    private int days;

    public Rental(Equipment equipment, String renter, int days) {
        this.equipment = equipment;
        this.renter = renter;
        this.days = days;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public String getRenter() {
        return renter;
    }

    public int getDays() {
        return days;
    }

    public double getTotal() {
        return equipment.getDailyRate() * days + equipment.getDeposit();
    }

    @Override
    public String toString() {
        return equipment.getName()
                + " | Renter: " + renter
                + " | " + days + " days"
                + " | Total: $" + String.format("%.2f", getTotal());
    }
}
