package de.quoss.example.xpathjdk;

import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Clemens Quoss
 */
public class XPAthJdkExample {
    
    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(XPAthJdkExample.class);

    private static final String XML_DOCUMENT = String.format(
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>%n"
          + "<persons>%n"
          + "    <person>%n"
          + "        <name>PersonA</name>%n"
          + "    </person>%n"
          + "    <person>%n"
          + "        <name>PersonB</name>%n"
          + "    </person>%n"
          + "</persons>%n"
    );
    
    private void run() {
        
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        try {
            XPathExpression expression = xpath.compile("person");
            Document document = stringToDocument(XML_DOCUMENT);
            Object object = expression.evaluate(document, XPathConstants.NODESET);
            if (object instanceof NodeList) {
                NodeList list = (NodeList) object;
                int length = list.getLength();
                LOGGER.info(String.format("run() [length=%s]", length));
            }
        } catch (IOException | ParserConfigurationException | SAXException
                | XPathExpressionException e) {
            LOGGER.error("", e);
        }
        
    }
    
    private Document stringToDocument(final String xmlDocument)
            throws IOException, ParserConfigurationException, SAXException {
        StringReader reader = new StringReader(xmlDocument);
        InputSource source = new InputSource(reader);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document result = builder.parse(source);
        return result;
    }
    
    public static final void main(String[] args) {
        
        new XPAthJdkExample().run();
        
    }
    
}
