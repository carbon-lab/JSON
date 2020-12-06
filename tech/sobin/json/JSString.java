package tech.sobin.json;

public class JSString extends JSBase {

	private String content;

	public JSString() {
		this.content = "";
	}

	public JSString(String s) {
		this.content = s;
	}

	public String stringify() {
		return "\"" + content + "\"";
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
