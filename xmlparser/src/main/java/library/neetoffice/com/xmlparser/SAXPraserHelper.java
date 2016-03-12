package library.neetoffice.com.xmlparser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Deo on 2016/3/1.
 */
class SAXPraserHelper extends DefaultHandler {
    final Object object;
    private final ArrayList<String> qNames = new ArrayList<>();
    private final HashMap<String, Class> typeTemp = new HashMap<>();
    private final HashMap<String, Collection> collectionTemp = new HashMap<>();
    private final HashMap<String, Object> tagTemp = new HashMap<>();
    private final HashMap<String, Field> attributeTemp = new HashMap<>();
    private final HashMap<String, Field> elementTemp = new HashMap<>();
    private final HashMap<String, AttributeMap> attributeMapTemp = new HashMap<>();
    private final HashMap<String, ElementMap> elementMapTemp = new HashMap<>();
    private final HashMap<String, StringBuffer> characters = new HashMap<>();

    SAXPraserHelper(Object object) throws XMLParserException {
        this.object = object;
        final Tag tag = object.getClass().getAnnotation(Tag.class);
        final String name = Object2StringHelper.getTagName(tag, object);
        final String key = name + "_";
        tagTemp.put(key, object);
        if (object instanceof ElementMap) {
            elementMapTemp.put(key, (ElementMap) object);
        }
        final Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            analyzeField(object, field, name + "_");
        }
    }


    private void analyzeField(Object object, Field field, String xmlPath) throws XMLParserException {
        field.setAccessible(true);
        if (object instanceof ElementMap) {
            elementMapTemp.put(xmlPath, (ElementMap) object);
        }
        final Tag tag = field.getAnnotation(Tag.class);
        final Element element = field.getAnnotation(Element.class);
        final Attribute attribute = field.getAnnotation(Attribute.class);
        if (tag != null) {
            final Class type = field.getType();
            if (type == Collection.class) {
                try {
                    addCollection(new ArrayList(), field, tag, xmlPath);
                } catch (IllegalAccessException e) {
                    throw new XMLParserException(e.getMessage());
                }
            } else if (type == List.class) {
                try {
                    addCollection(new ArrayList(), field, tag, xmlPath);
                } catch (IllegalAccessException e) {
                    throw new XMLParserException(e.getMessage());
                }
            } else if (type == ArrayList.class) {
                try {
                    addCollection(new ArrayList(), field, tag, xmlPath);
                } catch (IllegalAccessException e) {
                    throw new XMLParserException(e.getMessage());
                }
            } else if (type == Set.class) {
                try {
                    addCollection(new HashSet(), field, tag, xmlPath);
                } catch (IllegalAccessException e) {
                    throw new XMLParserException(e.getMessage());
                }
            } else if (type == HashSet.class) {
                try {
                    addCollection(new HashSet(), field, tag, xmlPath);
                } catch (IllegalAccessException e) {
                    throw new XMLParserException(e.getMessage());
                }
            } else if (type == TreeSet.class) {
                try {
                    addCollection(new TreeSet(), field, tag, xmlPath);
                } catch (IllegalAccessException e) {
                    throw new XMLParserException(e.getMessage());
                }
            } else {
                try {
                    final String name = Object2StringHelper.getTagName(tag, field);
                    final Object fieldObject;
                    if (tag.model() == DefaultElement.class) {
                        fieldObject = field.getType().newInstance();
                    } else {
                        fieldObject = tag.model().newInstance();
                    }
                    final String key = xmlPath + name + "_";
                    field.set(object, fieldObject);
                    tagTemp.put(key, fieldObject);
                    final Field[] fields = field.getType().getDeclaredFields();
                    for (Field f : fields) {
                        analyzeField(fieldObject, f, key);
                    }
                } catch (InstantiationException e) {
                    throw new XMLParserException(type.getName() + " doesn't have no-arg(default) constructor");
                } catch (IllegalAccessException e) {
                    throw new XMLParserException(e.getMessage());
                }
            }
        } else if (element != null) {
            final Class type = field.getType();
            if (Object2StringHelper.isElement(type)) {
                elementTemp.put(xmlPath, field);
            }
        } else if (attribute != null) {
            if (Object2StringHelper.isElement(field.getType())) {
                attributeTemp.put(xmlPath + Object2StringHelper.getAttributeName(attribute, field), field);
            } else if (field.getType() == AttributeMap.class) {
                try {
                    AttributeMap attributeMap = new AttributeMap();
                    field.set(object, attributeMap);
                    attributeMapTemp.put(xmlPath, attributeMap);
                } catch (IllegalAccessException e) {
                    throw new XMLParserException(e.getMessage());
                }
            }
        } else {
            final Class<?> typeClass = field.getType();
            final Tag typeTag = typeClass.getAnnotation(Tag.class);
            if (typeTag != null) {
                try {
                    String name = tag.value();
                    if (name.length() == 0) {
                        name = field.getName();
                    }
                    final Object fieldObject;
                    if (tag.model() == DefaultElement.class) {
                        fieldObject = field.getType().newInstance();
                    } else {
                        fieldObject = tag.model().newInstance();
                    }
                    final String key = xmlPath + name + "_";
                    field.set(object, fieldObject);
                    tagTemp.put(key, fieldObject);
                    final Field[] fields = field.getType().getDeclaredFields();
                    for (Field f : fields) {
                        analyzeField(fieldObject, f, key);
                    }
                } catch (InstantiationException e) {
                    throw new XMLParserException(typeClass.getName() + " doesn't have no-arg(default) constructor");
                } catch (IllegalAccessException e) {
                    throw new XMLParserException(e.getMessage());
                }
            }
        }
    }

    private void addCollection(Collection collection, Field field, Tag tag, String xmlPath) throws IllegalAccessException {
        field.set(object, collection);
        if (tag.model() != DefaultElement.class) {
            try {
                tag.model().newInstance();
            } catch (InstantiationException e) {
                throw new XMLParserException(tag.model() + " doesn't have no-arg(default) constructor");
            }
            String name = tag.value();
            if (name.length() == 0) {
                name = field.getName();
            }
            final String key = xmlPath + name + "_";
            typeTemp.put(key, tag.model());
            collectionTemp.put(key, collection);
        }
    }

    private static String getKey(ArrayList<String> list) {
        final StringBuffer stringBuffer = new StringBuffer();
        for (String s : list) {
            stringBuffer.append(s + "_");
        }
        return stringBuffer.toString();
    }


    private static void addFieldValue(Field field, Object object, String value) throws IllegalAccessException {
        if (field.getType() == Boolean.class) {
            field.set(object, Boolean.valueOf(value.trim()));
        } else if (field.getType() == boolean.class) {
            field.set(object, Boolean.valueOf(value.trim()));
        } else if (field.getType() == Integer.class) {
            try {
                field.set(object, Integer.valueOf(value.trim()));
            } catch (NumberFormatException e) {
                throw new XMLParserException(e.getMessage());
            }
        } else if (field.getType() == int.class) {
            try {
                field.set(object, Integer.valueOf(value.trim()));
            } catch (NumberFormatException e) {
                throw new XMLParserException(e.getMessage());
            }
        } else if (field.getType() == Float.class) {
            try {
                field.set(object, Float.valueOf(value.trim()));
            } catch (NumberFormatException e) {
                throw new XMLParserException(e.getMessage());
            }
        } else if (field.getType() == float.class) {
            try {
                field.set(object, Float.valueOf(value.trim()));
            } catch (NumberFormatException e) {
                throw new XMLParserException(e.getMessage());
            }
        } else if (field.getType() == Double.class) {
            try {
                field.set(object, Double.valueOf(value.trim()));
            } catch (NumberFormatException e) {
                throw new XMLParserException(e.getMessage());
            }
        } else if (field.getType() == double.class) {
            try {
                field.set(object, Double.valueOf(value.trim()));
            } catch (NumberFormatException e) {
                throw new XMLParserException(e.getMessage());
            }
        } else if (field.getType() == Long.class) {
            try {
                field.set(object, Long.valueOf(value.trim()));
            } catch (NumberFormatException e) {
                throw new XMLParserException(e.getMessage());
            }
        } else if (field.getType() == long.class) {
            try {
                field.set(object, Long.valueOf(value.trim()));
            } catch (NumberFormatException e) {
                throw new XMLParserException(e.getMessage());
            }
        } else if (field.getType() == byte[].class) {
            field.set(object, value.trim().getBytes());
        } else if (field.getType() == char[].class) {
            field.set(object, value.trim().toCharArray());
        } else if (field.getType() == String.class) {
            field.set(object, value.trim());
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        final String element = new String(ch, start, length);
        final String key = getKey(qNames);
        final String qName = qNames.get(qNames.size() - 1);
        final String xmlPath = key.substring(0, key.length() - qName.length() - 1);
        if (elementTemp.containsKey(key) && tagTemp.containsKey(key)) {
            final StringBuffer character;
            if (characters.containsKey(key)) {
                character = characters.get(key);
            } else {
                character = new StringBuffer();
                characters.put(key, character);
            }
            character.append(element);
        } else if (elementMapTemp.containsKey(xmlPath)) {
            final ElementMap map = elementMapTemp.get(xmlPath);
            if (map.containsKey(qName)) {
                try {
                    final ArrayList<ElementValue> list = map.get(qName);
                    final ElementValue elementValue = list.get(list.size() - 1);
                    if (elementValue.value != null) {
                        elementValue.value += element;
                    } else {
                        elementValue.value = element;
                    }
                } catch (IndexOutOfBoundsException e) {
                }
            }
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        Object newElement = null;
        final String xmlPath = getKey(qNames);
        qNames.add(qName);
        final String key = getKey(qNames);
        if (collectionTemp.containsKey(key) && typeTemp.containsKey(key)) {
            try {
                final Class type = typeTemp.get(key);
                final Collection collection = collectionTemp.get(key);
                final Object tagObject = type.newInstance();
                newElement = tagObject;
                collection.add(tagObject);
                tagTemp.put(key, tagObject);
                final Field[] fields = type.getDeclaredFields();
                for (Field f : fields) {
                    analyzeField(tagObject, f, key);
                }
            } catch (InstantiationException e) {
                throw new SAXException(e.getMessage());
            } catch (IllegalAccessException e) {
                throw new SAXException(e.getMessage());
            }
        } else if (elementTemp.containsKey(key) && tagTemp.containsKey(key)) {
            newElement = elementTemp.get(key);
        } else if (elementMapTemp.containsKey(xmlPath)) {
            final ElementMap map = elementMapTemp.get(xmlPath);
            newElement = new ElementValue();
            elementMapTemp.put(key, (ElementMap) newElement);
            if (map.containsKey(qName)) {
                final ArrayList<ElementValue> list = map.get(qName);
                list.add((ElementValue) newElement);
            } else {
                final ArrayList<ElementValue> list = new ArrayList<>();
                list.add((ElementValue) newElement);
                map.put(qName, list);
            }
        }
        if (newElement == null) {
            return;
        }
        final int length = attributes.getLength();
        for (int index = 0; index < length; index++) {
            final String attributeName = attributes.getQName(index);
            final String attributeKey = key + attributeName;
            if (attributeTemp.containsKey(attributeKey)) {
                try {
                    final Field field = attributeTemp.get(key + attributeName);
                    final String value = attributes.getValue(index);
                    if (field.getType() == AttributeMap.class) {
                        final AttributeMap map = (AttributeMap) field.get(object);
                        map.put(attributeName, value.trim());
                    } else {
                        addFieldValue(field, newElement, value);
                    }
                } catch (Exception e) {
                    throw new SAXException(e.getMessage());
                }
            } else if (attributeMapTemp.containsKey(key)) {
                final String value = attributes.getValue(index);
                final AttributeMap attributeMap = attributeMapTemp.get(key);
                attributeMap.put(attributeName, value.trim());
            } else if (elementMapTemp.containsKey(xmlPath)) {
                if (newElement instanceof ElementValue) {
                    final ElementValue elementValue = (ElementValue) newElement;
                    final String value = attributes.getValue(index);
                    elementValue.addAttribute(attributeName, value.trim());
                }
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        final String key = getKey(qNames);
        if (characters.containsKey(key)) {
            final Object object = tagTemp.get(key);
            Field field = elementTemp.get(key);
            StringBuffer stringBuffer = characters.get(key);
            try {
                addFieldValue(field, object, stringBuffer.toString());
            } catch (IllegalAccessException e) {
            }
            characters.remove(key);
        }
        qNames.remove(qNames.size() - 1);
    }
}
