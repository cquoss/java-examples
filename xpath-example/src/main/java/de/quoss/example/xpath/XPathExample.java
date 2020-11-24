package de.quoss.example.xpath;

import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XPathExample {

	private void run() throws Exception {
		
		String methodName = "run()";
		
		Document document = loadXml("input.xml");
		
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();

		processExpression(xpath, document, "/*");
		processExpression(xpath, document, "/Envelope/*");
		processExpression(xpath, document, "/soap:Envelope/*");
		
	}

	private void processExpression(final XPath xpath, final Document document, final String expression) 
			throws Exception {
		String methodName = "processExpression(String)";
		System.out.format("%s [expression=%s]%n", methodName, expression);
		Object object = xpath.evaluate(expression, document, XPathConstants.NODESET);
		if (object instanceof NodeList) {
			NodeList nodeList = (NodeList) object;
			iterateNodes(nodeList);
		}
	}
	
	private void iterateNodes(final NodeList nodeList) {
		String methodName = "iterateNodes(NodeList)";
		int length = nodeList.getLength();
		for (int i = 0; i < length; i++) {
			Node node = nodeList.item(i);
			String localName = node.getLocalName();
			String namespaceURI = node.getNamespaceURI();
			String nodeName = node.getNodeName();
			String nodeValue = node.getNodeValue();
			String prefix = node.getPrefix();
			String textContent = node.getTextContent();
			System.out.format("%s [node=%s]%n", methodName, node);
			System.out.format("%s   [localName=%s]%n", methodName, localName);
			System.out.format("%s   [namespaceURI=%s]%n", methodName, namespaceURI);
			System.out.format("%s   [nodeName=%s]%n", methodName, nodeName);
			System.out.format("%s   [nodeValue=%s]%n", methodName, nodeValue);
			System.out.format("%s   [prefix=%s]%n", methodName, prefix);
			System.out.format("%s   [textContent=%s]%n", methodName, textContent);
			iterateNodes(node.getChildNodes());
		}
	}
	
	private Document loadXml(final String name) throws Exception {
		
		String methodName = "loadXml(String)";
		
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(name);
		if (inputStream == null) {
			throw new Exception(String.format("Resource %s not found in class path.", name));
		}
		byte[] buf = new byte[1024];
		int bytesRead;
		StringBuilder stringBuilder = new StringBuilder();
		while ((bytesRead = inputStream.read(buf)) != -1) {
			byte[] buf2 = new byte[bytesRead];
			System.arraycopy(buf, 0, buf2, 0, bytesRead);
			stringBuilder.append(new String(buf2));
		}
		
		System.out.format("%s [xml=%s]%n", methodName, stringBuilder.toString());
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setNamespaceAware(true);
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		StringReader stringReader = new StringReader(stringBuilder.toString());
		InputSource inputSource = new InputSource(stringReader);
		return documentBuilder.parse(inputSource);
		
	}
	
	public static void main(String[] args) {
		try {
			new XPathExample().run();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

}
