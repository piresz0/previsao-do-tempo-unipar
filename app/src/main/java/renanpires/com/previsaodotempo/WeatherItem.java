package renanpires.com.previsaodotempo;

public class WeatherItem {
    private String day;
    private String temp;
    private String condition;
    private int icon;

    public WeatherItem(String day, String temp, String condition, int icon) {
        this.day = day;
        this.temp = temp;
        this.condition = condition;
        this.icon = icon;
    }

    public String getDay() { return day; }
    public String getTemp() { return temp; }
    public String getCondition() { return condition; }
    public int getIcon() { return icon; }
}