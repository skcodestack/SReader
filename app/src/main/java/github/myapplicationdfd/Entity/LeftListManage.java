package github.myapplicationdfd.Entity;

import java.util.ArrayList;

import github.myapplicationdfd.R;

/**
 * Author root
 * Date: 2016/8/31.
 */
public class LeftListManage {

    private ArrayList<LeftListBean> list=null;
    public  LeftListManage(){
        list=new ArrayList<>();

        list.add(getNews());

        list.add(getImage());
    }
    private  LeftListBean getNews(){
        LeftListBean bean=new LeftListBean();
        bean.setName("新闻快递");
        bean.setIcon(R.mipmap.news_icon);
        return  bean;
    }
    private  LeftListBean getImage(){
        LeftListBean bean=new LeftListBean();
        bean.setName("图片浏览");
        bean.setIcon(R.mipmap.pic_icon);
        return  bean;
    }
    public ArrayList<LeftListBean>  getData(){
        return  list;
    }
}
