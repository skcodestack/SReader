package github.myapplicationdfd;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import github.myapplicationdfd.Entity.PictureItemBean;
import github.myapplicationdfd.Entity.PicturePagerBean;
import github.myapplicationdfd.Entity.PicturesListRequestParam;
import github.myapplicationdfd.activity.base.BaseAppCompateActivity;
import github.myapplicationdfd.adapter.MyRecycleAdapter;
import github.myapplicationdfd.adapter.PictureRecycleAdapter;
import github.myapplicationdfd.listener.NavigateNewPageInterface;
import github.myapplicationdfd.listener.OnRecyclerViewScrollListener;
import github.myapplicationdfd.pager.PictureTabPager;
import github.myapplicationdfd.protocal.BaseProtocal;
import github.myapplicationdfd.utils.CommonUtils;
import github.myapplicationdfd.utils.Constants;
import github.myapplicationdfd.view.XSwipeRefreshLayout;

/**
 * Author root
 * Date: 2016/8/31.
 */
public class PicturesDetailActivity extends BaseAppCompateActivity implements SwipeRefreshLayout.OnRefreshListener, NavigateNewPageInterface<String> {


    private Toolbar toolbar;
    private XSwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private String id;
    private PictureRecycleAdapter myAdapter;

    @Override
    protected void initEvents() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PicturesDetailActivity.this.finish();
            }
        });
        id = getIntent().getStringExtra("id");


        ArrayList<PictureItemBean.Picture> mList=new ArrayList<>();
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        myAdapter = new PictureRecycleAdapter(mList,this);

        mRecyclerView.setAdapter(myAdapter);
