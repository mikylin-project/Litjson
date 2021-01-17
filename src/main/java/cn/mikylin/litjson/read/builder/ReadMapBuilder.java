package cn.mikylin.litjson.read.builder;

import cn.mikylin.litjson.buffer.CharBuf;
import cn.mikylin.litjson.handler.TypeHandlers;
import cn.mikylin.litjson.utils.Commons;
import java.util.*;

public final class ReadMapBuilder extends AbstractReadObjectBuilder<Map> {

    public ReadMapBuilder(CharBuf buffer) {
        super(buffer);
        initKey();
        result = Commons.newMap(16);
        buildObject();
    }

    @Override
    void invokeVoid() {
        String key = getKey();
        Object read = TypeHandlers.read(Void.class,null);
        result.put(key,read);
    }

    @Override
    void invokeBaseParam(CharBuf value) {
        String key = getKey();
        String valueStr = value.toString();
        Object read = TypeHandlers.read(String.class,valueStr);
        result.put(key,read);
    }

    @Override
    void parseObject(CharBuf value) {
        String key = getKey();
        Map m = new ReadMapBuilder(value).getResult();
        result.put(key,m);
    }

    @Override
    void parseArray(CharBuf value) {
        String key = getKey();
        List array = new ReadArrayBuilder(value,null).getResult();
        result.put(key,array);
    }

}
