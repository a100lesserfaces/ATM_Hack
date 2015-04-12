import java.util.Comparator;

public class ATM implements Comparable{

	private double lng;
	private double lat;
	private String address;
	private int numCrimes = 0;
	private String ranking; // top 25%, top 50%, bottom 50%, bottom 25%

	public ATM(String address, String lng, String lat) {
		this.address = address;
		this.lng = Double.parseDouble(lng);
		this.lat = Double.parseDouble(lat);
	}

	public double getLng() {
		return lng;
	}

	public double getLat() {
		return lat;
	}

	public String getAddress() {
		return address;
	}

	public void addCrime() {
		numCrimes++;
	}

	public int getCrimeNum() {
		return numCrimes;
	}

	@Override
	public int compareTo(Object arg0) {
		return (this.numCrimes - ((ATM)arg0).numCrimes);

	}
	
	public void setRanking(String ranking) {
		this.ranking = ranking;
	}
	
	public String getRanking() {
		return ranking;
	}

}

