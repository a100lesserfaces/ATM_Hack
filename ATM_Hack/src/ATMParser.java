import java.util.*;

public class ATMParser {

	/* Takes string input from the Capitol One API and adds all of the
	 * parsed ATM objects into the global ArrayList list object.
	 */
	public static ArrayList<ATM> parseATMs(String input) {
		int lngIndex, latIndex, addyIndex, nameIndex;
		ArrayList<ATM> list = new ArrayList<ATM>();
		String[] tokens = input.split("_id");
		for (int i = 1; i < tokens.length - 1; i++) {
			// get lng
			lngIndex = tokens[i].indexOf("lng");
			String lng = getLng(tokens[i].substring(lngIndex));
			// get lat
			latIndex = tokens[i].indexOf("\"lat");
			String lat = getLat(tokens[i].substring(latIndex));
			// get name
			nameIndex = tokens[i].indexOf("\"name");
			String name = getName(tokens[i].substring(nameIndex));
			// get address
			addyIndex = tokens[i].lastIndexOf("street_number");
			name += " : " + getAddress(tokens[i].substring(addyIndex));
			ATM temp = new ATM(name, lng, lat);
			list.add(temp);
		}
		return list;
	}
	
	public static String getName(String input) {
		int end = input.indexOf("\",");
		return input.substring(8, end);
	}

	/*
	 * Parses the longitude from the given ATM
	 */
	public static String getLng(String input) {
		//input is a substring starting with "lng"
		// starts at index 5
		int end = input.indexOf("}");
		return input.substring(5, end);
	}

	/*
	 * Parses the latitude from the given ATM
	 */
	public static String getLat(String input) {
		//input is a substring starting with "lat"
		int end = input.indexOf(",\"");
		return input.substring(6, end);
	}

	/*
	 * Parses the name from the given ATM
	 */
	public static String getAddress(String input) {
		//input is a substring starting with "name"
		// starts at index 7
		String ret ="";
		int end = input.indexOf("\",");
		ret += input.substring(16, end);
		int start = input.indexOf("street_name");
		end = input.indexOf("\"}");
		ret += " " + input.substring(start + 14, end);
		return ret;
	}
	
	public static double getMinLat(ArrayList<ATM> list) {
		double min = list.get(0).getLat();
		for (int i = 1; i < list.size(); i++) {
			if (list.get(i).getLat() < min) {
				min = list.get(i).getLat();
			}
		}
		return min - 0.0005;
	}
	
	public static double getMinLng(ArrayList<ATM> list) {
		double min = list.get(0).getLng();
		for (int i = 1; i < list.size(); i++) {
			if (list.get(i).getLng() < min) {
				min = list.get(i).getLng();
			}
		}
		return min - 0.0005;
	}
	
	public static double getMaxLat(ArrayList<ATM> list) {
		double max = list.get(0).getLat();
		for (int i = 1; i < list.size(); i++) {
			if (list.get(i).getLat() > max) {
				max = list.get(i).getLat();
			}
		}
		return max + 0.0005;
	}
	
	public static double getMaxLng(ArrayList<ATM> list) {
		double max = list.get(0).getLng();
		for (int i = 1; i < list.size(); i++) {
			if (list.get(i).getLng() > max) {
				max = list.get(i).getLng();
			}
		}
		return max + 0.0005;
	}
}
