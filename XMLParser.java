import java.util.ArrayList;
import java.util.HashMap;

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
        //!TESTER
        //System.out.println(nodeType.getNodeName());
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

    public Area retrieveRoomArea(Document document, String locationName) throws Exception {
        Node areaNode;
        
        switch(locationName) {
            case "Casting Office":
            case "office":
                areaNode = returnLocationNodeListShortCut(document, "office", null, "area");
                break;
            case "Trailers":
            case "trailer":
                areaNode = returnLocationNodeListShortCut(document, "trailer", null, "area");
                break;
            default:
                areaNode = returnLocationNodeListShortCut(document, "set", locationName, "area");
                break;
        }
        
        if (areaNode != null && areaNode.getNodeType() == Node.ELEMENT_NODE) {
            Element areaElement = (Element) areaNode;
            int x = Integer.parseInt(areaElement.getAttribute("x"));
            int y = Integer.parseInt(areaElement.getAttribute("y"));
            int h = Integer.parseInt(areaElement.getAttribute("h"));
            int w = Integer.parseInt(areaElement.getAttribute("w"));
            
            return new Area(x, y, h, w);
        }
        
        return null;
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

    public ArrayList<Take> retrieveActingSetTakes(Document document, String locationName) throws Exception {
        ArrayList<Take> takes = new ArrayList<>();
 
        Node takesNode = returnLocationNodeListShortCut(document, "set", locationName, "takes");
        NodeList takesNodeList = takesNode.getChildNodes();
 
        for (int i = 0; i < takesNodeList.getLength(); i++) {
            Node takeNode = takesNodeList.item(i);
 
            if (takeNode.getNodeType() != Node.ELEMENT_NODE) continue;
 
            Element takeElement = (Element) takeNode;
            int number = Integer.parseInt(takeElement.getAttribute("number"));
 
            Area area = null;
            NodeList areaNodes = takeElement.getElementsByTagName("area");
            if (areaNodes.getLength() > 0) {
                Element areaElement = (Element) areaNodes.item(0);
                int x = Integer.parseInt(areaElement.getAttribute("x"));
                int y = Integer.parseInt(areaElement.getAttribute("y"));
                int h = Integer.parseInt(areaElement.getAttribute("h"));
                int w = Integer.parseInt(areaElement.getAttribute("w"));
                area = new Area(x, y, h, w);
            }
 
            takes.add(new Take(number, area));
        }
 
        return takes;
    }


    public ArrayList<String> retrieveActingSetParts (Document document, String locationName, String attr) throws Exception {
        Node parts;
        NodeList partsNodeList;
        Node part;
        String partName;
        ArrayList<String> partNames = new ArrayList<>();

        parts = returnLocationNodeListShortCut(document, "set", locationName, "parts");
        partsNodeList = parts.getChildNodes();

        for (int i = 0; i < partsNodeList.getLength(); i++) {
            part = partsNodeList.item(i);
            if (part.getNodeType() == 1) {
                try {
                partName = part.getAttributes().getNamedItem(attr).getNodeValue();                    
                } catch (Exception e) {
                    partName = part.getTextContent();
                }                  
                partNames.add(partName);
            }
        }
        return partNames;
    }

    public ArrayList<Integer> retrieveActingSetPartsAsIntegers(Document document, String locationName, String attr) throws Exception {
        Node parts;
        NodeList partsNodeList;
        Node part;
        String partName;
        ArrayList<Integer> partLevels = new ArrayList<>();

        parts = returnLocationNodeListShortCut(document, "set", locationName, "parts");
        partsNodeList = parts.getChildNodes();

        for (int i = 0; i < partsNodeList.getLength(); i++) {
            part = partsNodeList.item(i);
            
            if (part.getNodeType() == Node.ELEMENT_NODE) { 
                try {
                    partName = part.getAttributes().getNamedItem(attr).getNodeValue();                    
                } catch (Exception e) {
                    partName = part.getTextContent();
                }                  
                
                try {
                    Integer level = Integer.valueOf(partName.trim());
                    partLevels.add(level);
                } catch (NumberFormatException e) {
                    System.err.println("could not parse " + partName + " as an integer");
                }
            }
        }
        return partLevels;
    }

    public ArrayList<String> retrieveCastingOfficeParts(Document document, String locationName, String attr) throws Exception {
        Node upgrades;
        NodeList upgradesNodeList;
        Node upgrade;
        String upgradeValue;
        ArrayList<String> upgradeValues = new ArrayList<>();

        upgrades = returnLocationNodeListShortCut(document, "office", null, "upgrades");
        upgradesNodeList = upgrades.getChildNodes();

        for (int i = 0; i < upgradesNodeList.getLength(); i++) {
            upgrade = upgradesNodeList.item(i);
            if (upgrade.getNodeType() == 1) {
                upgradeValue = upgrade.getAttributes().getNamedItem(attr).getNodeValue();
                upgradeValues.add(upgradeValue);
            }
        }
        System.out.println(upgradeValues);
        return upgradeValues;       
    }
    

    /**
     * CArd Parsign4 
     */


    public Document newCardsDoc(String XMLCardsFile) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(XMLCardsFile);
        return document;
    }
 
    public ArrayList<SceneCard> parseCards(Document document) throws Exception {
        ArrayList<SceneCard> cards = new ArrayList<>();
        Element root = document.getDocumentElement();
        NodeList cardNodes = root.getElementsByTagName("card");
 
        for (int i = 0; i < cardNodes.getLength(); i++) {
            Node cardNode = cardNodes.item(i);
            
            if (cardNode.getNodeType() == Node.ELEMENT_NODE) {
                Element cardElement = (Element) cardNode;
                
                String name = cardElement.getAttribute("name");
                String img = cardElement.getAttribute("img");
                int budget = Integer.parseInt(cardElement.getAttribute("budget"));
                Scene scene = parseScene(cardElement);
                ArrayList<Role> roles = parseRoles(cardElement);
                
                SceneCard card = new SceneCard(name, img, budget, scene, roles);

                cards.add(card);
            }
        }
        
        return cards;
    }

    public Deck createDeck(Document document) throws Exception {
        ArrayList<SceneCard> cards = parseCards(document);
        Deck deck = new Deck(cards);

        return deck;
    }

    private Scene parseScene(Element cardElement) {
        NodeList sceneNodes = cardElement.getElementsByTagName("scene");
        
        if (sceneNodes.getLength() > 0) {
            Element sceneElement = (Element) sceneNodes.item(0);
            int number = Integer.parseInt(sceneElement.getAttribute("number"));
            String description = sceneElement.getTextContent().trim();
            return new Scene(number, description);
        }
        
        return null;
    }
 
    private ArrayList<Role> parseRoles(Element cardElement) {
        ArrayList<Role> roles = new ArrayList<>();
        NodeList partNodes = cardElement.getElementsByTagName("part");
        
        for (int i = 0; i < partNodes.getLength(); i++) {
            Node partNode = partNodes.item(i);
            
            if (partNode.getNodeType() == Node.ELEMENT_NODE) {
                Element partElement = (Element) partNode;
                
                String name = partElement.getAttribute("name");
                int level = Integer.parseInt(partElement.getAttribute("level"));

                String line = parseLine(partElement);

                
                Role part = new Role(name, level, line, true); // only using to retriev on card roles no, onCard attribute in xml either
                
                // Area area = parseArea(partElement);
                // part.setArea(area);
                
                roles.add(part);
            }
        }
        
        return roles;
    }
 
    private Area parseArea(Element partElement) {
        NodeList areaNodes = partElement.getElementsByTagName("area");
        
        if (areaNodes.getLength() > 0) {
            Element areaElement = (Element) areaNodes.item(0);
            int x = Integer.parseInt(areaElement.getAttribute("x"));
            int y = Integer.parseInt(areaElement.getAttribute("y"));
            int h = Integer.parseInt(areaElement.getAttribute("h"));
            int w = Integer.parseInt(areaElement.getAttribute("w"));
            return new Area(x, y, h, w);
        }
        
        return null;
    }
 
    private String parseLine(Element partElement) {
        NodeList lineNodes = partElement.getElementsByTagName("line");
        
        if (lineNodes.getLength() > 0) {
            Element lineElement = (Element) lineNodes.item(0);
            return lineElement.getTextContent().trim();
        }
        
        return "";
    }
 


}
