package cn.mikylin.litjson.handler;

import cn.mikylin.litjson.buffer.BufAppendable;

public class CharTypeHandler implements TypeHandler<Character> {

    @Override
    public Class[] getTypes() {
        return new Class[] {Character.class,char.class};
    }

    @Override
    public Character read(String value) {
        return value.toCharArray()[0];
    }

    @Override
    public void append(Object value, BufAppendable append) {
        append.append(Character.toString((Character)value));
    }
}
