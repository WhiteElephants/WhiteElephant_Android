package rawe.gordon.com.fruitmarketclient.generals.dialogs.filter;

/**
 * Created by gordon on 16/5/15.
 */
public class Filter {
    private String color;
    private float from,to;
    private String category;

    public Filter(String color, float from, float to, String category) {
        this.color = color;
        this.from = from;
        this.to = to;
        this.category = category;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public float getFrom() {
        return from;
    }

    public void setFrom(float from) {
        this.from = from;
    }

    public float getTo() {
        return to;
    }

    public void setTo(float to) {
        this.to = to;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
