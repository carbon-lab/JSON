package tech.sobin.json;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class JSObject extends JSBase {

	private HashMap<String, JSBase> map = new HashMap<String, JSBase>();
	
	public String stringify() {
		StringBuilder R = new StringBuilder();
		boolean isFirst = true;

		R.append('{');
		for (Entry<String, JSBase> o : map.entrySet()) {
			if (isFirst) isFirst = false;
			else R.append(',');
			R.append('\"');
			R.append(o.getKey());
			R.append('\"');
			R.append(':');
			JSBase value = o.getValue();
			if (value == null) R.append("null");
			else R.append(o.getValue().stringify());
		}
		R.append('}');

		return R.toString();
	}
	
	public JSBase put(String key, JSBase value) {
		return map.put(key, value);
	}

	public JSBase put(String key, String value) {
		return put(key, new JSString(value));
	}

	public JSBase put(String key, int value) {
		return put(key, new JSInteger(value));
	}
	
	public JSBase put(String key, double value) {
		return put(key, new JSDecimal(value));
	}
	
	public JSBase get(String key) {
		return map.get(key);
	}
	
	public Set<Entry<String, JSBase>> entrySet() {
		return this.map.entrySet();
	}

	public static JSObject parse(CharStream charStream) throws JSON.FormatException {
		JSObject R = new JSObject();

		char ch;
		while (true) {
			charStream.skipSpace();
			if (charStream.peek() == '}') {
				charStream.read();
				break;
			}
			String key = JSString.parseNext(charStream);
			charStream.skipSpace();
			if (charStream.read() != ':')
				throw new JSON.FormatException(charStream.position());
			charStream.skipSpace();
			JSBase value = JSBase.parse(charStream);
			R.put(key, value);
			charStream.skipSpace();
			ch = charStream.read();
			if (ch == ',') continue;
			else if (ch == '}') break;
			else throw new JSON.FormatException(charStream.position());
		}

		return R;
	}

}
