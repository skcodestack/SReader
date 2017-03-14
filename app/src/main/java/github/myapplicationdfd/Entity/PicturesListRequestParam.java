package github.myapplicationdfd.Entity;

import android.text.TextUtils;

import github.myapplicationdfd.utils.CommonUtils;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/3/8.
 */

public class PicturesListRequestParam extends BaseBean {
    /*page	否	int	请求页数，默认page=1
    rows	否	int	每页返回的条数，默认rows=20
    id	否	int	分类ID，默认返回的是全部。这里的ID就是指分类的ID*/

    private String page;
    private String rows;
    private String id;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParams(){
        StringBuilder sb=new StringBuilder();
        sb.append("id="+getId());
        if(!CommonUtils.isEmpty(getPage())){
            sb.append("&page="+getPage());
        }
        if(!CommonUtils.isEmpty(getRows())){
            sb.append("&rows="+getRows());
        }
        return sb.toString();
    }

}
