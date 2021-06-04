package be.bxl.formation.demo_06_asynctask_and_requestapi.models;

public class WeatherData {

    private String city;
    private double temp;

    public WeatherData(String city, double temp) {
        this.city = city;
        this.temp = temp;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }
}
