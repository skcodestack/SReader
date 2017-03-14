package github.myapplicationdfd.Entity;

import java.util.List;

import github.myapplicationdfd.adapter.RecyclerViewAdapter;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/3/8.
 */

public class PicturePagerBean {
    /*"status":true,
"total":5,
"tngou"*/

    private Boolean status;
    private String total;
    private List<Gallery> tngou;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Gallery> getTngou() {
        return tngou;
    }

    public void setTngou(List<Gallery> tngou) {
        this.tngou = tngou;
    }

    public static class  Gallery implements RecyclerViewAdapter.Item{

        private int Type=2;

        private int id;
        private int  galleryclass ;//          图片分类
        private String title     ;//          标题
        private String img     ;//          图库封面
        private int count     ;//          访问数
        private int rcount     ;//           回复数
        private int fcount     ;//          收藏数
        private int size     ;//      图片多少张

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


        @Override
        public int getType() {
            return Type;
        }

        @Override
        public String toString() {
            return "Gallery{" +
                    "Type=" + Type +
                    ", id=" + id +
                    ", galleryclass=" + galleryclass +
                    ", title='" + title + '\'' +
                    ", img='" + img + '\'' +
                    ", count=" + count +
                    ", rcount=" + rcount +
                    ", fcount=" + fcount +
                    ", size=" + size +
                    '}';
        }
    }
}
