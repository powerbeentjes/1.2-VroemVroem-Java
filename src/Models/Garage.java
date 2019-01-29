package Models;

public class Garage {
    private int floors;
    private int rows;
    private int parkingLocations;
    private int openLocations;

    //Garage model constructor
    public Garage() {

    }

    //floors get/set
    public int getFloors() {
        return floors;
    }
    public void setFloors(int floors) {
        this.floors = floors;
    }

    //rows get/set
    public int getRows() { return rows; }
    public void setRows(int rows) {
        this.rows = rows;
    }

    //floors get/set
    public int getParkingLocations() {
        return parkingLocations;
    }
    public void setParkingLocations(int parkingLocations) {
        this.parkingLocations = parkingLocations;
    }

    //floors get/set
    public int getOpenLocations() {
        return openLocations;
    }
    public void setOpenLocations(int openLocations) {
        this.openLocations = openLocations;
    }
}
