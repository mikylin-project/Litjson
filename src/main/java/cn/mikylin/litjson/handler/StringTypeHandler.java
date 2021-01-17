package cn.mikylin.litjson.handler;

import cn.mikylin.litjson.buffer.BufAppendable;

public class StringTypeHandler implements TypeHandler<String> {

    @Override
    public Class[] getTypes() {
        return new Class[] {String.class};
    }

    @Override
    public String read(String value) {
        return value;
    }

    @Override
    public void append(Object t, BufAppendable append) {
        writeString(t.toString(),append);
    }
}
