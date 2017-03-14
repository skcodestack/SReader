package github.myapplicationdfd.Helper;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import github.myapplicationdfd.utils.CommonUtils;
import github.myapplicationdfd.Helper.Impl.ViewHelper;
import github.myapplicationdfd.R;

/**
 * Author root
 * Date: 2016/8/26.
 */
public class ViewHelperControl  {

    private  IViewHelper helper;
    public ViewHelperControl(View view) {
        this(new ViewHelper(view));
    }
    public ViewHelperControl(IViewHelper viewHelper) {
        this.helper=viewHelper;
    }

    public  void  showNetWorkError(View.OnClickListener onClickListener){

    View layout = helper.inflate(R.layout.message);
    TextView textView = (TextView) layout.findViewById(R.id.message_info);
    textView.setText(helper.getContext().getResources().getString(R.string.common_no_network_msg));

    ImageView imageView = (ImageView) layout.findViewById(R.id.message_icon);
    imageView.setImageResource(R.mipmap.ic_exception);

    if (null != onClickListener) {
        layout.setOnClickListener(onClickListener);
    }

    helper.showLayout(layout);
    }

    public void showError(String errorMsg, View.OnClickListener onClickListener) {
        View layout = helper.inflate(R.layout.message);
        TextView textView = (TextView) layout.findViewById(R.id.message_info);
        if (!CommonUtils.isEmpty(errorMsg)) {
            textView.setText(errorMsg);
        } else {
            textView.setText(helper.getContext().getResources().getString(R.string.common_error_msg));
        }

        ImageView imageView = (ImageView) layout.findViewById(R.id.message_icon);
        imageView.setImageResource(R.mipmap.ic_error);

        if (null != onClickListener) {
            layout.setOnClickListener(onClickListener);
        }

        helper.showLayout(layout);
    }

    public void showEmpty(String emptyMsg, View.OnClickListener onClickListener) {
        View layout = helper.inflate(R.layout.message);
        TextView textView = (TextView) layout.findViewById(R.id.message_info);
        if (!CommonUtils.isEmpty(emptyMsg)) {
            textView.setText(emptyMsg);
        } else {
            textView.setText(helper.getContext().getResources().getString(R.string.common_empty_msg));
        }

        ImageView imageView = (ImageView) layout.findViewById(R.id.message_icon);
        imageView.setImageResource(R.mipmap.ic_exception);

        if (null != onClickListener) {
            layout.setOnClickListener(onClickListener);
        }

        helper.showLayout(layout);
    }

    public void showLoading(String msg) {
        View layout = helper.inflate(R.layout.loading);
        if (!CommonUtils.isEmpty(msg)) {
            TextView textView = (TextView) layout.findViewById(R.id.loading_msg);
            textView.setText(msg);
        }
        helper.showLayout(layout);
    }

    public void restore() {
        helper.restoreView();
    }

}
