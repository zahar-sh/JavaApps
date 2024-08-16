package sample;

public class CharsWrapper implements CharSequence {
    private final char[] chars;
    private final int off, len;

    public CharsWrapper(char[] chars) {
        this.chars = chars;
        this.off = 0;
        this.len = chars.length;
    }
    public CharsWrapper(int start, int end, char[] chars) {
        this(start, end, chars, 0, chars.length);
    }
    public CharsWrapper(int start, int end, char[] chars, int first, int last) {
        if (chars == null)
            throw new NullPointerException();
        if (first < 0 || first > last || last >= chars.length)
            throw new IndexOutOfBoundsException();
        if (start < first || start > end || end >= last)
            throw new IndexOutOfBoundsException();
        this.chars = chars;
        off = start + first;
        len = end - off;
    }

    public int length() {
        return len;
    }
    public char charAt(int index) {
        if (index < 0 || index >= len)
            throw new IndexOutOfBoundsException();
        return chars[off + index];
    }
    public CharSequence subSequence(int start, int end) {
        return new CharsWrapper(start, end, chars, off, off + len);
    }

    public int hashCode() {
        int hash = 0;
        for (int i = off, end = off + len; i < end; i++)
            hash = hash * 31 + chars[i];
        return hash ^ (hash >>> 16);
    }
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o != null && getClass() == o.getClass()) {
            CharSequence seq = ((CharSequence) o);
            if (len == seq.length()) {
                for (int i = 0; i < len; i++)
                    if (chars[off + i] != seq.charAt(i))
                        return false;
                return true;
            }
        }
        return false;
    }
    public String toString() {
        return new String(chars, off, len);
    }
}