package cn.mikylin.litjson.handler;

import cn.mikylin.litjson.buffer.BufAppendable;

public class ByteTypeHandler implements TypeHandler<Byte> {

    @Override
    public Class[] getTypes() {
        return new Class[] {Byte.class,byte.class};
    }

    @Override
    public Byte read(String value) {
        return Byte.valueOf(value);
    }

    @Override
    public void append(Object value, BufAppendable append) {
        append.append(Byte.toString((Byte)value));
    }
}
