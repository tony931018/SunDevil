package application;

public class Equipment {
    private String name;
    private String category;
    private String condition;
    private String owner;
    private double value;
    private boolean available;

    public Equipment(String name, String category, String condition, String owner, double value) {
        this.name = name;
        this.category = category;
        this.condition = condition;
        this.owner = owner;
        this.value = value;
        this.available = true;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getCondition() {
        return condition;
    }

    public String getOwner() {
        return owner;
    }

    public double getValue() {
        return value;
    }

    public boolean isAvailable() {
        return available;
    }

    public double getDailyRate() {
        return value * 0.05;
    }

    public double getDeposit() {
        return value * 0.20;
    }

    public String getStatus() {
        if (available) {
            return "Available";
        } else {
            return "Rented";
        }
    }

    public void rent() {
        available = false;
    }

    public void returnItem() {
        available = true;
    }

    @Override
    public String toString() {
        return name + " | " + category + " | $" 
                + String.format("%.2f", getDailyRate()) 
                + "/day | " + getStatus();
    }
}
