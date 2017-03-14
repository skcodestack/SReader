package github.myapplicationdfd.Entity;

import java.util.ArrayList;

/**
 * Author root
 * Date: 2016/8/30.
 */
public class NewsTabBean extends BaseBean {
    /*"showapi_res_code": 0,
	"showapi_res_error": "",
	"showapi_res_body": {
		"channelList": [
			{
				"channelId": "5572a108b3cdc86cf39001cd",
				"name": "国内焦点"
			},*/



    private ArrayList<Channel> channelList;

    public ArrayList<Channel> getChannelList() {
        return channelList;
    }

    public void setChannelList(ArrayList<Channel> channelList) {
        this.channelList = channelList;
    }

    /**
     * 频道类
     */
    public static  class Channel{
        private String  channelId;
        private  String  name;

        public String getChannelId() {
            return channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
