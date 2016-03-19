package library.neetoffice.com.xmlparser;

/**
 * Created by Deo on 2016/3/11.
 */
public final class BooleanElement {
    @Element
    boolean value;

    public BooleanElement() {
    }

    public BooleanElement(boolean value) {
        this.value = value;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}
