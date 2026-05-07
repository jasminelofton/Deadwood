import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParser {
    
    public Document newBoardDoc(String XMLBoardFile) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = null;

        document = db.parse(XMLBoardFile);
        return document;
    }

    public ArrayList<String> retrieveLocationNames(Document document) throws Exception{
        Element root = document.getDocumentElement();
        NodeList locations = root.getElementsByTagName("*");
        ArrayList<String> locationNames = new ArrayList<>();

        for (int i =0; i < locations.getLength(); i++) {
            Node location = locations.item(i);

            if (location.getAttributes().getNamedItem("name") != null) {
                locationNames.add(location.getAttributes().getNamedItem("name").getNodeValue());
            } else {
                locationNames.add(location.getNodeName());
            }
        }
        return locationNames;
    }

}
