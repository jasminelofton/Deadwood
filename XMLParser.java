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

    private Node returnLocationNodeListShortCut(Document document, String locationNodeName, String locationAttrName, String type) {
        Element root = document.getDocumentElement();
        NodeList locationsNodeList; // list of all objects partaining to an element name "set" "office" "trailer"
        Node location; // node of the chosen object <set name="Train Station">
        NodeList locationNodeList; // child nodes of the chosen object <neighbors> <take> <parts>
        Node nodeType;
        int loop;
        //!TESTER
        //int temp = root.getChildNodes();                
        // for (int i = 0; i < temp.getLength(); i++) {
        //     System.out.println(temp.item(i).getNodeName());
        // }

        locationsNodeList = root.getElementsByTagName(locationNodeName); //set, trailer,office

        // !TESTER
        // for (int i = 0; i < locationsNodeList.getLength(); i++) {
        //     System.out.println(locationsNodeList.item(i).getNodeName());
        // }

        // Only partaining to an acting set, this will ensure we recieve the right set.
        if (locationAttrName != null) {
            loop = 0;
            while (!(location = locationsNodeList.item(loop)).getAttributes().getNamedItem("name").getNodeValue().equals(locationAttrName)) {
                loop++;
            }
        } else {
            loop = 0;
            while (!((location = locationsNodeList.item(loop)).getNodeName()).equals(locationNodeName)) {
                loop++;
            }
        }

        locationNodeList = location.getChildNodes();

        //!TESTER
        // for (int i = 0; i < locationNodeList.getLength(); i++) {
        //     System.out.println(locationNodeList.item(i).getNodeName());
        // }
        loop = 0;
        while (!(nodeType = locationNodeList.item(loop)).getNodeName().equals(type)) {
            loop++;
        }
        System.out.println(nodeType.getNodeName());
        return nodeType;
    }

    public ArrayList<String> retrieveLocationNames(Document document) throws Exception {
        Element root = document.getDocumentElement();
        NodeList locations = root.getChildNodes();

        // !TESTER
        // for (int i = 0; i < locations.getLength(); i++) {
        //     System.out.println(locations.item(i).getNodeType());
        // }
        // System.out.println(locations.item(1).getNodeName());

        ArrayList<String> locationNames = new ArrayList<>();

        for (int i =0; i < locations.getLength(); i++) {
            Node location = locations.item(i);

            // !This distinguishes between ELEMENT_NODE and TEXT_NODE
            // ELEMENT_NODE (what we want to grab) is classified by 1.
            // TEXT_NODE (what we dont want) is classified by 3.
            // TEXT_NODES are spaces and new lines between elements.
            // ELEMENT_NODE are the <> that we should only grab.
            // Trying to logically work with a TEXT_NODE gives a null error!
            if (location.getNodeType() == 1) {
                if (location.getAttributes().getNamedItem("name") != null) {
                    String locationName = location.getAttributes().getNamedItem("name").getNodeValue();
                    locationNames.add(locationName);
                } else {
                    locationNames.add(location.getNodeName());
                }
            }
        }
        return locationNames;
    }

    public ArrayList<String> retrieveNeighborsNames(Document document, String locationName) throws Exception {
        ArrayList<String> neighborNames = new ArrayList<>();
        Node neighbors;
        Node neighbor;
        NodeList neighborsNodeList;        
        String neighborName;

        switch(locationName) {
            case "Casting Office":
                neighbors = returnLocationNodeListShortCut(document, "office", null, "neighbors");
                neighborsNodeList = neighbors.getChildNodes();

                for (int i = 0; i < neighborsNodeList.getLength(); i++) {
                    neighbor = neighborsNodeList.item(i);
                    if (neighbor.getNodeType() == 1) {
                        neighborName = neighbor.getAttributes().getNamedItem("name").getNodeValue();
                        neighborNames.add(neighborName);
                    }
                }
                break;
            case "Trailers":
                neighbors = returnLocationNodeListShortCut(document, "trailer", null, "neighbors");
                neighborsNodeList = neighbors.getChildNodes();

                for (int i = 0; i < neighborsNodeList.getLength(); i++) {
                    neighbor = neighborsNodeList.item(i);
                    if (neighbor.getNodeType() == 1) {
                        neighborName = neighbor.getAttributes().getNamedItem("name").getNodeValue();
                        neighborNames.add(neighborName);
                    }
                }
                break;
            default:
                neighbors = returnLocationNodeListShortCut(document, "set", locationName, "neighbors");
                neighborsNodeList = neighbors.getChildNodes();

                for (int i = 0; i < neighborsNodeList.getLength(); i++) {
                    neighbor = neighborsNodeList.item(i);
                    if (neighbor.getNodeType() == 1) {
                        neighborName = neighbor.getAttributes().getNamedItem("name").getNodeValue();
                        neighborNames.add(neighborName);
                    }
                }
                break;
        }
        return neighborNames;
    }

}
