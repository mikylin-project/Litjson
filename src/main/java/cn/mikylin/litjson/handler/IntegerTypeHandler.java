package cn.mikylin.litjson.handler;

import cn.mikylin.litjson.buffer.BufAppendable;

public class IntegerTypeHandler implements TypeHandler<Integer> {

    @Override
    public Class[] getTypes() {
        return new Class[] {Integer.class,int.class};
    }

    @Override
    public Integer read(String value) {
        return Integer.valueOf(value);
    }

    @Override
    public void append(Object value, BufAppendable append) {
        append.append(Integer.toString((Integer)value));
    }
}
