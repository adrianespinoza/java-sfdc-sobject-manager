package aveh.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import aveh.sobjects.Fields;
import aveh.sobjects.SObject;

public class SObjectXmlParser {
    private static DocumentBuilder docBuilder;

    static {
        init();
    }

    public static SObject readSObject(String sObjectApiName, String path) {
        Document doc;
        SObject sObj = null;
        List<Fields> fieldsList;
        try {
            doc = docBuilder.parse(new File(path));
            doc.getDocumentElement().normalize();
            NodeList nodesRoot = doc.getDocumentElement().getElementsByTagName("fields");

            fieldsList = new ArrayList<Fields>();
            for (int i = 0; i < nodesRoot.getLength(); i++) {
                Element xmlElem = (Element)nodesRoot.item(i);
                Fields field = buildFields(xmlElem);
                if (field != null) {
                    fieldsList.add(field);
                }
            }
            sObj = new SObject();
            sObj.fullName = sObjectApiName;
            sObj.fields = fieldsList;
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sObj;
    }

    private static Fields buildFields(Element xmlElement) {
        Fields field = null;
        Map<String, Object> fieldDescription = new HashMap<String, Object>();
        NodeList childNode = xmlElement.getChildNodes();

        field = new Fields();

        for (int i = 0; i < childNode.getLength(); i++) {
            String key = childNode.item(i).getNodeName();
            if (!key.equals("#text")) {
            if(key.equals("picklist")) {
                NodeList childChildNode = ((Element) childNode.item(i)).getElementsByTagName("picklistValues");
                List<Fields.PicklistValues> picklistValeus = new ArrayList<Fields.PicklistValues>();
                for (int j = 0; j < childChildNode.getLength(); j++) {
                    Fields.PicklistValues persist = field.new PicklistValues();
                    persist.fullName =  ((Element) childChildNode.item(j)).getElementsByTagName("fullName").item(0).getTextContent();
                    persist.isdefault =  ((Element) childChildNode.item(j)).getElementsByTagName("default").item(0).getTextContent();;
                    picklistValeus.add(persist);
                }
                fieldDescription.put(key, picklistValeus);
            } else {
                String val = childNode.item(i).getTextContent();
                fieldDescription.put(key, val);
            }
            }
        }
        field.fieldDescription = fieldDescription;
        return field;
    }

    private static void init() {
        DocumentBuilderFactory docBuildFactory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = docBuildFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}
