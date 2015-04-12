import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.TreeMap;
import java.io.*;
import java.util.Comparator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Driver {

	public static void main(String[] args) {
		LinkedList<Crime> data = new LinkedList();

		try { //used for debugging
			System.setIn(new FileInputStream("/Users/Tiny/Documents/default/BitCamp/src/crime_incidents_2013_plain.xml"));
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

				newCrime = new Crime(parseItBaby(xCoordinate), parseItBaby(yCoordinate), offense, address);
				data.add(newCrime);
			}
		}
		System.out.println(data.size());
	}

	public static double parseItBaby(int input) {
		double ret = 0.0;
		String temp = String.valueOf(input);
		String whole = temp.substring(0, 2);
		String rest = temp.substring(2, temp.length());
		String fin = whole + "." + rest;
		ret = Double.parseDouble(fin);
		return ret;
	}
	//David wrote this.
	public static void crimeCalc(double xinput, double yinput, LinkedList<ATM> atms, LinkedList<Crime> crimes) {
		ListIterator<ATM> atmsIt = atms.listIterator();
		ATM tempATM;
		Crime tempCrime;

		while(atmsIt.hasNext()) {
			ListIterator<Crime> crimesIt = crimes.listIterator();
			tempATM = atmsIt.next();
			while (crimesIt.hasNext()) {
				tempCrime = crimesIt.next();
				if(Math.abs(tempATM.getLng() - tempCrime.lng) <= 0.0005) {
					if(Math.abs(tempATM.getLat() - tempCrime.lat) <= 0.0005) {
						tempATM.addCrime();
					}
				}
			}
		}
	}
	public static LinkedList<ATM> findATMs(double xinput, double yinput, LinkedList<ATM> atms) {
		LinkedList<ATM> ret = new LinkedList<ATM>();
		double xtempplus = xinput + 0.0005, ytempplus = yinput + 0.0005;
		double xtempminus = xinput - 0.0005, ytempminus = yinput - 0.0005;
		ListIterator<ATM> atmsIt = atms.listIterator();
		ATM temp;

		while(atmsIt.hasNext()) {
			temp = atmsIt.next();
			if (temp.getLng() <= xtempplus && temp.getLat() <= ytempplus) {
				if (temp.getLng() >= xtempminus && temp.getLat() >= ytempminus) {
					ret.add(temp);
				}
			}
		}

		return ret;
	}



	@SuppressWarnings("unchecked")
	public static LinkedList<ATM> sortatmsByCrime(LinkedList<ATM> atms){
		Collections.sort(atms);
		return atms;

	}

}
