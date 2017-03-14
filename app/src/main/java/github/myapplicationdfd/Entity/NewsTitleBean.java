package github.myapplicationdfd.Entity;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/3/8.
 */

public class NewsTitleBean {
    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "NewsTitleBean{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
