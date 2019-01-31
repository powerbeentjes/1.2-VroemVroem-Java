package parkeersimulator.model;

public class Financing {

    private float currentWeekIncome;
    private float currentDayIncome;
    private float upcomingIncome;

    public float singleDayPrice = 5.50f;

    public void addToWeekIncome(float amountToAdd) {
        this.currentWeekIncome += amountToAdd;
    }

    public void addToDayIncome(float amountToAdd) {
        this.currentDayIncome += amountToAdd;
        this.upcomingIncome -= amountToAdd;

        addToWeekIncome(amountToAdd);
    }

    public void addToUpcoming(float amountToAdd) {
        this.upcomingIncome += amountToAdd;
    }

    public float getCurrentWeekIncome() { return this.currentWeekIncome; }

    public float getCurrentDayIncome() { return this.currentDayIncome; }

    public float getUpcomingIncome() {
        return this.upcomingIncome;
    }

    public void resetCurrentDay() {
        this.currentDayIncome = 0;
    }

    public void resetCurrentWeek() {
        this.currentWeekIncome = 0;
    }
}
