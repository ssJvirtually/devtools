package dev.ssjvirtually.util;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;

public final class XmlUtil {

    private XmlUtil() {
    }

    public static String beautify(String xml) {
        if (xml == null || xml.trim().isEmpty()) {
            return "";
        }
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StringWriter stringWriter = new StringWriter();
            StreamResult xmlOutput = new StreamResult(stringWriter);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            // This is needed to support indenting in some implementations
            transformerFactory.setAttribute("indent-number", 4);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid XML: " + e.getMessage());
        }
    }

    public static String minify(String xml) {
        if (xml == null || xml.trim().isEmpty()) {
            return "";
        }
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StringWriter stringWriter = new StringWriter();
            StreamResult xmlOutput = new StreamResult(stringWriter);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString().replaceAll(">\\s+<", "><").trim();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid XML: " + e.getMessage());
        }
    }
}
