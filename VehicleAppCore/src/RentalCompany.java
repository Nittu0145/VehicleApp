import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class RentalCompany {
    private String dbPath;
    private ArrayList<Vehicle> vehicles;
    private ArrayList<Garage> garages;

    public RentalCompany(String dbPath, int garageNumber)
    {
        this.dbPath = dbPath;

        vehicles = new ArrayList<>();
        garages = new ArrayList<>();
        for (int i = 0; i < garageNumber; i++)
        {
            garages.add(new Garage(i));
        }

        load();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    private void load()
    {
        try {
            File xmlFile = new File(dbPath);
            if (!xmlFile.exists()) {
                return;
            }

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            Node vehiclesNode = doc.getElementsByTagName("Vehicles").item(0);
            for (int i = 0; i < vehiclesNode.getChildNodes().getLength(); i++) {
                Node node = vehiclesNode.getChildNodes().item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String name = element.getElementsByTagName("name").item(0).getTextContent();

                    if (element.getNodeName().equals("Car")) {
                        EngineKind fuelType = EngineKind.valueOf(element.getElementsByTagName("FuelType").item(0).getTextContent());
                        float fuelAmount = Float.parseFloat(element.getElementsByTagName("FuelAmount").item(0).getTextContent());

                        Garage garage = null;
                        if (element.getElementsByTagName("GarageNumber").getLength() > 0)
                            garage = garages.get(Integer.parseInt(element.getElementsByTagName("GarageNumber").item(0).getTextContent()));

                        Car car = new Car(name, fuelType, fuelAmount, garage);
                        addVehicle(car);
                    } else if (element.getNodeName().equals("Motorboat")) {
                        EngineKind fuelType = EngineKind.valueOf(element.getElementsByTagName("FuelType").item(0).getTextContent());
                        float fuelAmount = Float.parseFloat(element.getElementsByTagName("FuelAmount").item(0).getTextContent());

                        Motorboat motorboat = new Motorboat(name, fuelType, fuelAmount);
                        addVehicle(motorboat);
                    } else if (element.getNodeName().equals("Bicycle")) {
                        Garage garage = null;
                        if (element.getElementsByTagName("GarageNumber").getLength() > 0)
                            garage = garages.get(Integer.parseInt(element.getElementsByTagName("GarageNumber").item(0).getTextContent()));

                        Bicycle bicycle = new Bicycle(name, garage);
                        addVehicle(bicycle);
                    } else if (element.getNodeName().equals("Scooter")) {
                        Scooter scooter = new Scooter(name);
                        addVehicle(scooter);
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public void save()
    {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            // create root element
            Element rootElement = doc.createElement("Vehicles");
            doc.appendChild(rootElement);

            // iterate through the list of vehicles and add to XML file
            for (Vehicle vehicle : vehicles) {
                Element vehicleElement = null;
                if (vehicle instanceof Car) {
                    vehicleElement = doc.createElement("Car");
                } else if (vehicle instanceof Motorboat) {
                    vehicleElement = doc.createElement("Motorboat");
                } else if (vehicle instanceof Bicycle) {
                    vehicleElement = doc.createElement("Bicycle");
                } else if (vehicle instanceof Scooter) {
                    vehicleElement = doc.createElement("Scooter");
                }

                // add name element
                Element nameElement = doc.createElement("name");
                nameElement.appendChild(doc.createTextNode(vehicle.getName()));
                vehicleElement.appendChild(nameElement);

                if (vehicle instanceof EngineOwnerInterface engineOwner) {
                    // add fuel type element
                    Element fuelTypeElement = doc.createElement("FuelType");
                    fuelTypeElement.appendChild(doc.createTextNode(engineOwner.getFuelType().name()));
                    vehicleElement.appendChild(fuelTypeElement);

                    // add fuel amount element
                    Element fuelAmountElement = doc.createElement("FuelAmount");
                    fuelAmountElement.appendChild(doc.createTextNode(Float.toString(engineOwner.getFuelAmount())));
                    vehicleElement.appendChild(fuelAmountElement);
                }

                if (vehicle instanceof ParkableInterface parkable) {
                    Garage garage = parkable.getGarage();
                    if (garage != null) {
                        int garageNumber = garage.getNumber();

                        // add fuel amount element
                        Element garageNumberElement = doc.createElement("GarageNumber");
                        garageNumberElement.appendChild(doc.createTextNode(Integer.toString(garageNumber)));
                        vehicleElement.appendChild(garageNumberElement);
                    }
                }

                rootElement.appendChild(vehicleElement);
            }

            // write the content into XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(dbPath));
            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public Garage getGarage(int number)
    {
        return garages.get(number);
    }

    public Iterable<Vehicle> getVehicles() {
        return vehicles;
    }
}
