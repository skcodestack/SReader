package github.myapplicationdfd.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/3/13.
 */

public class ImageUtil {
    public  static  final String root_path= Environment.getExternalStorageDirectory().getAbsolutePath()
            + File.separatorChar+
            "myapp"+File.separatorChar;
    public static void saveBitmap(Context context,Bitmap bitmap,String filename){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File newdir = new File(root_path);
            if (!newdir.mkdirs()) {
            }
            SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
            final String fileName="image_"+format.format(new Date())+".png";
            //文件保存路径
            final String  uploadFilePath=root_path+fileName;
            Log.e("tag","===>"+uploadFilePath);
            FileOutputStream out=null;

            try {
                out=new FileOutputStream(uploadFilePath);
                bitmap.compress(Bitmap.CompressFormat.PNG,90,out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Toast.makeText(context,"保存成功！",Toast.LENGTH_SHORT).show();
            //提示刷新

        }else{
            Toast.makeText(context,"没有外设！",Toast.LENGTH_SHORT).show();
        }

    }
}
