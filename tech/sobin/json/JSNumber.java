package tech.sobin.json;

public abstract class JSNumber extends JSBase {
	public abstract String stringify();

	public static JSNumber parseNext(CharStream charStream)
			throws JSON.FormatException {
		char ch;
		int integer = 0;
		double decimal = 0.0;
		int mode = 0;
		ch = charStream.read();
		if (ch == '\0') throw new JSON.FormatException(charStream.position());

		if (ch == '0') {
			mode = 1;
			ch = charStream.peek();
			if (ch != '.') {
				if (ch == ',') {
					return new JSInteger(0);
				}
				else
					throw new JSON.FormatException(charStream.position());
			}
			charStream.read();
			double t = 0.1;
			ch = charStream.peek();
			while (Character.isDigit(ch)) {
				decimal += t * ((int)ch - 48);
				t /= 10.0;
				charStream.read();
				ch = charStream.peek();
			}
		}
		else if (Character.isDigit(ch)) {
			integer = (int)ch - 48;
			ch = charStream.peek();
			while (Character.isDigit(ch)) {
				integer = integer * 10 + ((int)ch - 48);
				charStream.read();
				ch = charStream.peek();
			}
			if (ch == '.') {
				mode = 1;
				charStream.read();
				ch = charStream.peek();
				double t = 0.1;
				decimal = integer;
				while (Character.isDigit(ch)) {
					decimal += t * ((int)ch - 48);
					t /= 10.0;
					charStream.read();
					ch = charStream.peek();
				}
			}
		}
		if (ch == 'e' || ch == 'E') {
			if (mode == 0) {
				decimal = integer;
				mode = 1;
			}
			charStream.read();
			ch = charStream.read();
			int _exp = 1;
			if (ch == '-') _exp = -1;
			else _exp *= ((int)ch - 48);
			ch = charStream.peek();
			while (Character.isDigit(ch)) {
				_exp = _exp * 10 + (_exp < 0 ? -1 : 1) * ((int)ch - 48);
				charStream.read();
				ch = charStream.peek();
			}
			decimal = decimal * Math.pow(10, _exp);
		}

		return mode == 0 ? new JSInteger(integer) :
				new JSDecimal(decimal);
	}

	@Override
	public String toString() {
		return this.stringify();
	}
}
