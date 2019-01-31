package parkeersimulator.model;

public class ParkeerGarage {

    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfOpenSpots;
    private int numberOfParkingPassOnlySpots;
    private Car[][][] cars;

    public static Location[] locations;

    /**
     * Constructor voor het aanmaken van een ParkeerGarage.
     * @param numberOfFloors
     * @param numberOfRows
     * @param numberOfPlaces
     */
    public ParkeerGarage(int numberOfFloors, int numberOfRows, int numberOfPlaces, int numberOfParkingPassOnlySpots) {
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfOpenSpots = numberOfFloors * numberOfRows * numberOfPlaces;
        this.numberOfParkingPassOnlySpots = numberOfParkingPassOnlySpots;
        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];

        locations = new Location[this.numberOfOpenSpots];

        for (int floor = 0; floor < numberOfFloors; floor++) {
            for (int row = 0; row < numberOfRows; row++) {
                for (int place = 0; place < numberOfPlaces; place++) {
                    int index = place + numberOfPlaces * (row + (numberOfRows * floor));
                    locations[index] = new Location(floor, row, place, (index <= numberOfParkingPassOnlySpots));
                }
            }
        }
    }

    /**
     * Deze methode retourneerd het aantal vakken van de parkeergarage
     * @return aantal vakken
     */
    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    /**
     * Deze methode retourneerd het aantal rijen per vlak
     * @return aantal rijen
     */
    public int getNumberOfRows() {
        return numberOfRows;
    }

    /**
     * Deze methode retourneerd het aantal plaatsen per rij
     * @return aantal plaatsen
     */
    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    /**
     * Deze methode retourneerd het totale aantal beschikbare plaatsen in de parkeergarage
     * @return aantal beschikbare plaatsen in totaal
     */
    public int getNumberOfOpenSpots() {
        return numberOfOpenSpots;
    }

    /**
     * Deze methode retourneerd een auto als er zich een auto bevindt op de plaats die meegegeven is
     * @param location locatie die gecontroleerd moet worden
     * @return auto die op de plek staat
     */
    public Car getCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

    /**
     * Deze methode plaatst een auto op de megegeven plaats
     * @param location locatie waar de auto geplaatst moet worden
     * @param car auto die geplaatst moet worden
     * @return een bool die waar is als de auto succesvol is geplaatst
     */
    public boolean setCarAt(Location location, Car car) {
        if (!locationIsValid(location)) {
            return false;
        }
        Car oldCar = getCarAt(location);
        if (oldCar == null) {
            cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
            car.setLocation(location);
            numberOfOpenSpots--;
            return true;
        }
        return false;
    }

    /**
     * Deze methode verwijderd een auto van een plaats
     * @param location de locatie waar een auto van verwijderd moet worden
     * @return de auto die verwijderd is
     */
    public Car removeCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        Car car = getCarAt(location);
        if (car == null) {
            return null;
        }
        cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
        car.setLocation(null);
        numberOfOpenSpots++;
        return car;
    }

    /**
     * Deze methode retourneerd de eerste beschikbare openbare plaats die gevonden kan worden
     * @return de locatie die gevonden is
     */
    public Location getFirstFreePublicLocation() {
        for (int i = 0; i < locations.length; i++) {
            if (getCarAt(locations[i]) == null && !locations[i].isParkingPassOnly()) {
                return locations[i];
            }
        }
        return null;
    }

    /**
     * Deze methode retourneerd de eerste beschikbare plaats die gereserveerd is voor mensen met een abonnement
     * @return de beschikbare locatie voor mensen met een abonnement
     */
    public Location getFirstFreeParkingPassOnlyLocation() {
        for (int i = 0; i < locations.length; i++) {
            if (getCarAt(locations[i]) == null && locations[i].isParkingPassOnly()) {
                return locations[i];
            }
        }
        return getFirstFreePublicLocation();
    }

    /**
     * Deze methode retourneerd de eerste gevonden auto die gaat vertrekken
     * @return auto die vertrekt
     */
    public Car getFirstLeavingCar() {
        for (int i = 0; i < locations.length; i++) {
            Car car = getCarAt(locations[i]);
            if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                return car;
            }
        }
        return null;
    }

    /**
     * Deze methode zorgt ervoor dat iedere auto een tik verder gaat in de tijd
     */
    public void tick() {
        for (int i = 0; i < locations.length; i++) {
            Car car = getCarAt(locations[i]);
            if (car != null) {
                car.tick();
            }
        }
    }

    /**
     * Deze methode controleerd of een plaats bestaat
     * @param location locatie die gecontroleerd moet worden
     * @return bool die true is als de locatie bestaat
     */
    private boolean locationIsValid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces) {
            return false;
        }
        return true;
    }

    public int getTotalParkingPassCarsParked() {
        int total = 0;
        for (int i = 0; i < locations.length; i++) {
            if (cars[locations[i].getFloor()][locations[i].getRow()][locations[i].getPlace()] instanceof ParkingPassCar) {
                total++;
            }
        }
        return total;
    }

    public int getTotalAdHocCarCarsParked() {
        int total = 0;
        for (int i = 0; i < locations.length; i++) {
            if (cars[locations[i].getFloor()][locations[i].getRow()][locations[i].getPlace()] instanceof AdHocCar) {
                total++;
            }
        }
        return total;
    }

    public int getTotalReservationCarsParked() {
        int total = 0;
        for (int i = 0; i < locations.length; i++) {
            if (cars[locations[i].getFloor()][locations[i].getRow()][locations[i].getPlace()] instanceof ReservationCar) {
                total++;
            }
        }
        return total;
    }
}