package tech.sobin.json;

public class JSString extends JSBase {

	private String content;

	public JSString() {
		this.content = "";
	}

	public JSString(String s) {
		if (s == null) content = "";
		else this.content = s;
	}

	public String stringify() {
		StringBuilder R = new StringBuilder();
		R.append('"');
		for (char c: content.toCharArray()) {
			switch (c) {
				case '\n': R.append("\\n"); break;
				case '\r': R.append("\\r"); break;
				case '\t': R.append("\\t"); break;
				case '\b': R.append("\\b"); break;
				case '\f': R.append("\\f"); break;
				case '\"': R.append("\\\""); break;
				case '\0': R.append("\\u0000"); break;
				default: R.append(c);
			}
		}
		R.append('"');
		return R.toString();
	}

	public static String parseNext(CharStream charStream) throws JSON.FormatException {
		StringBuilder R = new StringBuilder();
		char ch;
		while (true) {
			ch = charStream.read();
			if (ch == '\0') throw new JSON.FormatException(charStream.position());
			if (Character.isSpaceChar(ch)) continue;
			if (ch == '\"') break;
			throw new JSON.FormatException(charStream.position());
		}
		while (true) {
			ch = charStream.read();
			if (ch == '\0') throw new JSON.FormatException(charStream.position());
			if (ch == '\"') break;
			if (ch == '\\') {
				R.append(charStream.readEscape());
			} else {
				R.append(ch);
			}
		}
		return R.toString();
	}

	@Override
	public String toString() {
		return content;
	}
}
