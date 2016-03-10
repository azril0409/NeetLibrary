package library.neetoffice.com.xmlparser;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Deo on 2016/3/10.
 */
class Object2StringHelper {

    static String toXML(Object object) {
        if (object == null) {
            return null;
        }
        final Tag tag = object.getClass().getAnnotation(Tag.class);
        return toXML(object, getTagName(tag, object));
    }

    private static String getTagName(Tag tag, Object object) {
        String name = tag.value();
        if (tag == null || name.length() == 0) {
            name = object.getClass().getSimpleName();
        }
        return name;
    }

    static String toXML(Object object, String tag) {
        final StringBuffer stringBuffer = new StringBuffer();
        final ArrayList<Field> attributeFields = new ArrayList<>();
        final ArrayList<Field> elementFields = new ArrayList<>();
        for (Field field : object.getClass().getDeclaredFields()) {
            final Attribute attribute = field.getAnnotation(Attribute.class);
            final Element element = field.getAnnotation(Element.class);
            if (attribute != null) {
                attributeFields.add(field);
            } else if (element != null) {
                elementFields.add(field);
            }
        }
        stringBuffer.append("<");
        stringBuffer.append(tag);
        for (Field field : attributeFields) {
            field.setAccessible(true);
            try {
                if (field.getType() == Boolean.class
                        || field.getType() == boolean.class
                        || field.getType() == Integer.class
                        || field.getType() == int.class
                        || field.getType() == Float.class
                        || field.getType() == float.class
                        || field.getType() == Double.class
                        || field.getType() == double.class
                        || field.getType() == Long.class
                        || field.getType() == long.class
                        || field.getType() == byte[].class
                        || field.getType() == char[].class
                        || field.getType() == String.class) {
                    final Object value = field.get(object);
                    stringBuffer.append(" " + field.getName() + "='" + (value != null ? value : "") + "'");
                }
            } catch (IllegalAccessException e) {
            }
        }
        StringBuffer elementValue = new StringBuffer();
        for (Field elementField : elementFields) {
            elementField.setAccessible(true);
            try {
                if (elementField.getType() == Boolean.class
                        || elementField.getType() == boolean.class
                        || elementField.getType() == Integer.class
                        || elementField.getType() == int.class
                        || elementField.getType() == Float.class
                        || elementField.getType() == float.class
                        || elementField.getType() == Double.class
                        || elementField.getType() == double.class
                        || elementField.getType() == Long.class
                        || elementField.getType() == long.class
                        || elementField.getType() == byte[].class
                        || elementField.getType() == char[].class
                        || elementField.getType() == String.class) {
                    final Object value = elementField.get(object);
                    elementValue.append(value != null ? value : "");
                } else if (elementField.getType() == ElementMap.class) {
                    final ElementMap map = (ElementMap) elementField.get(object);
                    for (String key : map.keySet()) {
                        final ElementValue value = map.get(key);
                        elementValue.append("<");
                        elementValue.append(key);
                        for (String attribute : value.attribute.keySet()) {
                            String attributeValue = value.attribute.get(attribute);
                            if (attributeValue == null) {
                                attributeValue = "";
                            }
                            elementValue.append(" " + attribute + "='" + attributeValue + "'");
                        }
                        elementValue.append(getTagEndString(key,value.value));
                    }
                } else {
                    final Object o = elementField.get(object);
                    final Element element = elementField.getAnnotation(Element.class);
                    String name = element.name();
                    if (name.length() == 0) {
                        name = elementField.getName();
                    }
                    if (o != null) {
                        if (o instanceof Collection) {
                            for (Object item : (Collection) o) {
                                if (item.getClass().getAnnotation(Tag.class) != null) {
                                    elementValue.append(toXML(item, name));
                                }
                            }
                        } else {
                            if (elementField.getType().getAnnotation(Tag.class) != null) {
                                elementValue.append(toXML(o, name));
                            }
                        }
                    }
                }
            } catch (IllegalAccessException e) {
            }
        }
        stringBuffer.append(getTagEndString(tag,elementValue.toString()));
        return stringBuffer.toString();
    }

    private static String getTagEndString(String tag,String value){
        final StringBuffer stringBuffer = new StringBuffer();
        if (value.length() > 0) {
            stringBuffer.append(">");
            stringBuffer.append(value);
            stringBuffer.append("</");
            stringBuffer.append(tag);
            stringBuffer.append(">");
        } else {
            stringBuffer.append(" />");
        }
        return stringBuffer.toString();
    }
}