//        mRecyclerView.addOnScrollListener(new OnRecyclerViewScrollListener<PicturePagerBean.Gallery>(){
//
//            @Override
//            public void onStart() {
//                myAdapter.setFooterView(R.layout.item_footer);
//
//                if (myAdapter.hasHeader()){
//                    mRecyclerView.smoothScrollToPosition(myAdapter.getItemCount()+1);
//                }else{
//                    mRecyclerView.smoothScrollToPosition(myAdapter.getItemCount());
//                }
//            }
//
//            @Override
//            public void onLoadMore() {
//
//                getNetWorkData(id, true, new PictureTabPager.onLoadMoreCallInterface<PicturePagerBean.Gallery>() {
//                    @Override
//                    public void onSuccess(List<PicturePagerBean.Gallery> list) {
//                        onFinish(list);
//                    }
//
//                    @Override
//                    public void onError() {
//
//                    }
//                });
//            }
//
//            @Override
//            public void onFinish(List<PicturePagerBean.Gallery> list) {
//                myAdapter.getList().addAll(list);
//                myAdapter.notifyDataSetChanged();
//                setLoadingMore(false);
//            }
//        });
        mSwipeRefreshLayout.setColorSchemeColors(
                mActivity.getResources().getColor(R.color.gplus_color_1),
                mActivity.getResources().getColor(R.color.gplus_color_2),
                mActivity.getResources().getColor(R.color.gplus_color_3),
                mActivity.getResources().getColor(R.color.gplus_color_4));
        mSwipeRefreshLayout.setOnRefreshListener(this);

        initNetWorkData();
    }
    int curPage=1;
    public void  initNetWorkData(){
        curPage=1;
        toggleShowLoading(true,"loading");
        getNetWorkData(id,false,null);
    }

    @Override
    protected View getTagetView() {
        return mSwipeRefreshLayout;
    }

    @Override
    protected View initRootView() {
        View inflate = mActivity.getLayoutInflater().inflate(R.layout.picture_detial_layout, null);
        toolbar = (Toolbar) inflate.findViewById(R.id.common_toolbar);
        mSwipeRefreshLayout = (XSwipeRefreshLayout) inflate.findViewById(R.id.fragment_images_list_swipe_layout);
        mRecyclerView = (RecyclerView) inflate.findViewById(R.id.recycle);
        String title=getIntent().getStringExtra("title");
        toolbar.setTitle(title);
        return inflate;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.BOTTOM;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return true;
    }

    public  void  getNetWorkData( String id, final boolean isLoadMore, final PictureTabPager.onLoadMoreCallInterface listerner){
        LogUtil.e("==>getNetWorkData");
        String  uri=CommonConifg.pictures_pager_show;
        BaseProtocal protocal=new BaseProtocal();

        uri=uri+"?id="+id;
        Log.e("tag",uri);
        protocal.getNetData(uri, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //解析数据
                Prase(result,isLoadMore,listerner);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if(listerner!=null){
                    listerner.onError();
                }

                if(!isLoadMore) {
                    toggleShowError(true,"" + ex.getMessage(), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            initNetWorkData();
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
    private  void  Prase(String result,boolean isloadmore,final PictureTabPager.onLoadMoreCallInterface listerner){
        LogUtil.e("==>Prase");
        if(!isloadmore) {
            //刷新
            if (!CommonUtils.isEmpty(result)) {
                PictureItemBean bean = (PictureItemBean) toBean(result, PictureItemBean.class);
                if (bean != null && bean.getList()!=null && bean.getList().size()>0) {
                    showSuccessView(bean.getList(),isloadmore,listerner);
                } else {
                    toggleShowError(true,"数据请求失败:" , new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            initNetWorkData();
                        }
                    });
                }

            } else {
                Toast.makeText(mActivity, "数据请求失败，请检查网络是否连接正常！", Toast.LENGTH_SHORT).show();
                toggleShowError(true,"数据请求失败", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        initNetWorkData();
                    }
                });
            }
        }else{
            //加载更多
            if (!CommonUtils.isEmpty(result)) {
                PictureItemBean bean = (PictureItemBean) toBean(result, PictureItemBean.class);
                if (bean != null && bean.getList()!=null && bean.getList().size()>0) {
                    showSuccessView(bean.getList(),isloadmore,listerner);
                } else {
                    Toast.makeText(mActivity, "加载更多数据失败！", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(mActivity, "加载更多数据失败！", Toast.LENGTH_SHORT).show();

            }
        }
    }
    private  void  showSuccessView(List<PictureItemBean.Picture> list, boolean isloadmore, final PictureTabPager.onLoadMoreCallInterface listerner){
        LogUtil.e("==>showSuccessView");
        LogUtil.e("list==>"+list);
        if(list!=null && list.size()>0) {
            if(!isloadmore) {
                if (null != myAdapter) {
                    LogUtil.e("=====>update");
//                    mList=(ArrayList<PicturePagerBean.Gallery>) list;
////                    myAdapter.notifyDataSetChanged();
//                    myAdapter.notifyDataSetChanged();
                    myAdapter.getList().clear();
                    myAdapter.getList().addAll(list);
                    myAdapter.notifyDataSetChanged();
//                    myAdapter.setFooterView(0);
                    mSwipeRefreshLayout.setRefreshing(false);
                }

            }else{

                if(myAdapter!=null){
//                    myAdapter.getList().addAll(list);
//                    myAdapter.notifyDataSetChanged();
                    if(listerner!=null){
                        listerner.onSuccess(list);
                    }
                    Toast.makeText(mActivity, "加载成功！", Toast.LENGTH_SHORT).show();
                }

            }
            toggleShowLoading(false,"");
            curPage++;

        }else{
            if(!isloadmore){
                toggleShowEmpty(true,"",null);
            }else{
                Toast.makeText(mActivity, "加载数据为空！", Toast.LENGTH_SHORT).show();
            }
        }

    }


    private    String  error="";
    public Object toBean(String  json, Class clazz){
        try {
            JSONObject obj=new JSONObject(json);
            if(obj.getBoolean("status")){
                Gson gson=new Gson();
                Object result = gson.fromJson(json, clazz);
                return  result;
            }else{
//                error=obj.getString(return_msg);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            error=e.getMessage();
        }
        return  null;
    }

    @Override
    public void navigateToActivity(String s) {
        Log.e("tag","====>"+s);
        String url=CommonConifg.show_picture_url+s;
        Intent intent=new Intent(mActivity,PictureActivity.class);
        intent.putExtra("url",url);
        intent.putExtra("title","图片详情");
        mActivity.startActivity(intent);

    }

    @Override
    public void onRefresh() {
        initNetWorkData();
    }
}
