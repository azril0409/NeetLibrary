package library.neetoffice.com.xmlparser;

/**
 * Created by Deo on 2016/3/11.
 */
public class LongElement {
    @Content
    long value;

    public LongElement() {
    }

    public LongElement(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
