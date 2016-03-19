package library.neetoffice.com.xmlparser;

/**
 * Created by Deo on 2016/3/11.
 */
public class DoubleElement {
    @Element
    double value;

    public DoubleElement() {
    }

    public DoubleElement(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
