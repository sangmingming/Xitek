package me.isming.xitek.bbs.glide;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by sam on 17/3/7.
 */
public class DownloadImageService implements Runnable {

    private String mUrl;
    private Context mContext;
    private ImageDownloadCallback callBack;
    private File currentFile;

    public static void downloadImage(final Context context, String url, final ImageDownloadCallback callBack) {
        final Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        if (callBack != null) {
                            String path = (String) msg.obj;
                            callBack.onDownLoadSuccess(path);
                        }
                        break;
                    case 2:
                        if (callBack != null) {
                            callBack.onDownLoadFailed();
                        }
                        break;
                }
            }
        };
        DownloadImageService downloadImageService = new DownloadImageService(context, url, new ImageDownloadCallback() {
            @Override
            public void onDownLoadSuccess(String path) {
                Message msg = Message.obtain();
                msg.what = 1;
                msg.obj = path;
                handler.sendMessage(msg);
            }

            @Override
            public void onDownLoadFailed() {
                handler.sendEmptyMessage(2);
            }
        });
        new Thread(downloadImageService).start();
    }

    public DownloadImageService(Context context, String url, ImageDownloadCallback callBack) {
        this.mUrl = url;
        this.mContext = context;
        this.callBack = callBack;
    }

    @Override
    public void run() {
        Bitmap bitmap = null;
        String path = null;
        try {
            bitmap = Glide.with(mContext)
                    .load(mUrl)
                    .asBitmap()
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
            if (bitmap != null){
                // 在这里执行图片保存方法
                path = saveImageToGallery(mContext,bitmap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bitmap != null && currentFile.exists()) {
                callBack.onDownLoadSuccess(path);
            } else {
                callBack.onDownLoadFailed();
            }
        }
    }


    public String saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();//注意小米手机必须这样获得public绝对路径
        String fileName = "Xitek";
        File appDir = new File(file ,fileName);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        fileName = System.currentTimeMillis() + ".jpg";
        currentFile = new File(appDir, fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(currentFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(new File(currentFile.getPath()))));
        return currentFile.getPath();
    }
}
