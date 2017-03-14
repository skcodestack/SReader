package github.myapplicationdfd.pager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import github.myapplicationdfd.CommonConifg;
import github.myapplicationdfd.Entity.NewsPagerBean;
import github.myapplicationdfd.Entity.NewsRequestParam;
import github.myapplicationdfd.Entity.PicturePagerBean;
import github.myapplicationdfd.Entity.PicturesListRequestParam;
import github.myapplicationdfd.NewsDetailActivity;
import github.myapplicationdfd.PicturesDetailActivity;
import github.myapplicationdfd.R;
import github.myapplicationdfd.adapter.ListViewDataAdapter;
import github.myapplicationdfd.adapter.MyRecycleAdapter;
import github.myapplicationdfd.adapter.ViewHolderBase;
import github.myapplicationdfd.adapter.ViewHolderCreator;
import github.myapplicationdfd.listener.NavigateNewPageInterface;
import github.myapplicationdfd.listener.OnRecyclerViewScrollListener;
import github.myapplicationdfd.pla.PLAAdapterView;
import github.myapplicationdfd.pla.PLAImageView;
import github.myapplicationdfd.protocal.BaseProtocal;
import github.myapplicationdfd.utils.CommonUtils;
import github.myapplicationdfd.utils.Constants;
import github.myapplicationdfd.utils.Json2bean;
import github.myapplicationdfd.view.PLALoadMoreListView;
import github.myapplicationdfd.view.RefreshListView;
import github.myapplicationdfd.view.TopNewsViewPager;
import github.myapplicationdfd.view.XSwipeRefreshLayout;

/**
 * Author root
 * Date: 2016/8/30.
 */
public class PictureTabPager extends BasePager implements PLAAdapterView.OnItemClickListener ,PLALoadMoreListView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener,NavigateNewPageInterface<String> {




    public  interface  onLoadMoreCallInterface<T>{
        void onSuccess(List<T> list);
        void onError();
        void onRefresh();
    }

    private  String  code="1";

    private ArrayList<PicturePagerBean.Gallery> mList=new ArrayList<>();
    private XSwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private MyRecycleAdapter myAdapter;

    public PictureTabPager(Activity mActivity, String code) {
        super(mActivity,code);
        this.code=code;
        LogUtil.e("code==>"+code);
    }

    @Override
    public View initView() {
        View view = mActivity.getLayoutInflater().inflate(R.layout.picture_pager_layout, null);

        mSwipeRefreshLayout = (XSwipeRefreshLayout) view.findViewById(R.id.fragment_images_list_swipe_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle);

        initEvent();
        return view;
    }

    @Override
    protected void onCreatedView() {
        showLoading("loading...");
    }

    int  curPage=1;
    private void initEvent() {
        mList=new ArrayList<>();
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        myAdapter = new MyRecycleAdapter(mList,this);

        mRecyclerView.setAdapter(myAdapter);
        mRecyclerView.addOnScrollListener(new OnRecyclerViewScrollListener<PicturePagerBean.Gallery>(){

            @Override
            public void onStart() {
                myAdapter.setFooterView(R.layout.item_footer);

                if (myAdapter.hasHeader()){
                    mRecyclerView.smoothScrollToPosition(myAdapter.getItemCount()+1);
                }else{
                    mRecyclerView.smoothScrollToPosition(myAdapter.getItemCount());
                }
            }

            @Override
            public void onLoadMore() {
                PicturesListRequestParam requestParam=new PicturesListRequestParam();
                requestParam.setId(code);
                requestParam.setPage(curPage+"");
                requestParam.setRows(Constants.pageSize);
                getNetWorkData(requestParam, true, new onLoadMoreCallInterface<PicturePagerBean.Gallery>() {
                    @Override
                    public void onSuccess(List<PicturePagerBean.Gallery> list) {
                        onFinish(list);
                    }

                    @Override
                    public void onError() {

                    }

                    @Override
                    public void onRefresh() {

                    }
                });
            }

            @Override
            public void onFinish(List<PicturePagerBean.Gallery> list) {
                myAdapter.getList().addAll(list);
                myAdapter.notifyDataSetChanged();
                setLoadingMore(false);
            }
        });
        mSwipeRefreshLayout.setColorSchemeColors(
                mActivity.getResources().getColor(R.color.gplus_color_1),
                mActivity.getResources().getColor(R.color.gplus_color_2),
                mActivity.getResources().getColor(R.color.gplus_color_3),
                mActivity.getResources().getColor(R.color.gplus_color_4));
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }



    @Override
    public void initData() {
        curPage=1;
        LogUtil.e("==>initData");
        showLoading("loading...");
        PicturesListRequestParam requestParam=new PicturesListRequestParam();
        LogUtil.e("objs===>"+getParams());
        requestParam.setId(code);
        requestParam.setPage("1");
        requestParam.setRows(Constants.pageSize);
        getNetWorkData(requestParam,false,null);
    }

    @Override
    public View getTagerView() {
        return mSwipeRefreshLayout;
    }

    @Override
    public void onItemClick(PLAAdapterView<?> parent, View view, int position, long id) {

    }
    @Override
    public void navigateToActivity(String  str) {
        Intent intent=new Intent(mActivity, PicturesDetailActivity.class);
        intent.putExtra("id",str);
        intent.putExtra("title","图片详情");
        mActivity.startActivity(intent);
    }
    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefresh() {
        curPage=1;
        PicturesListRequestParam requestParam=new PicturesListRequestParam();
        LogUtil.e("objs===>"+getParams());
        requestParam.setId(code);
        requestParam.setPage("1");
        requestParam.setRows(Constants.pageSize);
        getNetWorkData(requestParam, false, new onLoadMoreCallInterface<PicturePagerBean.Gallery>() {
            @Override
            public void onSuccess(List list) {

            }

            @Override
            public void onError() {

            }

            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public  void  getNetWorkData(PicturesListRequestParam params, final boolean isLoadMore, final onLoadMoreCallInterface listerner){
        LogUtil.e("==>getNetWorkData");
        String  uri=CommonConifg.pictures_pager_list;
        BaseProtocal protocal=new BaseProtocal();

        uri=uri+"?"+params.getParams();
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
    private  void  Prase(String result,boolean isloadmore,final onLoadMoreCallInterface listerner){
        LogUtil.e("==>Prase");
        if(!isloadmore) {
            //刷新
            if (!CommonUtils.isEmpty(result)) {
                PicturePagerBean bean = (PicturePagerBean) toBean(result, PicturePagerBean.class);
                if (bean != null && bean.getTngou()!=null && bean.getTngou().size()>0) {
                    showSuccessView(bean.getTngou(),isloadmore,listerner);
                } else {
                    showError("数据请求失败:" , new View.OnClickListener() {
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
                PicturePagerBean bean = (PicturePagerBean) toBean(result, PicturePagerBean.class);
                if (bean != null && bean.getTngou()!=null && bean.getTngou().size()>0) {
                    showSuccessView(bean.getTngou(),isloadmore,listerner);
                } else {
                    Toast.makeText(mActivity, "加载更多数据失败！", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(mActivity, "加载更多数据失败！", Toast.LENGTH_SHORT).show();

            }
        }
    }
    private  void  showSuccessView(List<PicturePagerBean.Gallery> list, boolean isloadmore,final onLoadMoreCallInterface listerner){
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
                    if(listerner!=null){
                        listerner.onRefresh();
                    }
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
            showSuccessView();
            curPage++;

        }else{
            if(!isloadmore){
                showEmpty(null);
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

}
