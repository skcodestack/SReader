package github.myapplicationdfd.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.xutils.common.util.LogUtil;

import java.util.List;

import github.myapplicationdfd.CommonConifg;
import github.myapplicationdfd.Entity.PictureItemBean;
import github.myapplicationdfd.Entity.PicturePagerBean;
import github.myapplicationdfd.R;
import github.myapplicationdfd.listener.NavigateNewPageInterface;
import github.myapplicationdfd.pla.PLAImageView;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/3/13.
 */

public class PictureRecycleAdapter extends StaggeredGridLayoutAdapter<PictureItemBean.Picture> {
    NavigateNewPageInterface listener=null;
    public PictureRecycleAdapter(List<PictureItemBean.Picture> list, NavigateNewPageInterface listener) {
        super(list);
        this.listener=listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_image, null);
        return new ItemViewHolder(convertView);
    }

    @Override
    protected void onBindHeaderView(View headerView) {
        Log.e("TAG","这是HeadView数据绑定的过程");
    }

    @Override
    protected void onBindFooterView(View footerView) {
        Log.e("TAG","这是FootView数据绑定的过程");
    }

    @Override
    protected void onBindItemView(RecyclerView.ViewHolder holder, final PictureItemBean.Picture item) {
        final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        String url = CommonConifg.show_picture_url + item.getSrc()+"_180x220";
        LogUtil.e("image==>"+url);
//        x.image().bind(itemViewHolder.icon, url);
//        itemViewHolder.icon.setBackgroundResource(R.color.black);

        ImageLoader.getInstance().displayImage(url, itemViewHolder.icon);
        itemViewHolder.icon.setImageHeight(220);
        itemViewHolder.icon.setImageWidth(180);

        itemViewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    String src= item.getSrc();
                    listener.navigateToActivity(src);
                }
            }
        });
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        PLAImageView icon;
       // TextView remark;
        View rootView;
        public ItemViewHolder(View itemView) {
            super(itemView);
            rootView=itemView;
            icon = (PLAImageView) itemView.findViewById(R.id.list_item_images_list_image);
            TextView remark = (TextView) itemView.findViewById(R.id.remark);
            remark.setVisibility(View.GONE);
        }
    }
}
