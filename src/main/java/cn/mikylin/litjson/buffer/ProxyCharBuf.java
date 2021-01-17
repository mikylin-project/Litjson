package cn.mikylin.litjson.buffer;

import cn.mikylin.litjson.exception.JSONCharException;

public class ProxyCharBuf implements CharBuf {

    private CharBuf buffer;

    protected ProxyCharBuf(CharBuf buffer) {
        if (buffer == null || CharBuf.isNon(buffer))
            throw new JSONCharException("json can not be blank.");
        this.buffer = buffer;
    }

    @Override
    public char read() {
        return buffer.read();
    }

    @Override
    public boolean canRead() {
        return buffer.canRead();
    }

    @Override
    public CharBuf readTil(boolean isInCludeTil,char... chars) {
        return buffer.readTil(isInCludeTil,chars);
    }

    @Override
    public char last() {
        return buffer.last();
    }

    @Override
    public char head() {
        return buffer.head();
    }

    @Override
    public char[] data() {
        return buffer.data();
    }

    @Override
    public int length() {
        return buffer.length();
    }

    @Override
    public String toString() {
        return buffer.toString();
    }

    @Override
    public boolean offset(int size) {
        return buffer.offset(size);
    }

    @Override
    public int index() {
        return buffer.index();
    }
}
