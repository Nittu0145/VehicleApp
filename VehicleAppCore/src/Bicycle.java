public class Bicycle extends Vehicle implements ParkableInterface {
    private Garage garage = null;

    public Bicycle(String name) {
        this(name, null);
    }

    public Bicycle(String name, Garage garage) {
        super(name);

        if (garage != null)
        {
            park(garage);
        }
    }

    @Override
    public void park(Garage garage) {
        if (!garage.isEmpty())
        {
            return;
        }

        this.garage = garage;
        garage.setEmpty(false);
    }

    @Override
    public Garage getGarage() {
        return garage;
    }

    @Override
    public String toString() {
        String garageString = "";
        if (garage != null) {
            garageString = "\t Garage number: " + garage.getNumber();
        }

        return "Bicycle: " + name + "\t FuelType: " + garageString;
    }
}
