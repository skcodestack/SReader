package github.myapplicationdfd.fragment;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nshmura.recyclertablayout.RecyclerTabLayout;

import org.xutils.common.Callback;

import java.util.ArrayList;

import github.myapplicationdfd.CommonConifg;
import github.myapplicationdfd.Entity.NewsTabBean;
import github.myapplicationdfd.Entity.NewsTitleBean;
import github.myapplicationdfd.Helper.ViewHelperControl;
import github.myapplicationdfd.R;
import github.myapplicationdfd.pager.BasePager;
import github.myapplicationdfd.pager.NewsTabPager;
import github.myapplicationdfd.pager.PagerCreater;
import github.myapplicationdfd.pager.PagerManageUtil;
import github.myapplicationdfd.protocal.BaseProtocal;
import github.myapplicationdfd.utils.CommonUtils;
import github.myapplicationdfd.utils.Json2bean;
import github.myapplicationdfd.view.XViewPager;

/**
 * Created by PC on 2016/8/26.
 *
 *
 * initView-->getTarView--->initDate
 */
public class NewsFragment extends BaseFragment{

    private LinearLayout content;
    private PagerManageUtil pagerManageUtil;

    @Override
    protected View getTarView() {
        return content;
    }

    @Override
    public void initDate() {
        showLoading("loading...");
//        String  uri=CommonConifg.news_title_tab;
//        getNetWorkData(uri);
        getData();
    }
    private XViewPager xvp;
    private RecyclerTabLayout tabLayout;
    @Override
    protected View  initView(){
        View inflate = mActivity.getLayoutInflater().inflate(R.layout.framgment_pager,null);
        content = (LinearLayout) inflate.findViewById(R.id.pager_content);
        xvp = (XViewPager) inflate.findViewById(R.id.xvp);
        tabLayout = (RecyclerTabLayout) inflate.findViewById(R.id.tablayout);
        pagerManageUtil = new PagerManageUtil();
        return  inflate;
    }



    public  void  getNetWorkData(String uri){

//        BaseProtocal protocal=new BaseProtocal();
//        protocal.addHeader(CommonConifg.key_name, CommonConifg.key_value);
//        protocal.getNetData(uri, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                if(!CommonUtils.isEmpty(result)) {
//                    Json2bean parse=new Json2bean();
//                    NewsTabBean bean= (NewsTabBean) parse.toBean(result, NewsTabBean.class);
//                    showSuccessView(bean);
//                }else{
//                    Toast.makeText(mActivity,"数据请求失败，请检查网络是否连接正常！",Toast.LENGTH_SHORT).show();
//                    showError("数据请求失败",new View.OnClickListener(){
//
//                        @Override
//                        public void onClick(View v) {
//                            initDate();
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                Toast.makeText(mActivity,"数据请求失败，请检查网络是否连接正常！",Toast.LENGTH_SHORT).show();
//                showError("数据请求失败",new View.OnClickListener(){
//
//                    @Override
//                    public void onClick(View v) {
//                        initDate();
//                    }
//                });
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
    }

    public void getData(){
        ArrayList<NewsTitleBean> list=new ArrayList<>();
        String[] names = mActivity.getResources().getStringArray(R.array.news_category_list_names);
        String[] ids = mActivity.getResources().getStringArray(R.array.news_category_list_ids);
        NewsTitleBean bean=null;
        for (int i=0;i<ids.length;i++) {
            bean=new NewsTitleBean();
            bean.setCode(ids[i]);
            bean.setName(names[i]);
            list.add(bean);
        }

        MyAdapter adapter=new MyAdapter(list);
        xvp.setOffscreenPageLimit(list.size());
        xvp.setAdapter(adapter);
        tabLayout.setUpWithViewPager(xvp);
        showSuccessView();
    }

    private  void  showSuccessView(NewsTabBean bean){
//        if(bean!=null && bean.getChannelList()!=null &&  bean.getChannelList().size()>0){
//            MyAdapter adapter=new MyAdapter(bean.getChannelList());
//            xvp.setAdapter(adapter);
//            tabLayout.setUpWithViewPager(xvp);
//            showSuccessView();
//        }else{
//            showEmpty("数据为空",null);
//        }
    }
    public  class  MyAdapter extends PagerAdapter{
        private  ArrayList<NewsTitleBean> list;
        public MyAdapter(ArrayList<NewsTitleBean> list) {
            this.list=list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==(View)object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            TextView tv=new TextView(mActivity);
//            tv.setText(list.get(position).getName()+"=="+list.get(position).getChannelId());
//            container.addView(tv);
            if(pagerManageUtil==null){
                pagerManageUtil=new PagerManageUtil();
            }
            BasePager pager = pagerManageUtil.get(list.get(position).getCode());
            if(pager==null) {
                pager = PagerCreater.getInstance().createNewsPager(mActivity, list.get(position).getCode());
                pager.initData();
                pagerManageUtil.put(list.get(position).getCode(),pager);
            }
            container.addView(pager.getView());
            return  pager.getView();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return list.get(position).getName();
        }
    }


}
