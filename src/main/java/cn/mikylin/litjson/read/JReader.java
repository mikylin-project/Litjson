package cn.mikylin.litjson.read;

import cn.mikylin.litjson.buffer.CharBuf;
import cn.mikylin.litjson.handler.TypeHandler;
import cn.mikylin.litjson.handler.TypeHandlers;
import cn.mikylin.litjson.read.builder.*;
import java.util.*;

public class JReader<T> {

    private JReader(ReadBuilder<T> builder) {
        if (builder == null)
            throw new NullPointerException("builder can not be null.");
        this.builder = builder;
    }

    private final ReadBuilder<T> builder;

    public T read() {
        return Objects.nonNull(builder) ?
                builder.getResult() : null;
    }





    public static JReader<List> list(String json,Class clz,List<TypeHandler> handlers) {
        CharBuf buffer = CharBuf.createDefault(json);
        TypeHandlers.initHandler(handlers);
        try {
            return new JReader<>(new ReadArrayBuilder(buffer,clz));
        } finally {
            TypeHandlers.close();
        }
    }

    public static JReader<Object> object(String json,Class clz,List<TypeHandler> handlers) {
        CharBuf buffer = CharBuf.createDefault(json);
        TypeHandlers.initHandler(handlers);
        try {
            return new JReader<>(new ReadObjectBuilder(buffer,clz));
        } finally {
            TypeHandlers.close();
        }
    }

    public static JReader<Map> object(String json,List<TypeHandler> handlers) {
        CharBuf buffer = CharBuf.createDefault(json);
        TypeHandlers.initHandler(handlers);
        try {
            return new JReader<>(new ReadMapBuilder(buffer));
        } finally {
            TypeHandlers.close();
        }
    }

    public static JReader<List> list(String json,Class clz) {
        return list(json,clz,Collections.EMPTY_LIST);
    }

    public static JReader<Map> object(String json) {
        return object(json,Collections.EMPTY_LIST);
    }

    public static JReader<Object> object(String json,Class clz) {
        return object(json,clz,Collections.EMPTY_LIST);
    }
}