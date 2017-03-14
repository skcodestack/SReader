package github.myapplicationdfd.adapter;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.xutils.common.util.LogUtil;
import org.xutils.x;

import java.util.List;

import github.myapplicationdfd.CommonConifg;
import github.myapplicationdfd.Entity.PicturePagerBean;
import github.myapplicationdfd.R;
import github.myapplicationdfd.listener.NavigateNewPageInterface;
import github.myapplicationdfd.pla.PLAImageView;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/3/13.
 */

public class MyRecycleAdapter extends StaggeredGridLayoutAdapter<PicturePagerBean.Gallery> {
    NavigateNewPageInterface listener=null;
    public MyRecycleAdapter(List<PicturePagerBean.Gallery> list, NavigateNewPageInterface listener) {
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
    protected void onBindItemView(RecyclerView.ViewHolder holder, final PicturePagerBean.Gallery item) {
        final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        String url = CommonConifg.show_picture_url + item.getImg()+"_180x220";
        LogUtil.e("image==>"+url);
//        x.image().bind(itemViewHolder.icon, url);
//        itemViewHolder.icon.setBackgroundResource(R.color.black);

        ImageLoader.getInstance().displayImage(url, itemViewHolder.icon);
        itemViewHolder.icon.setImageHeight(220);
        itemViewHolder.icon.setImageWidth(180);
//        ImageLoader.getInstance().loadImage(url, new ImageLoadingListener() {
//            @Override
//            public void onLoadingStarted(String imageUri, View view) {
//                        itemViewHolder.icon.setImageHeight(220);
//                        itemViewHolder.icon.setImageWidth(180);
//            }
//
//            @Override
//            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//
//            }
//
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                int width = loadedImage.getWidth();
//                int height = loadedImage.getHeight();
//                if(height>width){
//                    itemViewHolder.icon.setImageHeight(220);
//                    itemViewHolder.icon.setImageWidth(180);
//                }else{
//                    itemViewHolder.icon.setImageHeight(180);
//                    itemViewHolder.icon.setImageWidth(220);
//                }
//                itemViewHolder.icon.setImageBitmap(loadedImage);
//            }
//
//            @Override
//            public void onLoadingCancelled(String imageUri, View view) {
//                itemViewHolder.icon.setImageHeight(220);
//                itemViewHolder.icon.setImageWidth(180);
//            }
//        });

        itemViewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    String id= item.getId()+"";
                    listener.navigateToActivity(id);
                }
            }
        });
        itemViewHolder.remark.setText(item.getTitle());
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        PLAImageView icon;
        TextView remark;
        View rootView;
        public ItemViewHolder(View itemView) {
            super(itemView);
            rootView=itemView;
            icon = (PLAImageView) itemView.findViewById(R.id.list_item_images_list_image);
            remark = (TextView) itemView.findViewById(R.id.remark);
        }
    }
}
