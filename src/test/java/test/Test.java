package test;

import java.lang.reflect.Method;

/**
 * Created by admin on 2017-04-12.
 */
public class Test {

    public static void main(String[] args) throws NoSuchMethodException {
      /*  StackTraceElement[] stack = new Throwable().getStackTrace();
        String m=stack[0].getMethodName();
        Method method = Test.class.getMethod(m,String[].class);*/
        System.out.printf("fdf=_".matches("^\\w+=\\w+$")+"-");
    }
}
