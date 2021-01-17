package cn.mikylin.litjson.write;

import cn.mikylin.litjson.handler.TypeHandler;
import cn.mikylin.litjson.handler.TypeHandlers;
import java.util.List;

/**
 * Json String writer
 * @author mikylin
 */
public class JWriter {

    private WriteBuilder builder;

    public static JWriter create(Object object, List<TypeHandler> handlers) {
        TypeHandlers.initHandler(handlers);
        JWriter jWriter = new JWriter(object);
        TypeHandlers.close();
        return jWriter;
    }

    public static JWriter create(Object object) {
        return create(object,null);
    }

    private JWriter(Object object) {
        builder = new WriteBuilder(object);
    }

    @Override
    public String toString() {
        return builder.toString();
    }

}
