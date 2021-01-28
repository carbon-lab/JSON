package tech.sobin.json;

import java.util.Iterator;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.function.Consumer;

public class JSArray extends JSBase implements JSObjectLike, Iterable<JSBase> {
	private final TreeMap<Integer, JSBase> array;
	private int length;

	public JSArray() {
		this.array = new TreeMap<Integer, JSBase>();
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

	public TreeMap<Integer, JSBase> getArray() {
		return array;
	}

	public int length() {
		return length;
	}

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

		char ch = charStream.peek();
		if (ch == ']') {
			charStream.read();
			return R;
		}
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

	public void push(String value) {
		this.push(new JSString(value));
	}

	public void push(Integer value) {
		this.push(new JSInteger(value));
	}

	public void push(Double value) {
		this.push(new JSDecimal(value));
	}

	public void set(int index, String value) {
		this.set(index, new JSString(value));
	}

	public void set(int index, Integer value) {
		this.set(index, new JSInteger(value));
	}

	public void set(int index, Double value) {
		this.set(index, new JSDecimal(value));
	}

	@Override
	public Iterator<JSBase> iterator() {
		return new JSArrayIterator(this);
	}

	@Override
	public void forEach(Consumer<? super JSBase> action) {
		if (action != null) {
			Iterator<JSBase> it = iterator();
			while (it.hasNext()) {
				action.accept(it.next());
			}
		}
	}

	private class JSArrayIterator implements Iterator<JSBase> {

		private final JSArray array;
		private int nextIndex = 0;

		public JSArrayIterator(JSArray array) {
			this.array = array;
		}

		@Override
		public boolean hasNext() {
			return nextIndex < array.length();
		}

		@Override
		public JSBase next() {
			return array.get(nextIndex++);
		}
	}
}
