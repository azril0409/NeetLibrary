package library.neetoffice.com.xmlparser;

/**
 * Created by Deo on 2016/3/10.
 */
public final class IntegerElement {
    @Content
    int value;

    public IntegerElement() {
    }

    public IntegerElement(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
