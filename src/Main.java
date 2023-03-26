public class Main {

    public static void main(String[] args) {
	    RentalCompany rentalCompany = new RentalCompany("db.xml", 5);

        for (Vehicle vehicle : rentalCompany.getVehicles()) {
            System.out.println(vehicle.toString());
        }

        /*Car car = new Car("MyCar1", EngineKind.CNG);
        rentalCompany.addVehicle(car);

        Motorboat motorboat = new Motorboat("MyMotorboat", EngineKind.Diesel);
        rentalCompany.addVehicle(motorboat);

        Bicycle bicycle = new Bicycle("MyBicycle");
        rentalCompany.addVehicle(bicycle);

        Garage garage = rentalCompany.getGarage(3);
        bicycle.park(garage);

        Scooter scooter = new Scooter("MyScooter");
        rentalCompany.addVehicle(scooter);*/

        rentalCompany.save();
    }
}
