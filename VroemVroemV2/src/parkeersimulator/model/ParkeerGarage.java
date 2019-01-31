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
     * Constructor for objects of class ParkeerGarage
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
     * @return floors
     */
    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    /**
     * @return rows
     */
    public int getNumberOfRows() {
        return numberOfRows;
    }

    /**
     * @return amount of places
     */
    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    /**
     * @return total amount of free spaces
     */
    public int getNumberOfOpenSpots() {
        return numberOfOpenSpots;
    }

    /**
     * This method returns if a specified place is occupied or not
     * @param location , the location that you want to test
     * @return Car , the car that is taking up the space , returns null if no car is there
     */
    public Car getCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

    /**
     * Ths method places a car in the specified location
     * @param location location where the car needs to be placed
     * @param car the car
     * @return boolean true if placement was a succes
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
     * Ths method removes a car in the specified location
     * @param location location where the car needs to be placed
     * @return the car that got removed
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
     * This method specifies the first free parking place
     * @return the found location
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
     * This method specifies the first free reserved parking place
     * @return the found location for people with a subscription
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
     * This method gives the first car that is going to leave
     * @return the cra that is going to leave
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
     * Makes every car going up in time
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
     * Checks if a specified place exists
     * @param location location that has to be checked
     * @return boolean true if location exists
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