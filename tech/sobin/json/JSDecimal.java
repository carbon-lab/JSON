package tech.sobin.json;

public class JSDecimal extends JSNumber {

	private double value;

	public JSDecimal() {
		this.value = 0.0;
	}

	public JSDecimal(double value) {
		this.value = value;
	}

	public String stringify() {
		return Double.toString(value);
	}

	public double getValue() {
		return value;
	}
}
