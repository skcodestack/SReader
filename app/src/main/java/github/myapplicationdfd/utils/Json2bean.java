package github.myapplicationdfd.utils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author root
 * Date: 2016/8/30.
 */
public class Json2bean {

    private  static  String  return_code="code";
    private  static  String  return_msg="msg";
    private  static  String  return_content="showapi_res_body";

    private    String  error="";

    public  String getError(){
        return error;
    }

    public  String getReturn_code() {
        return return_code;
    }

    public  void setReturn_code(String return_code) {
        Json2bean.return_code = return_code;
    }

    public  String getReturn_content() {
        return return_content;
    }

    public  void setReturn_content(String return_content) {
        Json2bean.return_content = return_content;
    }

    public  String getReturn_msg() {
        return return_msg;
    }

    public  void setReturn_msg(String return_msg) {
        Json2bean.return_msg = return_msg;
    }

    /**
     * to  bean
     * @param json
     * @param clazz
     * @return
     */
    public    Object  toBean(String  json, Class clazz){
        try {
            JSONObject obj=new JSONObject(json);
            if("200".equals(obj.getString(return_code))){
                Gson gson=new Gson();
                Object result = gson.fromJson(json, clazz);
                return  result;
            }else{
                  error=obj.getString(return_msg);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            error=e.getMessage();
        }
        return  null;
    }

}
