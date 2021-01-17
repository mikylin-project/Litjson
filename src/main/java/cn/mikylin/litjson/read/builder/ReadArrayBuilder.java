package cn.mikylin.litjson.read.builder;

import cn.mikylin.litjson.buffer.CharBuf;
import cn.mikylin.litjson.handler.TypeHandlers;
import cn.mikylin.litjson.utils.*;
import java.util.List;
import static cn.mikylin.litjson.utils.Constants.*;

public final class ReadArrayBuilder extends ReadBuilder<List> {

    //object class
    private Class clz;

    public ReadArrayBuilder(CharBuf buffer,Class clz) {
        super(buffer,TacheJsonAction.TACHE_BEGIN_READ_VALUE);
        this.clz = Commons.checkNotNullOrMap(clz);
        result = Commons.newArrayList();
        buildCollection();
    }

    private void buildCollection() {

        char beginChar = readTileNotBlank();

        Asserts.charEqualsAssert(beginChar,OPEN_SQUAREBRACKETS,
                "json array should begin with '['");

        while (true) {

            next();
            char readChar = readTileNotBlank();

            if (TacheJsonAction.isBeginReadValue(readTache)) {
                parseValue(readChar);
                readTache = TacheJsonAction.TACHE_END_READ_VALUE;
                initKey();
            } else if (TacheJsonAction.isEndReadValue(readTache)) {
                if(readChar == COMMA) // readChar = ','
                    readTache = TacheJsonAction.TACHE_BEGIN_READ_VALUE;
                else if (readChar == CLOSE_SQUAREBRACKETS) // readChar = ']'
                    break;
            }
        }
    }

    @Override
    void invokeVoid() {
        result.add(null);
    }

    @Override
    void invokeBaseParam(CharBuf value) {
        String valueStr = value.toString();
        Object val = TypeHandlers.read(clz,valueStr);
        result.add(val);
    }

    @Override
    void parseObject(CharBuf value) {
        ReadBuilder builder = Commons.isMap(clz) ?
                new ReadMapBuilder(value) : new ReadObjectBuilder(value,clz);
        Object res = builder.getResult();
        result.add(res);
    }

    @Override
    void parseArray(CharBuf value) {
        List res = new ReadArrayBuilder(value,clz).getResult();
        result.add(res);
    }
}