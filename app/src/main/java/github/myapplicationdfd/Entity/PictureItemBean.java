package github.myapplicationdfd.Entity;

import java.util.List;

import github.myapplicationdfd.adapter.RecyclerViewAdapter;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/3/13.
 */

public class PictureItemBean extends BaseBean{


    private int id;
    private int  galleryclass ;//          图片分类
    private String title     ;//          标题
    private String img     ;//          图库封面
    private int count     ;//          访问数
    private int rcount     ;//           回复数
    private int fcount     ;//          收藏数
    private int size     ;//      图片多少张

    private List<Picture> list;

    public List<Picture> getList() {
        return list;
    }

    public void setList(List<Picture> list) {
        this.list = list;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGalleryclass() {
        return galleryclass;
    }

    public void setGalleryclass(int galleryclass) {
        this.galleryclass = galleryclass;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRcount() {
        return rcount;
    }

    public void setRcount(int rcount) {
        this.rcount = rcount;
    }

    public int getFcount() {
        return fcount;
    }

    public void setFcount(int fcount) {
        this.fcount = fcount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public static class  Picture implements RecyclerViewAdapter.Item{
        private int id;
        private int gallery; //图片库
        private String src; //图片地址
        private int Type=2;


        @Override
        public int getType() {
            return Type;
        }
        public int getGallery() {
            return gallery;
        }

        public void setGallery(int gallery) {
            this.gallery = gallery;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
