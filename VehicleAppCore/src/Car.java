public class Car extends Vehicle implements EngineOwnerInterface, ParkableInterface {
    private EngineKind fuelType;
    private float fuelAmount;
    private Garage garage = null;

    public Car(String name, EngineKind fuelType) {
        this(name, fuelType, 0, null);
    }

    public Car(String name, EngineKind fuelType, float fuelAmount, Garage garage) {
        super(name);

        this.fuelType = fuelType;
        this.fuelAmount = fuelAmount;

        if (garage != null)
        {
            park(garage);
        }
    }

    @Override
    public void Refuel(float amount) {
        fuelAmount += amount;
    }

    @Override
    public EngineKind getFuelType() {
        return fuelType;
    }

    @Override
    public float getFuelAmount() {
        return fuelAmount;
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

        return "Car: " + name + "\t FuelType: " + fuelType.name() +
                "\t FuelAmount: " + Float.toString(fuelAmount) + garageString;
    }
}
