package parkeersimulator.model;

public class Location {

    private int floor;
    private int row;
    private int place;

    private boolean parkingPassOnly;

    /**
     * Constructor for objects of class Location
     */
    public Location(int floor, int row, int place, boolean parkingPassOnly) {
        this.floor = floor;
        this.row = row;
        this.place = place;

        this.parkingPassOnly = parkingPassOnly;
    }

    public boolean equals(Object obj) {
        if(obj instanceof Location) {
            Location other = (Location) obj;
            return floor == other.getFloor() && row == other.getRow() && place == other.getPlace();
        }
        else {
            return false;
        }
    }

    /**
     * @return A string representation of the location.
     */
    public String toString() {
        return floor + "," + row + "," + place;
    }

    /**
     * @return A code for the location.
     */
    public int hashCode() {
        return (floor << 20) + (row << 10) + place;
    }

    /**
     * @return The floor.
     */
    public int getFloor() {
        return floor;
    }

    /**
     * @return The row.
     */
    public int getRow() {
        return row;
    }

    /**
     * @return The place.
     */
    public int getPlace() {
        return place;
    }

    public boolean isParkingPassOnly() {
        return parkingPassOnly;
    }
}