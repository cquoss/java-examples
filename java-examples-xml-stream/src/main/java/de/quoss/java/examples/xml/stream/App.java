package de.quoss.java.examples.xml.stream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class App {

    // public api methods
    
    public static void main(String[] args) {
        new App().run(args);
    }

    // private helper methods

    private Document createDocument(CommandLine commandLine) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setNamespaceAware(true);
            documentBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            documentBuilderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            documentBuilderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            documentBuilderFactory.setXIncludeAware(false);
            documentBuilderFactory.setExpandEntityReferences(false);
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            Document document;
            try (FileInputStream inputStream = new FileInputStream(getInputFilePath(commandLine));
                    InputStreamReader reader = new InputStreamReader(inputStream, Charset.forName(getInputEncoding(commandLine)))) {
                document = builder.parse(new InputSource(reader));
            }
            System.out.println("document.input-encoding: " + document.getInputEncoding());
            System.out.println("document.xml-encoding: " + document.getXmlEncoding());
            System.out.println("document.xml-standalone: " + document.getXmlStandalone());
            System.out.println("document.xml-version: " + document.getXmlVersion());
            document.getDocumentElement().normalize();
            return document;
        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new IllegalStateException("Error creating document from file input stream: " + e.getMessage(), e);
        }
    }
    
    private Transformer createTransformer() {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
        } catch (TransformerConfigurationException e) {
            throw new IllegalStateException("Error creating transformer: " + e.getMessage(), e);
        }
        return transformer;
    }
    
    private void encodeUtf8TransformDomSource(CommandLine commandLine) {
        try (FileOutputStream outputStream = new FileOutputStream(getOutputFilePath(commandLine));
                OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            createTransformer().transform(new DOMSource(createDocument(commandLine)), new StreamResult(writer));
        } catch (IOException |TransformerException e) {
            throw new IllegalStateException("Error transforming dom: " + e.getMessage(), e);
        }
    }

    private void encodeUtf8TransformStreamSource(CommandLine commandLine) {
        try (FileInputStream inputStream = new FileInputStream(getInputFilePath(commandLine));
                InputStreamReader reader = new InputStreamReader(inputStream, Charset.forName(getInputEncoding(commandLine)));
                FileOutputStream outputStream = new FileOutputStream(getOutputFilePath(commandLine));
                OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            createTransformer().transform(new StreamSource(reader), new StreamResult(writer));
        } catch (IOException |TransformerException e) {
            throw new IllegalStateException("Error transforming stream source: " + e.getMessage(), e);
        }
    }

    private String getInputEncoding(CommandLine commandLine) {
        String inputEncoding = commandLine.getOptionValue("ie");
        if (inputEncoding == null) {
            throw new IllegalArgumentException("Input encoding must not be null");
        }
        return inputEncoding;
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

    private String getXmlFromFile(CommandLine commandLine) {
        try (FileInputStream stream = new FileInputStream(getInputFilePath(commandLine))) {
            return new String(readAllBytes(stream), Charset.forName(getInputEncoding(commandLine)));
        } catch (IOException e) {
                throw new IllegalStateException("Error reading input file: " + e.getMessage(), e);
        }
    }
    
    private byte[] readAllBytes(InputStream stream) throws IOException {
        byte[] buf = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int bytesRead;
        while ((bytesRead = stream.read(buf)) != -1) {
            baos.write(buf, 0, bytesRead);
        }
        return baos.toByteArray();
    }
    
    private void run(String[] args) {
        Options options = new Options();
        options.addOption("f", "function", true, "Function to perform");
        options.addOption("ie", "input-encoding", true, "Encoding of input file");
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
            case "encode-utf8-transform-stream-source":
                encodeUtf8TransformStreamSource(commandLine);
                break;
            default:
                throw new IllegalArgumentException("Function value '" + function + "' is not supported");
        }
    }

}
