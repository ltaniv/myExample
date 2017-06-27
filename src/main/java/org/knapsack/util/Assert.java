package org.knapsack.util;

import org.apache.commons.lang.StringUtils;

/**
 * Created by yxyy on 2017-06-27.
 */
public class Assert {
    public static void notEmpty(String str, String message) {
        if(StringUtils.isEmpty(str)) {
            throw new IllegalArgumentException(message);
        }
    }
}
