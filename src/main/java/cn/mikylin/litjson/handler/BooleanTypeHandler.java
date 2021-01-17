package cn.mikylin.litjson.handler;

import cn.mikylin.litjson.buffer.BufAppendable;

public class BooleanTypeHandler implements TypeHandler<Boolean> {

    @Override
    public Class[] getTypes() {
        return new Class[] {Boolean.class,boolean.class};
    }

    @Override
    public Boolean read(String value) {
        return Boolean.valueOf(value);
    }

    @Override
    public void append(Object value, BufAppendable append) {
        append.append(String.valueOf(value));
    }
}
