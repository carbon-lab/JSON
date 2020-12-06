package tech.sobin.json;

public class CharStream {

	private final char[] charSequence;
	private int position;

	public CharStream(String string) {
		this.charSequence = string.toCharArray();
		this.position = 0;
	}

	public char read() {
		if (position >= charSequence.length)
			return '\0';
		return charSequence[position++];
	}

	public char peek() {
		if (position >= charSequence.length)
			return '\0';
		return charSequence[position];
	}

	public int position() {
		return position;
	}

	public void skipSpace() {
		for (; position < charSequence.length; position++)
			if (!Character.isWhitespace(charSequence[position])) break;
	}

	public String readEscape() throws JSON.FormatException {
		char ch = read();
		switch (ch) {
			case 'n':
				return "\n";
			case '\"':
				return "\"";
			case '\'':
				return "\'";
			case 'r':
				return "\r";
			case 't':
				return "\t";
			case 'b':
				return "\b";
			case 'f':
				return "\f";
			case 'u':
				char[] uc = new char[4];
				for (int i = 0; i < 4; i++) {
					uc[i] = read();
					if (uc[i] == '\0')
						throw new JSON.FormatException(position);
				}
				int c = Integer.parseInt(new String(uc), 16);
				return new String(String.valueOf(c));
			default:
			case '\0':
				throw new JSON.FormatException(position);
		}
	}
}
