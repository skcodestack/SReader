package github.myapplicationdfd.Entity;

import github.myapplicationdfd.utils.CommonUtils;

/**
 * Author root
 * Date: 2016/8/30.
 */
public class NewsRequestParam extends BaseBean {

    /*
    * channelId  string	否	urlParam	新闻频道id，必须精确匹配 5572a109b3cdc86cf39001db
channelName string	否	urlParam	新闻频道名称，可模糊匹配 国内最新
title string	否	urlParam	新闻标题，模糊匹配 上市
page string	否	urlParam	页数，默认1。每页最多20条记录。展开	1
needContent string	否	urlParam	是否需要返回正文，1为需要，其他为不需要展开	 0
needHtml string	否	urlParam	 是否需要返回正文的html格式，1为需要，其他为不需要 展开	0

*/

//    private  String  channelId;
//    private  String  channelName;
//    private  String  title;
//    private  String  page;
//    private  String  needContent;
//    private  String  needHtml;
//
//    public String getChannelId() {
//        return channelId;
//    }
//
//    public void setChannelId(String channelId) {
//        this.channelId = channelId;
//    }
//
//    public String getChannelName() {
//        return channelName;
//    }
//
//    public void setChannelName(String channelName) {
//        this.channelName = channelName;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getPage() {
//        return page;
//    }
//
//    public void setPage(String page) {
//        this.page = page;
//    }
//
//    public String getNeedContent() {
//        return needContent;
//    }
//
//    public void setNeedContent(String needContent) {
//        this.needContent = needContent;
//    }
//
//    public String getNeedHtml() {
//        return needHtml;
//    }
//
//    public void setNeedHtml(String needHtml) {
//        this.needHtml = needHtml;
//    }
//
//    public  String   getParams(){
//        StringBuilder sb=new StringBuilder();
//        sb.append("channelId="+getChannelId());
//        if(!CommonUtils.isEmpty(getChannelName())){
//            sb.append("&channelName="+getChannelName());
//        }
//        if(!CommonUtils.isEmpty(getNeedContent())){
//            sb.append("&needContent="+getNeedContent());
//        }
//        if(!CommonUtils.isEmpty(getNeedHtml())){
//            sb.append("&needHtml="+getNeedHtml());
//        }
//        if(!CommonUtils.isEmpty(getPage())){
//            sb.append("&page="+getPage());
//        }
//        if(!CommonUtils.isEmpty(getTitle())){
//            sb.append("&title="+getTitle());
//        }
//        return  sb.toString();
//    }
    /*key	string	是	urlParam	API密钥（请在个人中心获取）	用户自己的key
    num	int	是	urlParam	指定返回数量，最大80	10
    rand	int	否	urlParam	参数值为1则随机获取	0
    word	string	否	urlParam	检索关键词	上海
    page	int	否	urlParam	翻页，每页输出数量跟随num参数	1*/

    private String key;
    private String num;
    private String rand;
    private String word;
    private String page;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getRand() {
        return rand;
    }

    public void setRand(String rand) {
        this.rand = rand;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public  String   getParams(){
        StringBuilder sb=new StringBuilder();
        sb.append("key="+getKey());
        if(!CommonUtils.isEmpty(getNum())){
            sb.append("&num="+getNum());
        }
        if(!CommonUtils.isEmpty(getRand())){
            sb.append("&rand="+getRand());
        }
        if(!CommonUtils.isEmpty(getWord())){
            sb.append("&word="+getWord());
        }
        if(!CommonUtils.isEmpty(getPage())){
            sb.append("&page="+getPage());
        }
        return  sb.toString();
    }
}
