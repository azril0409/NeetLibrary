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
public class SAXPraserHelper extends DefaultHandler {
    final Object object;
    private final ArrayList<String> qNames = new ArrayList<>();
    private HashMap<String, Class> typeTemp = new HashMap<>();
    private HashMap<String, Collection> collectionTemp = new HashMap<>();
    private HashMap<String, Object> tagTemp = new HashMap<>();
    private HashMap<String, Field> attributeTemp = new HashMap<>();
    private HashMap<String, Field> elementTemp = new HashMap<>();
    private HashMap<String, ElementMap> elementMapTemp = new HashMap<>();

    public SAXPraserHelper(Object object) throws XMLParserException {
        this.object = object;
        final Tag tag = object.getClass().getAnnotation(Tag.class);
        String name;
        if (tag.value().length() == 0) {
            name = object.getClass().getSimpleName();
        } else {
            name = tag.value();
        }
        tagTemp.put(name + "_", object);
        final Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            analyzeField(object, field, name + "_");
        }
    }

    private void analyzeField(Object object, Field field, String xmlPath) throws XMLParserException {
        field.setAccessible(true);
        final Tag tag = field.getAnnotation(Tag.class);
        final Element element = field.getAnnotation(Element.class);
        final Attribute attribute = field.getAnnotation(Attribute.class);
        if (tag != null) {
            addTag(field,tag,xmlPath);
        } else if(element!=null){
            final Class type = field.getType();
            if (type == ElementMap.class) {
                try {
                    ElementMap map = new ElementMap();
                    field.set(object, map);
                    elementMapTemp.put(xmlPath, map);
                } catch (IllegalAccessException e) {
                    throw new XMLParserException(e.getMessage());
                }
            }else  if (Object2StringHelper.isElement(type)) {
                elementTemp.put(xmlPath, field);
            }
        } else if (attribute != null) {
            attributeTemp.put(xmlPath + attribute.value(), field);
        }else{
            final Tag typeTag = field.getType().getAnnotation(Tag.class);
            if (typeTag!=null){
                addTag(field,typeTag,xmlPath);
            }
        }
    }

    private void addTag(Field field, Tag tag, String xmlPath){
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
        }else{
            try {
                String name = tag.value();
                if (name.length() == 0) {
                    name = field.getName();
                }
                Object fieldObject;
                if(tag.model()==DefaultElement.class){
                    fieldObject = field.getType().newInstance();
                }else{
                    fieldObject = tag.model().newInstance();
                }
                String key = xmlPath + name + "_";
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
        }}

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
            String key = xmlPath + name + "_";
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
            field.set(object, Boolean.valueOf(value));
        } else if (field.getType() == boolean.class) {
            field.set(object, Boolean.valueOf(value));
        } else if (field.getType() == Integer.class) {
            field.set(object, Integer.valueOf(value));
        } else if (field.getType() == int.class) {
            field.set(object, Integer.valueOf(value));
        } else if (field.getType() == Float.class) {
            field.set(object, Float.valueOf(value));
        } else if (field.getType() == float.class) {
            field.set(object, Float.valueOf(value));
        } else if (field.getType() == Double.class) {
            field.set(object, Double.valueOf(value));
        } else if (field.getType() == double.class) {
            field.set(object, Double.valueOf(value));
        } else if (field.getType() == Long.class) {
            field.set(object, Long.valueOf(value));
        } else if (field.getType() == long.class) {
            field.set(object, Long.valueOf(value));
        } else if (field.getType() == byte[].class) {
            field.set(object, value.getBytes());
        } else if (field.getType() == char[].class) {
            field.set(object, value.toCharArray());
        } else if (field.getType() == String.class) {
            field.set(object, value);
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
            try {
                final Object object = tagTemp.get(key);
                final Field field = elementTemp.get(key);
                addFieldValue(field, object, element);
            } catch (Exception e) {
                throw new SAXException(e.getMessage());
            }
        } else if (elementMapTemp.containsKey(xmlPath)) {
            final ElementMap map = elementMapTemp.get(xmlPath);
            if(map.containsKey(qName)){
                final ElementValue elementValue = map.get(qName);
                elementValue.value = element;
            }
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        final String xmlPath = getKey(qNames);
        qNames.add(qName);
        final String key = getKey(qNames);
        if (collectionTemp.containsKey(key) && typeTemp.containsKey(key)) {
            try {
                final Class type = typeTemp.get(key);
                final Collection collection = collectionTemp.get(key);
                final Object tagObject = type.newInstance();
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
        } else if (elementMapTemp.containsKey(xmlPath)) {
            final ElementMap map = elementMapTemp.get(xmlPath);
            map.put(qName, new ElementValue());
        }

        final int length = attributes.getLength();
        for (int index = 0; index < length; index++) {
            if (attributeTemp.containsKey(key + attributes.getQName(index)) && tagTemp.containsKey(key)) {
                try {
                    final Object object = tagTemp.get(key);
                    final Field field = attributeTemp.get(key + attributes.getQName(index));
                    final String value = attributes.getValue(index);
                    addFieldValue(field, object, value);
                } catch (Exception e) {
                    throw new SAXException(e.getMessage());
                }
            } else if (elementMapTemp.containsKey(xmlPath)) {
                final ElementMap map = elementMapTemp.get(xmlPath);
                final ElementValue elementValue = map.get(qName);
                final String name = attributes.getQName(index);
                final String value = attributes.getValue(index);
                elementValue.addAttribute(name,value);
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        qNames.remove(qNames.size() - 1);
    }
}
