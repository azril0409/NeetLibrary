package library.neetoffice.com.xmlparser;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Deo on 2016/3/10.
 */
class Object2StringHelper {

    static boolean isElement(Class type) {
        return (type == Boolean.class
                || type == boolean.class
                || type == Integer.class
                || type == int.class
                || type == Float.class
                || type == float.class
                || type == Double.class
                || type == double.class
                || type == Long.class
                || type == long.class
                || type == byte[].class
                || type == char[].class
                || type == String.class);
    }

    static String toXML(Object object) {
        if (object == null) {
            return null;
        }
        final Tag tag = object.getClass().getAnnotation(Tag.class);
        return toXML(object, getTagName(tag, object));
    }

    static String getTagName(Tag tag, Object object) {
        String name = tag.value();
        if (tag == null || name.length() == 0) {
            name = object.getClass().getSimpleName();
        }
        return name;
    }

    static String getTagName(Tag tag, Field field) {
        String name = tag.value();
        if (tag == null || name.length() == 0) {
            name = field.getName();
        }
        return name;
    }

    static String getAttributeName(Attribute attribute, Field field) {
        String name = attribute.value();
        if (attribute == null || name.length() == 0) {
            name = field.getName();
        }
        return name;
    }

    static String toXML(Object object, String tag) {
        final StringBuffer stringBuffer = new StringBuffer();
        final ArrayList<Field> attributeFields = new ArrayList<>();
        final ArrayList<Field> elementFields = new ArrayList<>();
        for (Field field : object.getClass().getDeclaredFields()) {
            final Tag tagAnnotation = field.getAnnotation(Tag.class);
            final Attribute attribute = field.getAnnotation(Attribute.class);
            final Element element = field.getAnnotation(Element.class);
            if (attribute != null) {
                attributeFields.add(field);
            } else if (element != null) {
                elementFields.add(field);
            } else if (tagAnnotation != null) {
                elementFields.add(field);
            } else {
                final Tag typeTag = field.getType().getAnnotation(Tag.class);
                if (typeTag != null) {
                    elementFields.add(field);
                }
            }
        }
        stringBuffer.append("<");
        stringBuffer.append(tag);
        for (Field field : attributeFields) {
            field.setAccessible(true);
            try {
                final Object value = field.get(object);
                if (isElement(field.getType())) {
                    final String name = getAttributeName(field.getAnnotation(Attribute.class), field);
                    stringBuffer.append(" " + name + "='" + (value != null ? value : "") + "'");
                } else if (field.getType() == AttributeMap.class) {
                    final AttributeMap map = (AttributeMap) value;
                    for (String name : map.keySet()) {
                        final String v = map.get(name);
                        stringBuffer.append(" " + name + "='" + (v != null ? v : "") + "'");
                    }
                }
            } catch (IllegalAccessException e) {
            }
        }
        //============================
        StringBuffer elementValue = new StringBuffer();
        for (Field elementField : elementFields) {
            elementField.setAccessible(true);
            try {
                if (isElement(elementField.getType())) {
                    final Object value = elementField.get(object);
                    elementValue.append(value != null ? value : "");
                } else if (elementField.getType() == ElementMap.class) {
                    final ElementMap map = (ElementMap) elementField.get(object);
                    for (String key : map.keySet()) {
                        final ArrayList<ElementValue> list = map.get(key);
                        for (ElementValue value : list) {
                            elementValue.append("<");
                            elementValue.append(key);
                            for (String attribute : value.attribute.keySet()) {
                                String attributeValue = value.attribute.get(attribute);
                                if (attributeValue == null) {
                                    attributeValue = "";
                                }
                                elementValue.append(" " + attribute + "='" + attributeValue + "'");
                            }
                            elementValue.append(getTagEndString(key, value.text));
                        }
                    }
                } else {
                    final Object o = elementField.get(object);
                    Tag tagAnnotation = elementField.getAnnotation(Tag.class);
                    if (tagAnnotation == null) {
                        tagAnnotation = elementField.getType().getAnnotation(Tag.class);
                    }
                    String name = tagAnnotation.value();
                    if (name.length() == 0) {
                        name = elementField.getName();
                    }
                    if (o != null) {
                        if (o instanceof Collection) {
                            for (Object item : (Collection) o) {
                                    elementValue.append(toXML(item, name));
                            }
                        } else {
                            elementValue.append(toXML(o, name));
                        }
                    }
                }
            } catch (IllegalAccessException e) {
            }
        }
        if (object instanceof ElementMap) {
            final ElementMap map = (ElementMap) object;
            for (String key : map.keySet()) {
                final ArrayList<ElementValue> list = map.get(key);
                for (ElementValue item : list) {
                    elementValue.append(toXML(item, key));
                }
            }
        }
        stringBuffer.append(getTagEndString(tag, elementValue.toString()));
        return stringBuffer.toString();
    }

    private static String getTagEndString(String tag, String value) {
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
