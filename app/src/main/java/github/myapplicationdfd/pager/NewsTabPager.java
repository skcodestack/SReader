package github.myapplicationdfd.pager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;

import github.myapplicationdfd.CommonConifg;
import github.myapplicationdfd.Entity.NewsPagerBean;
import github.myapplicationdfd.Entity.NewsRequestParam;
import github.myapplicationdfd.Entity.NewsTabBean;
import github.myapplicationdfd.NewsDetailActivity;
import github.myapplicationdfd.R;
import github.myapplicationdfd.protocal.BaseProtocal;
import github.myapplicationdfd.utils.CommonUtils;
import github.myapplicationdfd.utils.Constants;
import github.myapplicationdfd.utils.Json2bean;
import github.myapplicationdfd.view.RefreshListView;
import github.myapplicationdfd.view.TopNewsViewPager;

/**
 * Author root
 * Date: 2016/8/30.
 */
public class NewsTabPager extends BasePager {

    private  String  code="";
    private RefreshListView refreshListView;
    private ArrayList<NewsPagerBean.NewsInfo> mList=new ArrayList<>();
    //当前页
    private  int curPage=1;
    private ListViewAdapter adapter;
    private boolean isMore;
    private TopNewsViewPager headerViewPager;
    private TextView top_title;
    private TextView tv_number;
    private View head_view;

    public NewsTabPager(Activity mActivity,String code) {
        super(mActivity);
        this.code=code;
    }

    @Override
    public View initView() {
        View view = mActivity.getLayoutInflater().inflate(R.layout.news_pager_layout, null);
        refreshListView = (RefreshListView) view.findViewById(R.id.common_refresh_listview);

        initEvent();
        return view;
    }

    @Override
    protected void onCreatedView() {
        showLoading("loading...");
    }

