public abstract class Vehicle {
    private static int globalId = 0;
    protected int id;
    protected String name;

    public Vehicle(String name)
    {
        id = globalId++;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public abstract String toString();
}
