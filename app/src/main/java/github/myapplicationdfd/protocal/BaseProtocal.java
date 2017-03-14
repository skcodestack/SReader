package github.myapplicationdfd.protocal;

import android.util.Log;


import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Author root
 * Date: 2016/8/29.
 */
public class BaseProtocal {

    HashMap<String,String> prarms=null;
    HashMap<String,String> heads=null;
    public  void  addParam(String key,String Value){
        if(prarms==null){
            prarms=new HashMap<>();
        }
        prarms.put(key,Value);

    }
    public  void  addHeader(String key,String Value){
        if(heads==null){
            heads=new HashMap<>();
        }
        heads.put(key,Value);
    }
    public  void  getNetData(String  uri, HashMap<String,String> Prarmmap,HashMap<String,String> Headmap, Callback.CommonCallback callBack){
//        HttpUtils httpUtils=new HttpUtils();
//        RequestParams params=new RequestParams();
//        if(Headmap!=null){
//            for (Map.Entry<String,String> entry: Headmap.entrySet()) {
//                params.addHeader(entry.getKey(),entry.getValue());
//            }
//        }
//        if(Prarmmap!=null){
//            for (Map.Entry<String,String> entry: Prarmmap.entrySet()) {
//                params.addBodyParameter(entry.getKey(),entry.getValue());
//            }
//        }
//        httpUtils.send(com.lidroid.xutils.http.client.HttpRequest.HttpMethod.GET, uri,params,callBack);

        RequestParams requestParams=new RequestParams(uri);
        requestParams.setAsJsonContent(true);
        if(Headmap!=null){
            for (Map.Entry<String,String> entry: Headmap.entrySet()) {
                requestParams.addHeader(entry.getKey(),entry.getValue());
            }
        }
        if(Prarmmap!=null){
            for (Map.Entry<String,String> entry: Prarmmap.entrySet()) {
                requestParams.addQueryStringParameter(entry.getKey(),entry.getValue());
            }
        }

        x.http().get(requestParams,callBack);

    }
    public  void  getNetData(String  uri, Callback.CommonCallback callBack) {
        getNetData(uri,prarms,heads,callBack);

    }

}