    private void initEvent() {
        //头部滑动视图
        head_view = mActivity.getLayoutInflater().inflate(R.layout.pager_header_tab, null);
        headerViewPager = (TopNewsViewPager) head_view.findViewById(R.id.vp_header);
        top_title = (TextView) head_view.findViewById(R.id.tv_top_title);
        tv_number = (TextView) head_view.findViewById(R.id.tv_number);

        refreshListView.addHeaderView(head_view);

        refreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(mList!=null && mList.size()>0){
                        NewsPagerBean.NewsInfo content = mList.get(position);
                        if(content!=null) {
                            openHtml(content.getUrl(),content.getTitle());
                        }
                    }
            }
        });
        refreshListView.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                NewsRequestParam params=new NewsRequestParam();
                params.setNum(Constants.pageSize);
                params.setKey(CommonConifg.key_value);
                params.setPage("1");
                curPage=1;
                getNetWorkData(params,false);
            }

            @Override
            public void onLoadMore() {
                if(isMore){
                    getMorePage();

                }else{
                    Toast.makeText(mActivity, "已经是最后一页", Toast.LENGTH_SHORT).show();
                    refreshListView.onRefreshComplete(false);
                }
            }
        });
    }

    private  void  getMorePage(){
        NewsRequestParam params=new NewsRequestParam();
        params.setNum(Constants.pageSize);
        params.setKey(CommonConifg.key_value);
        params.setPage(curPage+"");
        getNetWorkData(params,true);
    }

    @Override
    public void initData() {
        showLoading("loading...");
        NewsRequestParam params=new NewsRequestParam();
        params.setNum(Constants.pageSize);
        params.setKey(CommonConifg.key_value);
        params.setPage("1");
        curPage=1;
        getNetWorkData(params,false);
    }


    public  void  getNetWorkData(NewsRequestParam params, final boolean isLoadMore){
        String  uri=CommonConifg.news_pager_content+code+"/";
        BaseProtocal protocal=new BaseProtocal();
//        protocal.addHeader(CommonConifg.key_name,CommonConifg.key_value);

        uri=uri+"?"+params.getParams();

        protocal.getNetData(uri, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //解析数据
                Prase(result,isLoadMore);
                refreshListView.onRefreshComplete(true);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                refreshListView.onRefreshComplete(false);
                if(!isLoadMore) {
                    showError("" + ex.getMessage(), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            initData();
                        }
                    });
                }else{
                    Toast.makeText(mActivity,"加载更多数据失败！",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /***
     * 解析数据
     * @param result
     * @param isloadmore
     */
    private  void  Prase(String result,boolean isloadmore){
        if(!isloadmore) {
            //刷新
            if (!CommonUtils.isEmpty(result)) {
                Json2bean parse = new Json2bean();
                NewsPagerBean bean = (NewsPagerBean) parse.toBean(result, NewsPagerBean.class);

                if (bean != null && "200".equalsIgnoreCase(bean.getCode())) {
                    showSuccessView(bean,isloadmore);
                } else {
                    showError("数据请求失败:" + parse.getError(), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            initData();
                        }
                    });
                }

            } else {
                Toast.makeText(mActivity, "数据请求失败，请检查网络是否连接正常！", Toast.LENGTH_SHORT).show();
                showError("数据请求失败", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        initData();
                    }
                });
            }
        }else{
            //加载更多
            if (!CommonUtils.isEmpty(result)) {
                Json2bean parse = new Json2bean();
                NewsPagerBean bean = (NewsPagerBean) parse.toBean(result, NewsPagerBean.class);

                if (bean != null && "200".equalsIgnoreCase(bean.getCode())) {
                    showSuccessView(bean,isloadmore);
                } else {
                    Toast.makeText(mActivity, "加载更多数据失败！", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(mActivity, "加载更多数据失败！", Toast.LENGTH_SHORT).show();

            }
        }
    }

    /**
     * 显示成功界面
     * @param bean
     */
    private  void  showSuccessView(NewsPagerBean bean,boolean isloadmore){
        if(bean.getNewslist()!=null
                && bean.getNewslist().size()>0) {
            //是否可以加载更多
//            int allPages = bean.getNewslist().getAllPages();
            int allPages=1000;
            isMore = curPage<allPages;
            curPage++;
            if(!isloadmore) {

                createTopViewPager(bean.getNewslist());
                adapter = new ListViewAdapter();
                refreshListView.setAdapter(adapter);
//                Toast.makeText(mActivity, "刷新成功！", Toast.LENGTH_SHORT).show();
            }else{
                mList.addAll(bean.getNewslist());
                if(adapter!=null){
                    adapter.notifyDataSetChanged();
                    Toast.makeText(mActivity, "加载成功！", Toast.LENGTH_SHORT).show();
                }

            }
            showSuccessView();

        }else{
            if(!isloadmore){
                showEmpty(null);
            }else{
                Toast.makeText(mActivity, "加载数据为空！", Toast.LENGTH_SHORT).show();
            }
        }

    }


    private  void  createTopViewPager(ArrayList<NewsPagerBean.NewsInfo> list){
        int size = list.size();
        final ArrayList<NewsPagerBean.NewsInfo> TopList=new ArrayList<>();
        ArrayList<NewsPagerBean.NewsInfo> ContentList=new ArrayList<>(list);
        size=size>10?10:0;

            for (int i = 0; i < size; i++) {
                NewsPagerBean.NewsInfo bean = list.get(i);
                String imageurls = bean.getPicUrl();
                if (!TextUtils.isEmpty(imageurls)) {
                    TopList.add(bean);
                    ContentList.remove(bean);
                }
            }

        if(TopList!=null && TopList.size()>0){

            TopViewPagerAdapter viewPagerAdapter= new TopViewPagerAdapter(TopList);

            headerViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    int TopCount = TopList.size();
                    NewsPagerBean.NewsInfo info = TopList.get(position);
                    setTopTitle(info,position,TopCount);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            headerViewPager.setAdapter(viewPagerAdapter);
            int TopCount = TopList.size();
            NewsPagerBean.NewsInfo info = TopList.get(0);
            setTopTitle(info,0,TopCount);

        }else{
            refreshListView.removeHeaderView(head_view);
        }

        mList=ContentList;
    }


private  void  setTopTitle(NewsPagerBean.NewsInfo info,int pos,int count){

    if(info!=null){
        if(!CommonUtils.isEmpty(info.getTitle())) {
            top_title.setText(info.getTitle().trim());
        }
    }
    tv_number.setText((pos+1)+"/"+count);
}
    @Override
    public View getTagerView() {
        return refreshListView;
    }


   private   class  ListViewAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mList==null?0:mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder  viewHolder;
            View  view;
            if(convertView!=null){
                viewHolder= (ViewHolder) convertView.getTag();
                view=convertView;
            }else{
                viewHolder=new ViewHolder();
                View  inflate=mActivity.getLayoutInflater().inflate(R.layout.news_list_item,null);
                viewHolder.iv= (ImageView) inflate.findViewById(R.id.iv_listimage);
                viewHolder.title= (TextView) inflate.findViewById(R.id.tv_listTile);
                viewHolder.time= (TextView) inflate.findViewById(R.id.tv_listTime);
                inflate.setTag(viewHolder);
                view=inflate;
            }

            NewsPagerBean.NewsInfo bean = mList.get(position);
            String  imageurls = bean.getPicUrl();
            if(!TextUtils.isEmpty(imageurls)) {
                viewHolder.iv.setVisibility(View.VISIBLE);
                ImageOptions opt = new ImageOptions.Builder()
                        .setConfig(Bitmap.Config.ARGB_4444)
                        .setCrop(true)
                        .setFadeIn(true)
                        .setSize(viewHolder.iv.getWidth(), viewHolder.iv.getHeight())
                        .setUseMemCache(true)
                        .setIgnoreGif(true)
                        .build();
                x.image().bind(viewHolder.iv, imageurls, opt);
            }else{
                viewHolder.iv.setImageResource(R.mipmap.pic_item_list_default);
//                viewHolder.iv.setVisibility(View.GONE);
            }
            if(!CommonUtils.isEmpty(bean.getTitle())) {
                viewHolder.title.setText(bean.getTitle().trim());
            }
            viewHolder.time.setText(bean.getCtime());
            return  view;
        }
    }


    public  class  ViewHolder{
        public ImageView iv;
        public TextView  title;
        public TextView  time;
    }



    private  class  TopViewPagerAdapter extends PagerAdapter{
        private  ArrayList<NewsPagerBean.NewsInfo> topList;
        public TopViewPagerAdapter(ArrayList<NewsPagerBean.NewsInfo> topList){
            this.topList=topList;
        }
        @Override
        public int getCount() {
            return (topList!=null)?topList.size():0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==(View)object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
           container.removeView((View)object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView  iv=new ImageView(mActivity);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(R.mipmap.pic_item_list_default);
            ImageOptions opt = new ImageOptions.Builder()
                    .setConfig(Bitmap.Config.ARGB_4444)
                    .setCrop(true)
                    .setFadeIn(true)
                    .setSize(iv.getWidth(), iv.getHeight())
                    .setUseMemCache(true)
                    .setIgnoreGif(true)
                    .build();

            final NewsPagerBean.NewsInfo bean = topList.get(position);

            x.image().bind(iv, bean.getPicUrl(), opt);
            iv.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    openHtml(bean.getUrl(),bean.getTitle());
                }
            });
            container.addView(iv);
            return iv;
        }
    }


    private  void  openHtml(String url,String title){
        Intent intent  =new Intent(mActivity, NewsDetailActivity.class);
        intent.putExtra("url",url);
        intent.putExtra("title",title);
        mActivity.startActivity(intent);

    }
}
