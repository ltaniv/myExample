package org.knapsack.spring;

import java.util.List;
import java.util.Map;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by admin on 2017-04-14.
 */
public class AjaxFormUtils {


    public static JSONObject jsonMessage(boolean status, String message, Map<String, Object> model, String ... keys){
        JSONObject json = new JSONObject();
        json.put("status",status);
        json.put("data",getErrors(model,keys));
        json.put("msg",message);
        return json;
    }
    public static JSONObject jsonMessage(boolean status, Map<String, Object> model, String ... keys){
        return jsonMessage(status,null,model,keys);
    }
    public static JSONObject jsonMessage(boolean status, String message){
        return jsonMessage(status,message,null,new String[0]);
    }
    public static JSONObject jsonMessage(boolean status){
        return jsonMessage(status,null,null,new String[0]);
    }


    private static JSONObject getErrors(Map<String, Object> model,String ... keys){
        JSONObject json = new JSONObject();
        if(keys.length>0) {
            for (String c : keys) {
                BeanPropertyBindingResult bpbr = (BeanPropertyBindingResult) model.get("org.springframework.validation.BindingResult." + c);
                if (bpbr != null) {
                    List<FieldError> errors = bpbr.getFieldErrors();
                    for (FieldError fe : errors){
                        json.put(fe.getField(), fe.getDefaultMessage());
                    }
                }
            }
        }
        return json;
    }



}
