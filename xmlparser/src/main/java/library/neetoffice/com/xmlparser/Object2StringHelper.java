package library.neetoffice.com.xmlparser;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Deo on 2016/3/10.
 */
class Object2StringHelper {

    static boolean isContent(Class type) {
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

    static String toXML(Object object) throws XMLParserException {
        if (object == null) {
            return null;
        }
        final Element element = object.getClass().getAnnotation(Element.class);
        if (element == null) {
            throw new XMLParserException("root class dosn't have @Element");
        }
        return toXML(object, getElementName(element, object));
    }

    static String getElementName(Element element, Object object) {
        if (element != null && element.value().length() > 0) {
            return element.value();
        } else {
            return object.getClass().getSimpleName();
        }
    }

    static String getElementName(Element element, Field field) {
        if (element != null && element.value().length() > 0) {
            return element.value();
        } else {
            return field.getName();
        }
    }

    static String getAttributeName(Attribute attribute, Field field) {
        String name = attribute.value();
        if (attribute == null || name.length() == 0) {
            name = field.getName();
        }
        return name;
    }

    static String toXML(Object object, String elementName) {
        final StringBuffer stringBuffer = new StringBuffer();
        final ArrayList<Field> attributeFields = new ArrayList<>();
        final ArrayList<Field> elementFields = new ArrayList<>();
        final ArrayList<Class<?>> classes = new ArrayList<>();
        Class<?> c = object.getClass();
        while (c != null) {
            classes.add(c);
            c = c.getSuperclass();
        }
        final int count = classes.size();
        for (int i = 0; i < count; i++) {
            final Class<?> cls = classes.get(count - i - 1);
            for (Field field : cls.getDeclaredFields()) {
                final Element elementAnnotation = field.getAnnotation(Element.class);
                final Attribute attribute = field.getAnnotation(Attribute.class);
                final Content content = field.getAnnotation(Content.class);
                if (attribute != null) {
                    attributeFields.add(field);
                } else if (content != null) {
                    elementFields.add(field);
                } else if (elementAnnotation != null) {
                    elementFields.add(field);
                } else {
                    final Element typeElement = field.getType().getAnnotation(Element.class);
                    if (typeElement != null) {
                        elementFields.add(field);
                    }
                }
            }
        }
        stringBuffer.append("<");
        stringBuffer.append(elementName);
        for (Field field : attributeFields) {
            field.setAccessible(true);
            try {
                final Object value = field.get(object);
                if (isContent(field.getType())) {
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
            field.setAccessible(false);
        }
        //============================
        final StringBuffer elementValue = new StringBuffer();
        for (Field elementField : elementFields) {
            elementField.setAccessible(true);
            try {
                if (isContent(elementField.getType())) {
                    if (elementField.getAnnotation(Element.class) != null) {
                        final Element tagAnnotation = elementField.getAnnotation(Element.class);
                        final String name = getElementName(tagAnnotation, elementField);
                        final Object value = elementField.get(object);
                        final String v = value != null ? String.valueOf(value) : "";
                        if (v.length() == 0) {
                            elementValue.append(String.format("<%s />", name));
                        } else {
                            elementValue.append(String.format("<%s>%s</%s>", name, v, name));
                        }
                    } else {
                        final Object value = elementField.get(object);
                        elementValue.append(value != null ? value : "");
                    }
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
                    final Element tagAnnotation = elementField.getAnnotation(Element.class);
                    final String name = getElementName(tagAnnotation, elementField);
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
            elementField.setAccessible(false);
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
        if(elementValue.length()==0){
            elementValue.append(object);
        }
        stringBuffer.append(getTagEndString(elementName, elementValue.toString()));
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
