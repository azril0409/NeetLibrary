package library.neetoffice.com.xmlparser;

/**
 * Created by Deo on 2016/3/9.
 */
public final class StringElement {
    @Element
    String text;

    public StringElement() {
    }

    public StringElement(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
