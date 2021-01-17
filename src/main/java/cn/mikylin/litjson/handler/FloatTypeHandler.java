package cn.mikylin.litjson.handler;

import cn.mikylin.litjson.buffer.BufAppendable;

public class FloatTypeHandler implements TypeHandler<Float> {

    @Override
    public Class[] getTypes() {
        return new Class[] {Float.class,float.class};
    }

    @Override
    public Float read(String value) {
        return Float.valueOf(value);
    }

    @Override
    public void append(Object value, BufAppendable append) {
        append.append(Float.toString((Float)value));
    }
}
