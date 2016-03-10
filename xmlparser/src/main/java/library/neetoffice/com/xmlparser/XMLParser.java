package library.neetoffice.com.xmlparser;


import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


/**
 * Created by Deo on 2016/3/1.
 */
public class XMLParser {
    public <T> T fromXML(String xml, Class<T> classOfT) throws XMLParserException {
        Object object = null;
        try {
            object = classOfT.newInstance();
            final SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = null;
            parser = factory.newSAXParser();
            final XMLReader xmlReader = parser.getXMLReader();
            SAXPraserHelper handler = new SAXPraserHelper(object);
            xmlReader.setContentHandler(handler);
            final InputSource input = new InputSource(new ByteArrayInputStream(xml.getBytes()));
            xmlReader.parse(input);
            object = handler.object;
            handler = null;
            return (T) object;
        } catch (RuntimeException e) {
            throw new XMLParserException(e.getMessage());
        } catch (InstantiationException e) {
            throw new XMLParserException(classOfT.getName() + " doesn't have no-arg(default) constructor");
        } catch (Exception e) {
            throw new XMLParserException(e.getMessage());
        }
    }

    public String toXML(Object object) {
        return Object2StringHelper.toXML(object);
    }
}
