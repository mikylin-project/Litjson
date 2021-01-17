package cn.mikylin.litjson.handler;

import cn.mikylin.litjson.buffer.BufAppendable;

public class DoubleTypeHandler implements TypeHandler<Double> {

    @Override
    public Class[] getTypes() {
        return new Class[] {Double.class,double.class};
    }

    @Override
    public Double read(String value) {
        return Double.valueOf(value);
    }

    @Override
    public void append(Object value, BufAppendable append) {
        append.append(Double.toString((Double)value));
    }
}
