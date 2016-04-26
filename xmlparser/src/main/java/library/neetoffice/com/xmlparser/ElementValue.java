package library.neetoffice.com.xmlparser;

/**
 * Created by Deo on 2016/3/10.
 */
public final class ElementValue extends ElementMap  {
    @Attribute
    AttributeMap attribute = new AttributeMap();
    @Content
    String text = "";

    public AttributeMap getAttributes() {
        return attribute;
    }

    public void addAttribute(String name, String value) {
        attribute.put(name, value);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
