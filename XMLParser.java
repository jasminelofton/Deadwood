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
        Element root = document.getDocumentElement();
        ArrayList<String> neighborNames = new ArrayList<>();
        NodeList locationsNodeList;
        Node location;
        NodeList locationNodeList;
        NodeList locationNeighbors;
        Node neighbor;
        NodeList neighborsNodeList;        
        String neighborName;
        NodeList temp;
        int loop;

        switch(locationName) {
            case "Casting Office":

                //!TESTER
                //temp = root.getChildNodes();                
                // for (int i = 0; i < temp.getLength(); i++) {
                //     System.out.println(temp.item(i).getNodeName());
                // }

                locationsNodeList = root.getElementsByTagName("office");

                // !TESTER
                // for (int i = 0; i < locationsNodeList.getLength(); i++) {
                //     System.out.println(locationsNodeList.item(i).getNodeName());
                // }

                location = locationsNodeList.item(0);

                locationNodeList = location.getChildNodes();
                //!TESTER
                // for (int i = 0; i < locationNodeList.getLength(); i++) {
                //     System.out.println(locationNodeList.item(i).getNodeName());
                // }

                loop = 0;
                while (!((neighbor = locationNodeList.item(loop)).getNodeName()).equals("neighbors")) {
                    loop++;
                }

                neighborsNodeList = neighbor.getChildNodes();

                for (int i = 0; i < neighborsNodeList.getLength(); i++) {
                    neighbor = neighborsNodeList.item(i);
                    if (neighbor.getNodeType() == 1) {
                        neighborName = neighbor.getAttributes().getNamedItem("name").getNodeValue();
                        neighborNames.add(neighborName);
                    }
                }
                break;
            case "Trailers":
                //!TESTER
                //temp = root.getChildNodes();                
                // for (int i = 0; i < temp.getLength(); i++) {
                //     System.out.println(temp.item(i).getNodeName());
                // }

                locationsNodeList = root.getElementsByTagName("trailer");

                // !TESTER
                // for (int i = 0; i < locationsNodeList.getLength(); i++) {
                //     System.out.println(locationsNodeList.item(i).getNodeName());
                // }

                location = locationsNodeList.item(0);

                locationNodeList = location.getChildNodes();
                //!TESTER
                // for (int i = 0; i < locationNodeList.getLength(); i++) {
                //     System.out.println(locationNodeList.item(i).getNodeName());
                // }

                loop = 0;
                while (!((neighbor = locationNodeList.item(loop)).getNodeName()).equals("neighbors")) {
                    loop++;
                }

                neighborsNodeList = neighbor.getChildNodes();

                for (int i = 0; i < neighborsNodeList.getLength(); i++) {
                    neighbor = neighborsNodeList.item(i);
                    if (neighbor.getNodeType() == 1) {
                        neighborName = neighbor.getAttributes().getNamedItem("name").getNodeValue();
                        neighborNames.add(neighborName);
                    }
                }
                break;
            default:
                //!TESTER
                //temp = root.getChildNodes();                
                // for (int i = 0; i < temp.getLength(); i++) {
                //     System.out.println(temp.item(i).getNodeName());
                // }

                locationsNodeList = root.getElementsByTagName("set");

                // !TESTER
                // for (int i = 0; i < locationsNodeList.getLength(); i++) {
                //     System.out.println(locationsNodeList.item(i).getNodeName());
                // }

                location = locationsNodeList.item(0);

                locationNodeList = location.getChildNodes();
                //!TESTER
                // for (int i = 0; i < locationNodeList.getLength(); i++) {
                //     System.out.println(locationNodeList.item(i).getNodeName());
                // }

                loop = 0;
                while (!((neighbor = locationNodeList.item(loop)).getNodeName()).equals("neighbors")) {
                    loop++;
                }

                neighborsNodeList = neighbor.getChildNodes();

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
