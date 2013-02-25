package util;

public class Attributes {
	private final static double CHANCE_OF_MUTATION = 0.01;

	private final long[] stats = new long[Attribute.values().length];

	public Attributes() {
		for (Attribute a : Attribute.values()) {
			stats[a.getValue()] = Math.round((Math.random() * 10000));
		}
	}

	public int getLength() {
		return stats.length;
	}

	public long getAttribute(Attribute a) {
		return stats[a.getValue()];
	}

	public static Attributes breed(Attributes a1, Attributes a2) {
		Attributes result = new Attributes();

		for (Attribute a : Attribute.values()) {
			if (Math.random() > CHANCE_OF_MUTATION) {
				result.stats[a.getValue()] = Math
						.round((a1.stats[a.getValue()] + a2.stats[a.getValue()]) / 2);
			}
		}

		return result;
	}
}
