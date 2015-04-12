import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import javax.swing.JFrame;


public class RealDriver {
	
	private static ArrayList<ATM> atms = new ArrayList<ATM>();
	private static LinkedList<Crime> crimes = new LinkedList<Crime>();
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Scanner userIn = new Scanner(System.in);
		double lng, lat;
		LinkedList<ATM> validATMs;
		
		
		crimes = Parser.parse();
		//System.out.println(crimes.size());
		callAPI();
		//trans();
		
		System.out.println(crimes.get(1).lat + " " + crimes.get(1).lng);
		trimCrimes();
		System.out.println(atms.size());
		System.out.println(crimes.size());
		Class.crimeCalc(atms, crimes);
		setAllRankings();
		//System.out.println(atms.get(301).getRanking());
		System.out.println(crimes.get(1).lat + " " + crimes.get(1).lng);
		
		GUI gui = new GUI(atms);
        gui.setSize(600,700);
        gui.setVisible(true);
        gui.setDefaultCloseOperation(GUI.EXIT_ON_CLOSE);
		
		/*System.out.println("Done!");
		System.out.println("\nEnter longitude, followed by latitude: ");
		lng = userIn.nextDouble();
		lat = userIn.nextDouble();*/
		
		userIn.close();
	}
	
	@SuppressWarnings("unchecked")
	public static String processData(double lng, double lat) {
		LinkedList<ATM> validATMs;
		String ret = "!";
		
		validATMs = Class.findATMs(lng, lat, atms);
		Collections.sort(validATMs);
		int upTo = 10;
		if (validATMs.size() < upTo)
			upTo = validATMs.size();
		for(int count = 0; count < upTo; count++) {
			int temp = count + 1;
			ret += temp + ". [Rank "+ validATMs.get(count).getRanking() + "] " + validATMs.get(count).getAddress() +  "!";
		}
		return ret;
	}
	
	
	
	public static void trimCrimes() {
		double minLat, minLng, maxLat, maxLng;
		minLat = ATMParser.getMinLat(atms);
		minLng = ATMParser.getMinLng(atms);
		maxLat = ATMParser.getMaxLat(atms);
		maxLng = ATMParser.getMaxLng(atms);
		for (int i = 0; i < crimes.size(); i++) {
			Crime temp = crimes.get(i);
			if (temp.lat > maxLat || temp.lat < minLat || temp.lng > maxLng || temp.lng < minLng) {
				crimes.remove(i);
			}
		}
	}
	
	public static void callAPI() {
		URL yahoo;
		try {
			yahoo = new URL("http://api.reimaginebanking.com:80/atms?lng=-77.0164&lat=38.9047&rad=15&key=ENT2b6cd49423d45d9986d4d3fd967eb423");
			URLConnection yc = yahoo.openConnection();
		     BufferedReader in = new BufferedReader( new InputStreamReader( yc.getInputStream()));
		     String inputLine;

		     while ((inputLine = in.readLine()) != null) 
		         atms = ATMParser.parseATMs(inputLine);
		    	 //System.out.println(inputLine);
		     in.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
	}
	
	public static void trans(){
		double xx, yy, r, ct, st, xorg = 38.89, yorg = 77.01;
		
		Crime ctemp;
		for(int i = 0; i < crimes.size(); i++){
			ctemp = crimes.get(i);
			xx = ctemp.lng - 0;
			yy = ctemp.lat - 0;
		r = Math.sqrt(((xx*xx) + (yy*yy)));
		ct = xx/r;
		st = yy/r;
		xx = r * ( (ct * Math.cos(0)) + (st * Math.sin(0)) );
		yy =  r * ( (st * Math.cos(0))- (ct * Math.sin(0)) );
		ctemp.lat = yorg + yy;
		ctemp.lng = xorg + xx;
		}
	}
	
	public static void setAllRankings() {
		Collections.sort(atms);
		int mid = atms.size() / 2;
		int top = mid / 2;
		int bot = mid + top;
		for (int i = 0; i < atms.size(); i++) {
			if (i <= top) {
				atms.get(i).setRanking("A");
			} else if (i <= mid) {
				atms.get(i).setRanking("B");
			} else if (i <= bot) {
				atms.get(i).setRanking("C");
			} else {
				atms.get(i).setRanking("D");
			}
		}
	}
}
