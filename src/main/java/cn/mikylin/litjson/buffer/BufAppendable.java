package cn.mikylin.litjson.buffer;

public interface BufAppendable {

    BufAppendable append(String str);

    default BufAppendable append(char c) {
        return append(String.valueOf(c));
    }
}
