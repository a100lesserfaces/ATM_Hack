import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class Parser {
	
	public static LinkedList<Crime> parse() {
		
		LinkedList<Crime> data = new LinkedList();

		try { //used for debugging
			System.setIn(new FileInputStream("/Users/Tiny/Documents/default/ATM_Hack/src/03_16_2015_to_03_27_2015.xml"));
		} catch (FileNotFoundException e2) {
		// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Document doc = null;
		
		try {
			doc = dBuilder.parse(System.in);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Element docElement = doc.getDocumentElement();
		NodeList n1 = docElement.getChildNodes();
		for(int i = 0; i < n1.getLength(); ++i) {
			
			Node nextCrime = n1.item(i);
			
			if (nextCrime.getNodeType() == Node.ELEMENT_NODE) {
				NodeList n2 = nextCrime.getChildNodes();
				
				Crime newCrime = null;
				String offense = null;
				String address = null;
				int xCoordinate = 0;
				int yCoordinate = 0;
				
				for(int j = 0; j < n2.getLength(); ++j) {
					
					Node nextAtt = n2.item(j);
					
					if (nextAtt.getNodeType() == Node.ELEMENT_NODE) {		
						
						if(nextAtt.getNodeName().compareTo("dcst:offense") == 0) {
							Node temp = nextAtt.getFirstChild();
							offense = temp.getNodeValue();
						} else if(nextAtt.getNodeName().compareTo("dcst:blockxcoord") == 0) {
							Node temp = nextAtt.getFirstChild();
							String xCoord = temp.getNodeValue();
							xCoord = xCoord.substring(0, 6);
							xCoordinate = Integer.parseInt(xCoord);
						} else if(nextAtt.getNodeName().compareTo("dcst:blockycoord") == 0) {
							Node temp = nextAtt.getFirstChild();
							String yCoord = temp.getNodeValue();
							yCoord = yCoord.substring(0, 6);
							yCoordinate = Integer.parseInt(yCoord);
						} else if(nextAtt.getNodeName().compareTo("dcst:blocksiteaddress") == 0) {
							Node temp = nextAtt.getFirstChild();
							address = temp.getNodeValue();
						}
					}
				}
				
				
				
				newCrime = new Crime(parseItBabyX(xCoordinate), parseItBaby(yCoordinate), offense, address);
				data.add(newCrime);
			}
		}
		return data;
	}
	
	public static double parseItBaby(int input) {
		double ret = 0.0;
		String temp = String.valueOf(input);
		String whole = temp.substring(0, 2);
		String rest = temp.substring(2, temp.length());
		String fin = whole + "." + rest;
		ret = Double.parseDouble(fin) - 90; // subtract 90 from y 
		return ret;
	}
	
	public static double parseItBabyX(int input) {
		double ret = 0.0;
		String temp = String.valueOf(input);
		String whole = temp.substring(0, 2);
		String rest = temp.substring(2, temp.length());
		String fin = whole + "." + rest;
		ret = Double.parseDouble(fin);
		return ret;
	}
}
