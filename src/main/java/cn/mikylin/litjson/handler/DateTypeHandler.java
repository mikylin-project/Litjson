package cn.mikylin.litjson.handler;

import cn.mikylin.litjson.buffer.BufAppendable;
import cn.mikylin.litjson.utils.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DateTypeHandler implements TypeHandler<Date> {

    @Override
    public Class[] getTypes() {
        return new Class[] {Date.class};
    }

    //option for jwriter to serialize the json string
    private DateFormat writeDateFormat;

    //option for jreader to unserialize the json string
    private List<DateFormat> readParseFormats;

    public void setReadParseFormats(List<DateFormat> readParseFormats) {
        this.readParseFormats = readParseFormats;
    }

    public void setWriteDateFormat(DateFormat format) {
        this.writeDateFormat = format;
    }

    @Override
    public Date read(String value) {
        if (StringUtils.isBlank(value)
                || Objects.equals(value,"null"))
            return null;

        for(DateFormat dateFormat : readParseFormats) {
            try {
                return dateFormat.parse(value);
            } catch (ParseException e) { }
        }
        throw new ClassCastException("the string trans to Date exception");
    }

    @Override
    public void append(Object value, BufAppendable append) {
        Date d = (Date)value;
        String format = writeDateFormat.format(d);
        writeString(format,append);
    }

}
