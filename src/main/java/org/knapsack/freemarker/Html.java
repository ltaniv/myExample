package org.knapsack.freemarker;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Html{
    boolean use() default true;
    String file();
}