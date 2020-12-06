package tech.sobin.json;

public class JSON {

	public static class FormatException extends Exception {
		private static final long serialVersionUID = 3086696727554196371L;
		public final int position;

		public FormatException(int position) {
			super("Unexpected character in JSON at position " + position);
			this.position = position;
		}
	}

	public static String stringify(JSObject object) {
		return object.stringify();
	}

	public static JSObject parse(String string) throws FormatException {
		JSObject R = null;
		CharStream cs = new CharStream(string);
		char ch;
		while ((ch = cs.read()) != '\0') {
			if (Character.isWhitespace(ch)) continue;
			if (ch == '{') {
				R = JSObject.parse(cs);
				break;
			}
			else if (ch == '[') {
				R = JSArray.parse(cs);
				break;
			}
			else throw new FormatException(cs.position());
		}
		while ((ch = cs.read()) != '\0') {
			if (!Character.isWhitespace(ch))
				throw new FormatException(cs.position());
		}
		return R;
	}

}
