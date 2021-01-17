package cn.mikylin.litjson.write;

import cn.mikylin.litjson.buffer.WriteBuf;
import cn.mikylin.litjson.handler.TypeHandlers;
import cn.mikylin.litjson.utils.Constants;
import cn.mikylin.litjson.utils.InvokeUtils;
import java.util.*;

import static cn.mikylin.litjson.utils.Constants.COLON;

public class WriteBuilder extends WriteBuf {

    public WriteBuilder(Object obj) {
        writeKV(null,obj);
    }

    private void writeKV(String key,Object obj) {

        if (tryWriteBase(key,obj))
            return;

        if (obj instanceof Map) {
            writeMap((Map) obj);
        } else if (obj instanceof Collection) {
            writeCollection((Collection) obj);
        } else {
            writeObject(obj);
        }
    }

    private void writeObject(Object value) {
        Map<String,Object> map = InvokeUtils.toMap(value);
        writeMap(map);
    }

    private void writeMap(Map value) {

        append(Constants.OPEN_CURLY);

        Set<Map.Entry> set = value.entrySet();
        int count = 0;
        int max = set.size() - 1;
        for (Map.Entry e : set) {
            writeKV(e.getKey().toString(),e.getValue());
            if (count ++ < max) {
                append(Constants.COMMA);
            }
        }
        append(Constants.CLOSE_CURLY);
    }

    private void writeCollection(Collection value) {

        append(Constants.OPEN_SQUAREBRACKETS);

        int size = value.size() - 1;

        List list = new ArrayList(value);

        for (int i = 0 ; i <= size ; i ++) {
            Object o = list.get(i);
            writeKV(null,o);
            if (i != size)
                append(Constants.COMMA);
        }
        append(Constants.CLOSE_SQUAREBRACKETS);
    }


    private boolean tryWriteBase(String key,Object obj) {

        if (key != null) {
            TypeHandlers.append(key,this);
            append(COLON);
        }
        return TypeHandlers.append(obj,this);
    }




}
