package github.myapplicationdfd;

import android.graphics.Bitmap;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.PopupWindow;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import github.myapplicationdfd.activity.base.BaseAppCompateActivity;
import github.myapplicationdfd.utils.CommonUtils;
import github.myapplicationdfd.utils.ImageUtil;
import github.myapplicationdfd.utils.PopupWindowUtil;
import uk.co.senab.photoview.PhotoView;

/**
 * Author root
 * Date: 2016/8/31.
 */
public class PictureActivity extends BaseAppCompateActivity implements View.OnClickListener {


    private Toolbar toolbar;
    private PhotoView photoView;
    private String url;
    private WindowManager.LayoutParams params;
    Bitmap  curBitmap=null;
    private PopupWindowUtil takePhotoPopWin;

    @Override
    protected void initEvents() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureActivity.this.finish();
            }
        });


        url =   getIntent().getStringExtra("url");
        if (CommonUtils.isEmpty(url)) {
            toggleShowEmpty(true, "no image  pager!", null);
        }

        ImageLoader.getInstance().loadImage(url, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                photoView.setBackgroundResource(R.color.default_image_background);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                photoView.setBackgroundResource(android.R.color.transparent);
                curBitmap=loadedImage;
                photoView.setImageBitmap(loadedImage);
                photoView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        pictureDeal();
                        return false;
                    }
                });
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });



    }

    private void pictureDeal(){
        takePhotoPopWin = new PopupWindowUtil(this, this);
//        设置Popupwindow显示位置（从底部弹出）
        takePhotoPopWin.showAtLocation(findViewById(R.id.ll_main), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
        params = getWindow().getAttributes();
        //当弹出Popupwindow时，背景变半透明
        params.alpha=0.7f;
        getWindow().setAttributes(params);
        //设置Popupwindow关闭监听，当Popupwindow关闭，背景恢复1f
        takePhotoPopWin.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params = getWindow().getAttributes();
                params.alpha=1f;
                getWindow().setAttributes(params);
            }
        });



    }

    @Override
    protected View getTagetView() {
        return photoView;
    }

    @Override
    protected View initRootView() {
        View inflate = mActivity.getLayoutInflater().inflate(R.layout.picture_layout, null);
        toolbar = (Toolbar) inflate.findViewById(R.id.common_toolbar);
        photoView = (PhotoView) inflate.findViewById(R.id.photoview);
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


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_save){
            //保存图片
//            curBitmap
            if(curBitmap!=null && !TextUtils.isEmpty(url)) {
                int start = url.lastIndexOf("/");
                int end = url.lastIndexOf(".");
                String name = url.substring(start, end);
                ImageUtil.saveBitmap(mActivity, curBitmap,name+".png");
            }
            if(takePhotoPopWin!=null){
                takePhotoPopWin.dismiss();
            }
        }
    }
}
