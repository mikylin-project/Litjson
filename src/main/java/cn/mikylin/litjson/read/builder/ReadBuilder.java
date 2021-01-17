package cn.mikylin.litjson.read.builder;

import cn.mikylin.litjson.buffer.*;
import cn.mikylin.litjson.utils.*;
import static cn.mikylin.litjson.utils.Constants.*;

public abstract class ReadBuilder<T> extends ProxyCharBuf {

    //tache type
    protected TacheJsonAction readTache;

    T result;

    protected ReadBuilder(CharBuf buffer,TacheJsonAction readTache) {
        super(buffer);
        initKey();
        // init the control type and read tache
        this.readTache = readTache;
    }

    // char[] to save key
    private CharBuf key;

    public T getResult() {
        return result;
    }

    protected void setKey(CharBuf key) {
        this.key = key;
    }

    protected String getKey() {
        return key.toString();
    }

    /**
     * clear the key char[] and value char[]
     */
    protected void initKey() {
        key = CharBuffer.NON;
    }

    protected void parseValue(char readChar) {

        if (readChar == DOUBLE_QUOTATION_MARK) { // readChar = '"'
            CharBuf value = readString();
            invokeBaseParam(value);
        } else if (readChar == OPEN_CURLY) { // readChar = '{'
            CharBuf value = readObject();
            parseObject(value);
        } else if (readChar == OPEN_SQUAREBRACKETS) { // readChar = '['
            CharBuf value = readArray();
            parseArray(value);
        } else {
            CharBuf value = readTil(false,COMMA,CLOSE_CURLY,BLANK,LINE_FEED);
            if (value.equals("null"))
                invokeVoid();
            else
                invokeBaseParam(value);

            char c = read();
            if (BlankCharUtils.isBlank(c))
                readTileNotBlank();
            if (c == COMMA)
                offset(-1);
        }
    }

    abstract void invokeVoid();

    /**
     * set the value to the method
     */
    abstract void invokeBaseParam(CharBuf value);

    /**
     * recursion create object
     */
    abstract void parseObject(CharBuf value);

    /**
     * recursion create array
     */
    abstract void parseArray(CharBuf value);
}
