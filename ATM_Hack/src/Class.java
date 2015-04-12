import java.util.*;
import java.util.ListIterator;


public class Class {
	
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
	public static LinkedList<ATM> findATMs(double lng, double lat, ArrayList<ATM> atms) {
		LinkedList<ATM> ret = new LinkedList<ATM>();

		for(int count = 0; count < atms.size(); count++) {
			if (Math.abs(atms.get(count).getLng() - lng) <= 0.005) {
				if (Math.abs(atms.get(count).getLat() - lat) <= 0.005) {
					ret.add(atms.get(count));
				}
			}
		}
		
		return ret;
	}
	
	//David wrote this.
	public static void crimeCalc(ArrayList<ATM> atms, LinkedList<Crime> crimes) {
		ATM tempATM;
		Crime tempCrime;
		for (int i = 0; i < atms.size(); i++) {
			tempATM = atms.get(i);
			for (int j = 0; j < crimes.size(); j++) {
				tempCrime = crimes.get(j);
				if (Math.abs(tempATM.getLng() - tempCrime.lng) <= 0.0100) {//Was 0.005
					if (Math.abs(tempATM.getLat() - tempCrime.lat) <= 1.0) { // was 0.005
						System.out.println("Added crime.");
						tempATM.addCrime();
					}
				}
			}
		}
	}
	
	public static void printList(LinkedList<Crime> atms) {
		//Doesn't work, who cares.
		ListIterator<Crime> atmsIt = atms.listIterator();
		Crime temp;
		
		while(atmsIt.hasNext()) {
			temp = atmsIt.next();
			temp.toString();
		}
	}
}
