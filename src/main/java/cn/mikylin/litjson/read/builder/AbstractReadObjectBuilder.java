package cn.mikylin.litjson.read.builder;

import cn.mikylin.litjson.buffer.CharBuf;
import cn.mikylin.litjson.utils.TacheJsonAction;
import cn.mikylin.litjson.utils.Asserts;
import static cn.mikylin.litjson.utils.Constants.*;

public abstract class AbstractReadObjectBuilder<T> extends ReadBuilder<T> {

    protected AbstractReadObjectBuilder(CharBuf buffer) {
        super(buffer,TacheJsonAction.TACHE_BEGIN_READ_KEY);
    }

    /**
     * boolean the type and do the work
     */
    protected void buildObject() {

        char beginChar = readTileNotBlank();

        Asserts.charEqualsAssert(beginChar,OPEN_CURLY,
                "json object should begin with '{'");

        while (canRead()) {

            next();
            char readChar = readTileNotBlank();

            if (TacheJsonAction.isBeginReadKey(readTache)) { // begin to read a key
                Asserts.charEqualsAssert(readChar,DOUBLE_QUOTATION_MARK,
                        "json key should begin with '\"'");
                CharBuf key = readString();
                setKey(key);

                next();
                readChar = readTileNotBlank();
                Asserts.charEqualsAssert(readChar,COLON,"json miss ':'");
                readTache = TacheJsonAction.TACHE_BEGIN_READ_VALUE;
            } else if (TacheJsonAction.isBeginReadValue(readTache)) { // begin to read a value
                parseValue(readChar);
                readTache = TacheJsonAction.TACHE_END_READ_VALUE;
                initKey();
            } else if (TacheJsonAction.isEndReadValue(readTache)) { // value read end
                if (readChar == COMMA) // readChar = ','
                    readTache = TacheJsonAction.TACHE_BEGIN_READ_KEY;
                else if (readChar == CLOSE_CURLY) // readChar = '}'
                    break;
            }
        }
    }

}