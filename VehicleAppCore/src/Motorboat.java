public class Motorboat extends Vehicle implements EngineOwnerInterface {
    private EngineKind fuelType;
    private float fuelAmount = 0;

    public Motorboat(String name, EngineKind fuelType) {
        this(name, fuelType, 0);
    }

    public Motorboat(String name, EngineKind fuelType, float fuelAmount) {
        super(name);

        this.fuelType = fuelType;
        this.fuelAmount = fuelAmount;
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
    public String toString() {
        return "Motorboat: " + name + "\t FuelType: " + fuelType.name() +
                "\t FuelAmount: " + Float.toString(fuelAmount);
    }
}
