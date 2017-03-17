package library.neetoffice.com.xmlparser;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


/**
 * Created by Deo on 2016/3/1.
 */
public class XMLParser {
    public void fromXML(String xml, Object object) throws XMLParserException {
        try {
            final SAXParserFactory factory = SAXParserFactory.newInstance();
            final SAXParser parser = factory.newSAXParser();
            final XMLReader xmlReader = parser.getXMLReader();
            final XMLParserHelper handler = new XMLParserHelper(object);
            xmlReader.setContentHandler(handler);
            final InputSource input = new InputSource(new ByteArrayInputStream(xml.getBytes()));
            xmlReader.parse(input);
        } catch (Exception e) {
            throw new XMLParserException(e.getMessage());
        }
    }

    public <T> T fromXML(String xml, Class<T> classOfT) throws XMLParserException {
        try {
            final Object object = classOfT.newInstance();
            fromXML(xml, object);
            return (T) object;
        } catch (InstantiationException e) {
            throw new XMLParserException(classOfT.getName() + " doesn't have no-arg(default) constructor");
        } catch (Exception e) {
            throw new XMLParserException(e.getMessage());
        }
    }

    public String toXML(Object object) throws XMLParserException {
        return Object2StringHelper.toXML(object);
    }

    public <T> T read(String path, Class<T> classOfT) throws XMLParserException, IOException {
        return read(new File(path), classOfT);
    }

    public void read(String path, Object object) throws XMLParserException, IOException {
        read(new File(path), object);
    }

    public <T> T read(File file, Class<T> classOfT) throws XMLParserException, IOException {
        final FileInputStream fileInputStream = new FileInputStream(file);
        return read(fileInputStream, classOfT);
    }

    public void read(File file, Object object) throws XMLParserException, IOException {
        final FileInputStream fileInputStream = new FileInputStream(file);
        read(fileInputStream, object);
    }

    public <T> T read(InputStream inputStream, Class<T> classOfT) throws XMLParserException, IOException {
        final String xml = readXml(inputStream);
        return fromXML(xml, classOfT);
    }

    public void read(InputStream inputStream, Object object) throws XMLParserException, IOException {
        final String xml = readXml(inputStream);
        fromXML(xml, object);
    }

    private String readXml(InputStream inputStream) throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String xml;
        try {
            int i = 0;
            byte[] buffer = new byte[1024];
            while ((i = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, i);
            }
            xml = byteArrayOutputStream.toString();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
            }
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
            }
        }
        return xml;
    }
}
