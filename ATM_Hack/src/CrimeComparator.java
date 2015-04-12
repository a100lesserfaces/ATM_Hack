import java.util.Comparator;

public class CrimeComparator implements Comparator<Crime> {

	public int compare(Crime cr1, Crime cr2) {
		// TODO Auto-generated method stub

		if(cr1.lat < cr2.lat) {
			return -1;
		} else if(cr1.lat > cr2.lat) {
			return 1;
		} else {
			if(cr1.lng < cr2.lng) {
				return -1;
			} else if(cr1.lng < cr2.lng) {
				return 1;
			} else {
				return 0;
			}
		}
	}
}