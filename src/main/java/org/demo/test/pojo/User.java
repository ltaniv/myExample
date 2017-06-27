package org.demo.test.pojo;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.GroupSequence;

/**
 * Created by admin on 2017-04-13.
 */
public class User {

    /* 验证分类 */
    public interface RegisterChecks{}
    public interface LoginChecks{}

    /* 验证顺序 *
    @GroupSequence({OneChecks.class,TwoChecks.class,ThreeChecks.class})
    public interface OrderedChecks {}*/

    @NotBlank(groups = {RegisterChecks.class,LoginChecks.class})
    private String name;

    @Email(groups = RegisterChecks.class)
    private String email;
    @NotEmpty(groups = {RegisterChecks.class,LoginChecks.class})
    private String password;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
