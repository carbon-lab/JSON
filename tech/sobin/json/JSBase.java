package tech.sobin.json;

public abstract class JSBase {
	abstract String stringify();
	static JSBase parse(CharStream charStream) throws JSON.FormatException {
		JSBase R;
		char ch = charStream.peek();
		if (ch == '\"')
			R = new JSString(JSString.parseNext(charStream));
		else if (ch == '{') {
			charStream.read();
			R = JSObject.parse(charStream);
		}
		else if (ch == '[') {
			charStream.read();
			R = JSArray.parse(charStream);
		}
		else if (Character.isDigit(ch))
			R = JSNumber.parseNext(charStream);
		else if (ch == 'n') {
			charStream.read();
			if (charStream.read() != 'u')
				throw new JSON.FormatException(charStream.position());
			if (charStream.read() != 'l')
				throw new JSON.FormatException(charStream.position());
			if (charStream.read() != 'l')
				throw new JSON.FormatException(charStream.position());
			R = null;
		}
		else throw new JSON.FormatException(charStream.position());
		return R;
	}
}
