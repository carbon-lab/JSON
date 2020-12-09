package tech.sobin.json;

import java.util.HashMap;
import java.util.Map.Entry;

public class JSArray extends JSObject {
	private final HashMap<Integer, JSBase> array;
	private int length;

	public JSArray() {
		this.array = new HashMap<Integer, JSBase>();
		this.length = 0;
	}

	public void push(JSBase value) {
		array.put(length, value);
		length += 1;
	}

	public void set(int index, JSBase value) {
		if (index < 0) return;
		array.put(index, value);
		if (index >= length) length = index + 1;
	}

	public JSBase get(int index) {
		return array.get(index);
	}

	public HashMap<Integer, JSBase> getArray() {
		return array;
	}

	public int length() {
		return length;
	}

	@Override
	public String stringify() {
		StringBuilder R = new StringBuilder();
		boolean isFirst = true;

		R.append('[');
		int i = 0;
		for (Entry<Integer, JSBase> o : array.entrySet()) {
			int index = o.getKey();
			while (i < index) {
				if (isFirst) isFirst = false;
				else R.append(',');
				R.append("null");
				i++;
			}
			if (isFirst) isFirst = false;
			else R.append(',');
			JSBase value = o.getValue();
			if (value == null) R.append("null");
			else R.append(o.getValue().stringify());
			i++;
		}
		R.append(']');

		return R.toString();
	}

	public static JSArray parse(CharStream charStream) throws JSON.FormatException {
		JSArray R = new JSArray();

		char ch;
		while (true) {
			charStream.skipSpace();
			JSBase value = JSBase.parse(charStream);
			R.push(value);
			charStream.skipSpace();
			ch = charStream.read();
			if (ch == ',') continue;
			else if (ch == ']') break;
			else throw new JSON.FormatException(charStream.position());
		}

		return R;
	}

}
