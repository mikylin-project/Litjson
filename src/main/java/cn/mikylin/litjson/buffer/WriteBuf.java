package cn.mikylin.litjson.buffer;

import java.util.LinkedList;
import java.util.Queue;

public class WriteBuf implements BufAppendable {

    private Queue<CharSequence> strs = new LinkedList<>();

    private volatile String result = null;

    @Override
    public String toString() {

        if (result == null) {
            synchronized (this) {
                if (result == null) {
                    StringBuilder builder = new StringBuilder();
                    while (!strs.isEmpty()) {
                        CharSequence poll = strs.poll();
                        builder.append(poll);
                    }
                    result = builder.toString();
                }
            }
        }
        return result;
    }

    @Override
    public BufAppendable append(String str) {
        strs.add(str);
        return this;
    }

}
