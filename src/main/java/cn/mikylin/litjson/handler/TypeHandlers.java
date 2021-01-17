package cn.mikylin.litjson.handler;

import cn.mikylin.litjson.buffer.BufAppendable;
import cn.mikylin.litjson.exception.JSONObjectInvokeException;
import java.text.*;
import java.util.*;

public final class TypeHandlers {

    private static TypeHandler[] handlerArray;

    static {
        List<TypeHandler> defaultHandlers = new ArrayList<>();

        defaultHandlers.add(new StringTypeHandler());
        defaultHandlers.add(new DoubleTypeHandler());
        defaultHandlers.add(new FloatTypeHandler());
        defaultHandlers.add(new IntegerTypeHandler());
        defaultHandlers.add(new LongTypeHandler());
        defaultHandlers.add(new ByteTypeHandler());
        defaultHandlers.add(new ShortTypeHandler());
        defaultHandlers.add(new CharTypeHandler());
        defaultHandlers.add(new VoidTypeHandler());
        defaultHandlers.add(new BooleanTypeHandler());

        DateTypeHandler dateTypeHandler = new DateTypeHandler();
        dateTypeHandler.setWriteDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        List<DateFormat> dateFormats = new ArrayList<>();
        dateFormats.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        dateFormats.add(new SimpleDateFormat("yyyy-MM-dd"));
        dateFormats.add(new SimpleDateFormat("HH:mm:ss"));
        dateTypeHandler.setReadParseFormats(dateFormats);
        defaultHandlers.add(dateTypeHandler);

        handlerArray = new TypeHandler[defaultHandlers.size()];
        defaultHandlers.toArray(handlerArray);
    }

    private static ThreadLocal<Map<Class,TypeHandler>> tlMap = new ThreadLocal<>();

    public static Object read(Class clz,String value) {

        clz = value == null || value.equals("null") ? Void.class : clz;

        Map<Class, TypeHandler> handlerMap = tlMap.get();
        TypeHandler typeHandler = handlerMap.get(clz);
        if (typeHandler == null)
            throw new JSONObjectInvokeException("can not parse type [" + clz.getName() + "].");
        return typeHandler.read(value);
    }

    public static boolean append(Object object, BufAppendable append) {

        Class clz = object == null ? Void.class : object.getClass();

        Map<Class,TypeHandler> handlerMap = tlMap.get();
        TypeHandler typeHandler = handlerMap.get(clz);
        if (typeHandler == null)
            return false;
        typeHandler.append(object,append);
        return true;
    }

    public static void setHandler(TypeHandler... handlers) {

        Map<Class, TypeHandler> handlerMap = tlMap.get();
        if (handlerMap == null) {
            tlMap.set(new HashMap<>());
            handlerMap = tlMap.get();
        }

        for (TypeHandler h : handlers) {
            Class[] types = h.getTypes();
            for (Class t : types)
                handlerMap.put(t,h);
        }
    }

    public static void defaultHandler() {
        setHandler(handlerArray);
    }

    public static void close() {
        tlMap.remove();
    }

    public static void initHandler(List<TypeHandler> handlers) {

        if (handlers == null || handlers == Collections.EMPTY_LIST || handlers.size() == 0) {
            TypeHandlers.defaultHandler();
            return;
        }

        TypeHandler[] h = new TypeHandler[handlers.size()];
        handlers.toArray(h);
        TypeHandlers.setHandler(h);
    }
}
