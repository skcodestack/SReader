package github.myapplicationdfd.Helper;

/**
 * Author root
 * Date: 2016/8/26.
 */
public interface IBaseView {
    /**
     * 显示网络错误
     */

    public  void  showNetError();

    /**
     * 显示加载界面
     * @param msg
     */
    public void showLoading(String msg);

    /**
     * 显示错误信息
     * @param msg
     */
    public  void   showError(String msg);

    /**
     * 隐藏加载界面
     */
    public  void   hideLoading();

    /**
     * 显示异常信息
     * @param ex
     */
    public  void  showException(String  ex);
}
