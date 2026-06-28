package de.quoss.java.examples.xml.stream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class App {

    // public api methods
    
    public static void main(String[] args) {
        new App().run(args);
    }

    // private helper methods

    private Document createDocument(String filePath) {
        Document document;
        try (FileInputStream stream = new FileInputStream(filePath)) {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setNamespaceAware(true);
            documentBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            documentBuilderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            documentBuilderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            documentBuilderFactory.setXIncludeAware(false);
            documentBuilderFactory.setExpandEntityReferences(false);
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            return builder.parse(stream);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new IllegalStateException("Error creating document from file input stream: " + e.getMessage(), e);
        }
    }
    
    private void encodeUtf8TransformDomSource(CommandLine commandLine) {
        DOMSource domSource = new DOMSource(createDocument(getInputFilePath(commandLine)));
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            throw new IllegalStateException("Error creating transformer: " + e.getMessage(), e);
        }
        StreamResult streamResult = new StreamResult(new File(getOutputFilePath(commandLine)));
        try {
            transformer.transform(domSource, streamResult);
        } catch (TransformerException e) {
            throw new IllegalStateException("Error transforming dom: " + e.getMessage(), e);
        }
    }

    private String getInputFilePath(CommandLine commandLine) {
        String inputFilePath = commandLine.getOptionValue("ifp");
        if (inputFilePath == null) {
            throw new IllegalArgumentException("Input file path must not be null");
        }
        return inputFilePath;
    }

    private String getOutputFilePath(CommandLine commandLine) {
        String outputFilePath = commandLine.getOptionValue("ofp");
        if (outputFilePath == null) {
            throw new IllegalArgumentException("Output file path must not be null");
        }
        return outputFilePath;
    }

    private void run(String[] args) {
        Options options = new Options();
        options.addOption("f", "function", true, "Function to perform");
        options.addOption("ifp", "input-file-path", true, "Full path to input file");
        options.addOption("ofp", "output-file-path", true, "Full path to output file");
        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine;
        try {
            commandLine = parser.parse(options, args);
        } catch (ParseException e) {
            throw new IllegalStateException("Error parsing the command line: " + e.getMessage(), e);
        }
        String function = commandLine.getOptionValue("f");
        if (function == null) {
            throw new IllegalArgumentException("Function value must not be null");
        }
        switch (function) {
            case "encode-utf8-transform-dom-source":
                encodeUtf8TransformDomSource(commandLine);
                break;
            default:
                throw new IllegalArgumentException("Function value '" + function + "' is not supported");
        }
    }

}
