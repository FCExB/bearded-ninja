package util;

public enum Attribute {
	FOOD(0), SEX(1);

	private int index;

	private Attribute(int i) {
		index = i;
	}

	public int getValue() {
		return index;
	}
}
