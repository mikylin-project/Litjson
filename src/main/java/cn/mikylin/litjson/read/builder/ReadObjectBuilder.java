package cn.mikylin.litjson.read.builder;

import cn.mikylin.litjson.buffer.CharBuf;
import cn.mikylin.litjson.handler.TypeHandlers;
import cn.mikylin.litjson.utils.*;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public final class ReadObjectBuilder extends AbstractReadObjectBuilder<Object> {

    // param setters
    // if object is a map, this could be null
    private Map<String, Method> setters;

    //object class
    private Class clazz;

    public ReadObjectBuilder(CharBuf buffer,Class clz) {
        super(buffer);
        clazz = clz;
        result = InvokeUtils.create(clazz);
        setters = InvokeUtils.sets(clazz);
        buildObject();
    }

    @Override
    void invokeVoid() {
        String key = getKey();
        Method setter = setters.get(key);
        Class clz = InvokeUtils.methodParam(setter);
        Object val = TypeHandlers.read(clz,null);
        InvokeUtils.invokeSet(setter,result,val);
    }

    @Override
    void invokeBaseParam(CharBuf value) {
        String key = getKey();
        String valueStr = value.toString();

        Method setter = setters.get(key);
        Class clz = InvokeUtils.methodParam(setter);
        Object val = TypeHandlers.read(clz,valueStr);
        InvokeUtils.invokeSet(setter,result,val);
    }

    @Override
    void parseObject(CharBuf value) {
        String key = getKey();
        Method setter = setters.get(key);
        Class clz = InvokeUtils.methodParam(setter);

        ReadBuilder builder = Commons.isMap(clz) ?
                new ReadMapBuilder(value) : new ReadObjectBuilder(value,clz);

        Object obj = builder.getResult();
        InvokeUtils.invokeSet(setter,result,obj);
    }

    @Override
    void parseArray(CharBuf value) {
        String key = getKey();
        Class clas = InvokeUtils.genericityClass(clazz,key);
        List array = new ReadArrayBuilder(value,clas).getResult();
        InvokeUtils.invokeSet(setters.get(key),result,array);
    }
}
