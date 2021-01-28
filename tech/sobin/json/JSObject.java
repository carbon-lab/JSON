package tech.sobin.json;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;

public class JSObject extends JSBase implements JSObjectLike, Iterable<Entry<String, JSBase>> {

	private LinkedHashMap<String, JSBase> map = new LinkedHashMap<String, JSBase>();
	
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

	public JSBase put(String key, Integer value) {
		return put(key, new JSInteger(value));
	}
	
	public JSBase put(String key, Double value) {
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

	@Override
	public Iterator<Entry<String, JSBase>> iterator() {
		return map.entrySet().iterator();
	}

	@Override
	public void forEach(Consumer<? super Entry<String, JSBase>> action) {
		map.entrySet().forEach(action);
	}

	@Override
	public Spliterator<Entry<String, JSBase>> spliterator() {
		return map.entrySet().spliterator();
	}
}
