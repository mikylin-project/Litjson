package cn.mikylin.litjson.handler;

import cn.mikylin.litjson.buffer.BufAppendable;
import cn.mikylin.litjson.utils.Constants;

public interface TypeHandler<T> {

    Class[] getTypes();

    T read(String value);

    void append(Object value, BufAppendable append);


    default void writeString(String str,BufAppendable append) {
        append.append(Constants.DOUBLE_QUOTATION_MARK)
              .append(str)
              .append(Constants.DOUBLE_QUOTATION_MARK);
    }

}
