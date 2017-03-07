package me.isming.xitek.bbs.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import me.isming.xitek.bbs.R;
import me.isming.xitek.bbs.glide.DownloadImageService;
import me.isming.xitek.bbs.glide.ImageDownloadCallback;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PhotoViewActivity extends AppCompatActivity {

    public static final String EXTRA_SRC = "src";

    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private PhotoViewAttacher mAttacher;
    private String mImageSrc;

    public static void show(Context context, String url) {
        Intent intent = new Intent(context, PhotoViewActivity.class);
        intent.putExtra(EXTRA_SRC, url);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.save_image:
                if (checkPermission()) {
                    downloadImage();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private boolean checkPermission() {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 120);
            return false;
        }
    }


    private void downloadImage() {
        mProgressBar.setVisibility(View.VISIBLE);
        DownloadImageService.downloadImage(getApplicationContext(), mImageSrc, new ImageDownloadCallback() {
            @Override
            public void onDownLoadSuccess(String path) {
                mProgressBar.setVisibility(View.GONE);
                Snackbar.make(mImageView, "成功下载到:" + path, Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onDownLoadFailed() {
                mProgressBar.setVisibility(View.GONE);
                Snackbar.make(mImageView, "下载失败", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 120) {
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    downloadImage();
                }

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.photo_view, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        mImageSrc = getIntent().getStringExtra(EXTRA_SRC);
        mImageView = (ImageView) findViewById(R.id.image);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mProgressBar.setVisibility(View.VISIBLE);
        mAttacher = new PhotoViewAttacher(mImageView);
        Glide.with(this).load(mImageSrc).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                mProgressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                mProgressBar.setVisibility(View.GONE);
                mAttacher.update();
                return false;
            }
        }).into(mImageView);




    }

}
