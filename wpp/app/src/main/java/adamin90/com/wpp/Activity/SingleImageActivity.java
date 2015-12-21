package adamin90.com.wpp.Activity;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.iflytek.voiceads.AdError;
import com.iflytek.voiceads.AdKeys;
import com.iflytek.voiceads.IFLYAdListener;
import com.iflytek.voiceads.IFLYAdSize;
import com.iflytek.voiceads.IFLYBannerAd;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import adamin90.com.wpp.R;
import adamin90.com.wpp.view.FloatingActionMenu;
import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoViewAttacher;

public class SingleImageActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.menu)
    FloatingActionMenu floatingActionMenu;
    @Bind(R.id.fab)
    FloatingActionButton floatingActionButton;
    @Bind(R.id.image)
    ImageView imageView;
    @Bind(R.id.appbar)
    AppBarLayout appBarLayout;
    @Bind(R.id.layout_adview)
    LinearLayout linearLayout;
    private IFLYBannerAd bannerView;

    private String url;

    private boolean ishide = false;

    private PhotoViewAttacher attacher;

    private OkHttpClient okHttpClient = new OkHttpClient();
    private Request request;
    private String files = Environment.getExternalStorageDirectory().toString() + "/adamin90/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_image);
        ButterKnife.bind(this);
        init();
        createBannerAd();


    }

    private void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail");
        floatingActionMenu.setmItemGap(50);
        url = getIntent().getStringExtra("image");
        Picasso.with(this).load(url)
                .noPlaceholder().into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                attacher = new PhotoViewAttacher(imageView);
                attacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                    @Override
                    public void onPhotoTap(View view, float x, float y) {
                        hideOrShowToolbar();

                    }
                });

            }

            @Override
            public void onError() {

            }
        });


        floatingActionMenu.setOnMenuItemClickListener(new FloatingActionMenu.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(FloatingActionMenu fam, int index, FloatingActionButton item) {
                switch (item.getId()) {
                    case R.id.fab2:
                        request = new Request.Builder()
                                .url(url)
                                .build();
                        okHttpClient.newCall(request)
                                .enqueue(new com.squareup.okhttp.Callback() {
                                    @Override
                                    public void onFailure(Request request, IOException e) {
                                        Snackbar.make(floatingActionButton, "下载失败" + e.toString(), Snackbar.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onResponse(Response response) throws IOException {

                                        InputStream inputStream = response.body().byteStream();
                                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                        WallpaperManager wallpaperManager = WallpaperManager.getInstance(SingleImageActivity.this);
                                        wallpaperManager.setBitmap(bitmap);
                                        Snackbar.make(floatingActionButton, "成功设置壁纸", Snackbar.LENGTH_SHORT).show();
                                    }
                                });


                        break;
                    case R.id.fab3:
                        request = new Request.Builder()
                                .url(url)
                                .build();
                        okHttpClient.newCall(request)
                                .enqueue(new com.squareup.okhttp.Callback() {
                                    @Override
                                    public void onFailure(Request request, IOException e) {
                                        Snackbar.make(floatingActionButton, "下载失败" + e.toString(), Snackbar.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onResponse(Response response) throws IOException {

                                        InputStream inputStream = response.body().byteStream();
                                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                        File file = new File(files + String.format("%d.jpeg", System.currentTimeMillis()));
                                        FileOutputStream outputStream = new FileOutputStream(file);
                                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                                        byte[] bytes = byteArrayOutputStream.toByteArray();
                                        outputStream.write(bytes);
                                        outputStream.close();
                                        inputStream.close();
                                        Snackbar.make(floatingActionButton, "成功下载到sdcard/storage/emulated/0/adamin90目录下", Snackbar.LENGTH_SHORT).show();
                                    }
                                });


                        break;
                    case R.id.fab4:
                        request = new Request.Builder()
                                .url(url)
                                .build();
                        okHttpClient.newCall(request)
                                .enqueue(new com.squareup.okhttp.Callback() {
                                    @Override
                                    public void onFailure(Request request, IOException e) {
                                        Snackbar.make(floatingActionButton, "下载失败" + e.toString(), Snackbar.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onResponse(Response response) throws IOException {

                                        InputStream inputStream = response.body().byteStream();
                                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                        File file = new File(files + String.format("%d.jpeg", System.currentTimeMillis()));
                                        FileOutputStream outputStream = new FileOutputStream(file);
                                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                                        byte[] bytes = byteArrayOutputStream.toByteArray();
                                        outputStream.write(bytes);
                                        outputStream.close();
                                        inputStream.close();
                                        Intent intent = new Intent(Intent.ACTION_SEND);
                                        intent.setType("image/jpeg");
                                        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(file.getPath()));
                                        startActivity(Intent.createChooser(intent, "分享"));
                                    }
                                });


                        break;
                }
            }
        });

    }

    private void hideOrShowToolbar() {
        appBarLayout.animate()
                .translationY(ishide ? 0 : -appBarLayout.getHeight())
                .setInterpolator(new DecelerateInterpolator())
                .start();
        floatingActionButton.animate()
                .scaleX(ishide ? 1.0F : 0.0F)
                .scaleY(ishide ? 1.0F : 0.0F)
                .alpha(ishide ? 0.8F : 0.0F)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(500)
                .start();
        linearLayout.animate()
                .translationY(ishide?0:-(appBarLayout.getHeight()+linearLayout.getHeight()))
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(1000)
                .start();
        ishide = !ishide;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void createBannerAd() {
        //此广告位为Demo专用，广告的展示不产生费用
        String adUnitId = "9E716F2A8F3D47416CF1C863CB532707";
        //创建旗帜广告，传入广告位ID
        bannerView = IFLYBannerAd.createBannerAd(this, adUnitId);
        //设置请求的广告尺寸
        bannerView.setAdSize(IFLYAdSize.BANNER);
        //设置下载广告前，弹窗提示
        bannerView.setParameter(AdKeys.DOWNLOAD_ALERT, "true");

        //请求广告，添加监听器
        bannerView.loadAd(mAdListener);
        //将广告添加到布局
        linearLayout = (LinearLayout)findViewById(R.id.layout_adview);
        linearLayout.removeAllViews();
        linearLayout.addView(bannerView);

    }

    IFLYAdListener mAdListener = new IFLYAdListener(){

        /**
         * 广告请求成功
         */
        @Override
        public void onAdReceive() {
            //展示广告
            bannerView.showAd();


        }

        /**
         * 广告请求失败
         */
        @Override
        public void onAdFailed(AdError error) {

        }

        /**
         * 广告被点击
         */
        @Override
        public void onAdClick() {
        }

        /**
         * 广告被关闭
         */
        @Override
        public void onAdClose() {
        }

        @Override
        public void onAdExposure() {
            // TODO Auto-generated method stub

        }
    };
}
