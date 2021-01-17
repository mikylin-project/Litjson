package cn.mikylin.litjson;

import java.util.Date;
import java.util.List;

public class Person {

    // 年龄
    private Integer age;
    // 名字
    private String name;
    // 生日
    private Date birthDay;
    // 性别 true - male，false - female
    private Boolean sex;
    // 配偶
    private Person spouse;
    // 家人
    private List<Person> families;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Person getSpouse() {
        return spouse;
    }

    public void setSpouse(Person spouse) {
        this.spouse = spouse;
    }

    public List<Person> getFamilies() {
        return families;
    }

    public void setFamilies(List<Person> families) {
        this.families = families;
    }
}
