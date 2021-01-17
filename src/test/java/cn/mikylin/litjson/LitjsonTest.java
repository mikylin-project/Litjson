package cn.mikylin.litjson;

import cn.mikylin.litjson.read.JReader;
import cn.mikylin.litjson.write.JWriter;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LitjsonTest {

    @Test
    public void writeTest () {
        Person p = new Person();
        p.setName("xiao ming");
        p.setAge(18);
        p.setBirthDay(new Date());
        p.setSex(true);

        Person p1 = new Person();
        p1.setSex(false);

        p.setSpouse(p1);

        List<Person> personList = new ArrayList<>();
        personList.add(p1);
        p.setFamilies(personList);

        JWriter writer = JWriter.create(p);
        String json = writer.toString();
        System.out.println(json);
    }


    private static String json1 = "{\"birthDay\":\"2021-01-05 22:51:49\"}";
    private static String json2 =
            "{" +
                "\"birthDay\":\"2021-01-05 22:51:49\"," +
                "\"sex\":true," +
                "\"name\":\"xiao ming\"," +
                "\"families\":[" +
                    "{" +
                        "\"birthDay\":null," +
                        "\"sex\":false," +
                        "\"name\":null," +
                        "\"families\":null," +
                        "\"age\":null," +
                        "\"spouse\":null" +
                    "}" +
                "]," +
                "\"age\":18," +
                "\"spouse\":{" +
                    "\"birthDay\":null," +
                    "\"sex\":false," +
                    "\"name\":null," +
                    "\"families\":null," +
                    "\"age\":null," +
                    "\"spouse\":null" +
                "}" +
            "}";

    @Test
    public void readTest () {

        JReader reader = JReader.object(json2,Person.class);
        Person read = (Person)reader.read();
        String name = read.getName();
        System.out.println(name);

        JWriter writer = JWriter.create(read);
        System.out.println(writer.toString());
    }
}
