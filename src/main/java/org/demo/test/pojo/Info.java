package org.demo.test.pojo;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

/**
 * Created by admin on 2017-04-16.
 */
public class Info {

    @NotEmpty
    private int age;
    @NotEmpty
    private Date birthday;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }


}
