package parkeersimulator.model;

import java.time.DayOfWeek;
import java.util.Random;

public class Simulator extends AbstractModel  {
	
	public interface PlusMinButton {
		void setButtons(boolean enabled);
	}
	
	private PlusMinButton plusMinButtonHandler = null;
	
	public void setPlusMinButtonHandler(PlusMinButton plusMinButtonHandler) {
		this.plusMinButtonHandler=plusMinButtonHandler;
	}

	private static final String AD_HOC = "1";
	private static final String PASS = "2";
    private static final String RESERVATION = "3";

	private Thread thread;
	public CarQueue entranceCarQueue;
    public CarQueue entrancePassQueue;
    public CarQueue paymentCarQueue;
    public CarQueue exitCarQueue;

    private ParkeerGarage parkeerGarage;
    public Financing financing;

    public int day = 0;
    private int hour = 0;
    private int minute = 0;

    private int tickPause = 25;
    public int tickAmount = 100;

    int weekDayArrivals= 100; // average number of arriving cars per hour
    int weekendArrivals = 200; // average number of arriving cars per hour
    int weekDayPassArrivals= 50; // average number of arriving cars per hour
    int weekendPassArrivals = 25; // average number of arriving cars per hour
    int weekDayReservationArrivals = 50; // average number of arriving cars per hour
    int weekendReservationArrivals = 25; // average number of arriving cars per hour

    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 7; // number of cars that can pay per minute
    int exitSpeed = 5; // number of cars that can leave per minute

    private int parkingPassTotal = 50; // number of parking passes active in total

    public Simulator() {
        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        parkeerGarage = new ParkeerGarage(3, 6, 30, parkingPassTotal);
        financing = new Financing();
        thread=null;
    }

    /**
     * Deze methode returnd een instantie van de parkeerGarage model, zodat deze ksn worden
     * benaderd vanuit de simulatormodel
     * @return parkeerGarage
     */
    public ParkeerGarage getParkeerGarage() { return parkeerGarage; }

    /**
     * Deze methode zorgt ervoor dat er een nieuwe thread word aangemaakt
     * en de simulator start op het aantal stappen
     * @param aantalStappen
     */
    public void run(int aantalStappen) {
        //check of er al een thread is
        if(thread==null){
            thread =new Thread (new Runnable() {

                @Override
                public void run() {
                    //Roep de tick methode aan afhankelijk van het aantalstappen
                    for (int i = 0; i < aantalStappen; i++) {
                        tick();
                        if(i==aantalStappen-1) {
                        	thread = null;
                        	plusMinButtonHandler.setButtons(true);
                        }
                    }
                }
            });
            thread.start();
        }
    }

    public void stop()
    {
        if(thread != null)
        {
            thread.stop();
            thread = null;
        }
    }

    private void tick() {
        updateFinancing();
    	advanceTime();
    	handleExit();
    	updateViews();
    	// Pause.
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    	handleEntrance();
    }

    int lastDay;
    private void updateFinancing() {
        if (day == 0 && lastDay != day) {
            financing.resetCurrentWeek();
        }
        if (day != lastDay) {
            lastDay = day;
            financing.resetCurrentDay();
        }
    }

    private void advanceTime(){
        // Advance the time by one minute.
        minute++;
        while (minute > 59) {
            minute -= 60;
            hour++;
        }
        while (hour > 23) {
            hour -= 24;
            day++;
        }
        while (day > 6) {
            day -= 7;
        }

    }

   public String getTime()
    {

        String time = DayOfWeek.of(this.day+1).toString() + " " +  this.hour + ":" + this.minute + ((this.hour > 12) ? " PM" : " AM" );
        return time;
    }

    private void handleEntrance(){
    	carsArriving();
    	carsEntering(entrancePassQueue);
    	carsEntering(entranceCarQueue);  	
    }
    
    private void handleExit(){
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }
    
    private void updateViews(){
    	parkeerGarage.tick();
        // Update the car park view.
        notifyViews();
    }
    
    private void carsArriving(){
    	int numberOfCars=getNumberOfCars(weekDayArrivals, weekendArrivals);
        addArrivingCars(numberOfCars, AD_HOC);

        if (parkeerGarage.getTotalParkingPassCarsParked() < parkingPassTotal) {
            numberOfCars = getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
            addArrivingCars(numberOfCars, PASS);
        }

        numberOfCars=getNumberOfCars(weekDayReservationArrivals, weekendReservationArrivals);
        addArrivingCars(numberOfCars, RESERVATION);
    }

    private void carsEntering(CarQueue queue){
        int i=0;
        // Remove car from the front of the queue and assign to a parking space.
    	while (queue.carsInQueue()>0 && 
    			parkeerGarage.getNumberOfOpenSpots()>0 &&
    			i<enterSpeed) {
            Car car = queue.removeCar();

            Location freeLocation;
            if (car instanceof ParkingPassCar) {
                freeLocation = parkeerGarage.getFirstFreeParkingPassOnlyLocation();
            } else {
                freeLocation = parkeerGarage.getFirstFreePublicLocation();
            }

            parkeerGarage.setCarAt(freeLocation, car);

            if (car.getHasToPay()) {
                financing.addToUpcoming(financing.singleDayPrice);
            }

            i++;
        }
    }
    
    private void carsReadyToLeave(){
        // Add leaving cars to the payment queue.
        Car car = parkeerGarage.getFirstLeavingCar();
        while (car!=null) {
        	if (car.getHasToPay()){
	            car.setIsPaying(true);
	            paymentCarQueue.addCar(car);
        	}
        	else {
        		carLeavesSpot(car);
        	}
            car = parkeerGarage.getFirstLeavingCar();
        }
    }

    private void carsPaying(){
        // Let cars pay.
    	int i=0;
    	while (paymentCarQueue.carsInQueue()>0 && i < paymentSpeed){
            Car car = paymentCarQueue.removeCar();

            financing.addToDayIncome(financing.singleDayPrice);

            carLeavesSpot(car);
            i++;
    	}
    }
    
    private void carsLeaving(){
        // Let cars leave.
    	int i=0;
    	while (exitCarQueue.carsInQueue()>0 && i < exitSpeed){
            exitCarQueue.removeCar();
            i++;
    	}	
    }
    
    private int getNumberOfCars(int weekDay, int weekend){
        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = day < 5
                ? weekDay
                : weekend;

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.3;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        return (int)Math.round(numberOfCarsPerHour / 60);
    }
    
    private void addArrivingCars(int numberOfCars, String type){
        // Add the cars to the back of the queue.
    	switch(type) {

    	case AD_HOC: 
            for (int i = 0; i < numberOfCars; i++) {
            	entranceCarQueue.addCar(new AdHocCar());
            }
            break;

    	case PASS:
            for (int i = 0; i < numberOfCars; i++) {
            	entrancePassQueue.addCar(new ParkingPassCar());
            }
            break;

        case RESERVATION:
             for (int i = 0; i < numberOfCars; i++) {
                entrancePassQueue.addCar(new ReservationCar());
              }
             break;
    	}

    }
    
    private void carLeavesSpot(Car car){
    	parkeerGarage.removeCarAt(car.getLocation());
        exitCarQueue.addCar(car);
    }

}
