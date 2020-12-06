package tech.sobin.json;

public class JSInteger extends JSNumber {

	private int value;

	public JSInteger() {
		this.value = 0;
	}

	public JSInteger(int i) {
		this.value = i;
	}

	public String stringify() {
		return Integer.toString(value);
	}

	public int getValue() {
		return value;
	}
}
