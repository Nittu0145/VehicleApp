public class Garage {
    private int number;
    private Boolean isEmpty;

    public Garage(int number) {
        this(number, true);
    }

    public Garage(int number, Boolean isEmpty) {
        this.number = number;
        this.isEmpty = isEmpty;
    }

    public int getNumber() {
        return number;
    }

    public Boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(Boolean value) {
        isEmpty = value;
    }
}
