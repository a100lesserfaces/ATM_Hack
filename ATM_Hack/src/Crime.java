
public class Crime {
	public double lat;
    public double lng;
    public String crimeType;
    public String crimeAddress;
   
    Crime(double lat, double lng, String crimeType, String crimeAddress) {
            this.lat = lat;
            this.lng = lng;
            this.crimeType = crimeType;
            this.crimeAddress = crimeAddress;
    }
    
    public void toString(Crime crime) {
    	System.out.println("Longitude: ");
    	System.out.println(crime.lat);
    
    }
}
