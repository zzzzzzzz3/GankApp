package com.example.msi.myapp.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文 件 名: SaveImage
 * 创 建 人: ZhangRonghua
 * 创建日期: 2016/7/7 10:13
 * 邮   箱: qq798435167@gmail.com
 * 博   客: http://zzzzzzzz3.github.io
 * 修改时间：
 * 修改备注：
 */
public class SaveImage {
    private static SaveImage INSTANCE = new SaveImage();
    private static final String IMAGEDIR = "imageload";
    private SaveImage(){
    }
    public static SaveImage getINSTANCE(){
        return INSTANCE;
    }
    public void saveImage(String name, Bitmap bitmap){
        String imageName = name+".jpg";
        File loadDir = new File(getSdPath()+IMAGEDIR);
        if (!loadDir.exists()){
            loadDir.mkdirs();
        }
        File imageFile = new File(loadDir,imageName);
        if (!imageFile.exists()){
            try {
                imageFile.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public void saveImage(String name, File file, Context context){
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            //只能在getExternalFilesDir(null).getPath()这个路径下创建文件夹
            File imageDir = new File(context.getExternalFilesDir(null).getPath()+"/"+IMAGEDIR);
            if (!imageDir.exists()){
                imageDir.mkdirs();
            }
            File imageFile = new File(imageDir,name+".jpg");
            if (!imageFile.exists()){
                imageFile.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
                byte[] buf = new byte[1024];
                int len;
                while ((len = fileInputStream.read(buf))>0){
                    fileOutputStream.write(buf,0,len);
                }
                fileInputStream.close();
                fileOutputStream.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean cleanImage(Context context){
        File imageDir = new File(context.getExternalFilesDir(null).getPath()+"/"+IMAGEDIR);
        if (imageDir.exists()){
            File[] files = imageDir.listFiles();
            for (File child : files) {
                child.delete();
            }
        }
        return imageDir.list().length==0;
    }
    public String getSdPath(){
        String sdPath;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            sdPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
            return sdPath;
        }
        return null;
    }
}
