package github.myapplicationdfd.Helper;

import android.content.Context;
import android.view.View;

/**
 * view管理  帮助接口
 */
public interface IViewHelper {
    //得到context
    public Context getContext();
    //显示
    public  void  showLayout(View view);
    //得到当前layout
    public  View  getCurrentView();
    //infalte
    public   View  inflate(int id);
    //得到当前view
    public View getView();
    //恢复view
    public  void  restoreView();
}
