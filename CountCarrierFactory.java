package imageflubber;

public class CountCarrierFactory {
	static CountCarrier k = null;
	public static CountCarrier getCarrier() {
		if (k == null) {
			k = new CountCarrier();
			return k;
		}
		return k;
	}
}
