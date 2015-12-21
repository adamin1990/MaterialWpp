package adamin90.com.wpp.Activity;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.BitmapCompat;
import android.support.v4.os.EnvironmentCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.iflytek.voiceads.AdError;
import com.iflytek.voiceads.AdKeys;
import com.iflytek.voiceads.IFLYAdListener;
import com.iflytek.voiceads.IFLYAdSize;
import com.iflytek.voiceads.IFLYBannerAd;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import adamin90.com.wpp.Constant;
import adamin90.com.wpp.R;
import adamin90.com.wpp.adapter.DetailPagerAdapter;
import adamin90.com.wpp.model.detail.DetailData;
import adamin90.com.wpp.service.FetchDetailService;
import adamin90.com.wpp.view.FloatingActionMenu;
import adamin90.com.wpp.view.TouchImageView;
import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class DetailActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.appbar)
    AppBarLayout appBarLayout;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.floatingmenu)
    FloatingActionMenu floatingActionMenu;
    @Bind(R.id.fab)
    FloatingActionButton floatingActionButton;
    @BindString(R.string.app_name) String appname;

    @Bind(R.id.layout_adview)
    LinearLayout linearLayout;
    private IFLYBannerAd bannerView;


    private DetailData detailDatas;
    private List<adamin90.com.wpp.model.detail.List> lists;
    private int id;
    private int limit = 10;
    private int start;
    private String order = "-1";
    private int sourcetype = 1;
    private int update = 0;
    private int pagenow = 1;
    private DetailPagerAdapter detailPagerAdapter;
    private boolean ishide = false;
    private String title;
    private boolean hasnextpage=false;
    private  boolean isfirst=true;



    private Retrofit retrofit;
    private FetchDetailService fetchDetailService;
    private OkHttpClient okHttpClient=new OkHttpClient();
    private Request request;
    private String  files=Environment.getExternalStorageDirectory().toString()+"/adamin90/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getParams();
        init();
        getData();
        setLisener();
        File file=new File(files);
        if(!file.exists()){
            file.mkdir();
        }

        createBannerAd();


    }

    private void setLisener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
              if(position==lists.size()-1){
                  if(hasnextpage){
                      start=lists.size()+1;
                      getData();
                  }
              }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        floatingActionMenu.setOnMenuItemClickListener(new FloatingActionMenu.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(FloatingActionMenu fam, final int index, FloatingActionButton item) {
                switch (item.getId()){
                    case R.id.fab2:
                        request=new Request.Builder()
                                .url(lists.get(viewPager.getCurrentItem()).getOrginUrl())
                                .build();
                        okHttpClient.newCall(request)
                                .enqueue(new Callback() {
                                    @Override
                                    public void onFailure(Request request, IOException e) {
                                        Snackbar.make(floatingActionButton,"下载失败"+e.toString(),Snackbar.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onResponse(Response response) throws IOException {

                                        InputStream inputStream= response.body().byteStream();
                                        Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                                        WallpaperManager wallpaperManager=WallpaperManager.getInstance(DetailActivity.this);
                                        wallpaperManager.setBitmap(bitmap);
                                        Snackbar.make(floatingActionButton,"成功设置壁纸",Snackbar.LENGTH_SHORT).show();
                                    }
                                });


                        break;
                    case R.id.fab3:
                        request=new Request.Builder()
                                .url(lists.get(viewPager.getCurrentItem()).getOrginUrl())
                                .build();
                       okHttpClient.newCall(request)
                                .enqueue(new Callback() {
                                    @Override
                                    public void onFailure(Request request, IOException e) {
                                        Snackbar.make(floatingActionButton,"下载失败"+e.toString(),Snackbar.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onResponse(Response response) throws IOException {

                                    InputStream inputStream= response.body().byteStream();
                                        Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                                        File file=new File(files+String.format("%d.jpeg",System.currentTimeMillis()));
                                        FileOutputStream outputStream=new FileOutputStream(file);
                                        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                                        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                                        byte[] bytes=byteArrayOutputStream.toByteArray();
                                        outputStream.write(bytes);
                                        outputStream.close();
                                        inputStream.close();
                                        Snackbar.make(floatingActionButton,"成功下载到sdcard/storage/emulated/0/adamin90目录下",Snackbar.LENGTH_SHORT).show();
                                    }
                                });


                        break;
                    case R.id.fab4:
                        request=new Request.Builder()
                                .url(lists.get(viewPager.getCurrentItem()).getOrginUrl())
                                .build();
                        okHttpClient.newCall(request)
                                .enqueue(new Callback() {
                                    @Override
                                    public void onFailure(Request request, IOException e) {
                                        Snackbar.make(floatingActionButton,"下载失败"+e.toString(),Snackbar.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onResponse(Response response) throws IOException {

                                        InputStream inputStream= response.body().byteStream();
                                        Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                                        File file=new File(files+String.format("%d.jpeg",System.currentTimeMillis()));
                                        FileOutputStream outputStream=new FileOutputStream(file);
                                        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                                        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                                        byte[] bytes=byteArrayOutputStream.toByteArray();
                                        outputStream.write(bytes);
                                        outputStream.close();
                                        inputStream.close();
                                        Intent intent=new Intent(Intent.ACTION_SEND);
                                        intent.setType("image/jpeg");
                                        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(file.getPath()));
                                        startActivity(Intent.createChooser(intent,"分享"));
                                    }
                                });


                        break;
                }
            }
        });
    }

    private void getParams() {
        id = getIntent().getIntExtra("id",2226);
        start = getIntent().getIntExtra("index", 0);
        Timber.e("index是" + start);
        sourcetype = getIntent().getIntExtra("sourcetype", 1);
        order = getIntent().getStringExtra("order");
        update = getIntent().getIntExtra("update", 0);
        title=getIntent().getStringExtra("title")==null?appname:getIntent().getStringExtra("title");
    }

    private void init() {
        lists = new ArrayList<>();
        detailDatas = new DetailData();
        detailPagerAdapter = new DetailPagerAdapter(lists, new DetailPagerAdapter.TabLisenter() {
            @Override
            public void Tab() {
                hideOrShowToolbar();
            }

            @Override
            public void LongTab() {
                Snackbar.make(viewPager,"点击下载",Snackbar.LENGTH_LONG).show();

            }
        });
        viewPager.setAdapter(detailPagerAdapter);
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASEURL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        fetchDetailService = retrofit.create(FetchDetailService.class);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);
        floatingActionMenu.setmItemGap(48);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        Timber.e("onpostcreate++++++");
    }

    private void getData() {
        fetchDetailService.fetchDetails(order, sourcetype, id, start, limit, update)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DetailData>() {
                    @Override
                    public void call(DetailData detailData) {
                        if(detailData!=null){
                            if(detailData.getData().getIsNextRight()==1){
                                hasnextpage=true;
                            }else {
                                hasnextpage=false;
                            }
                            lists.addAll(detailData.getData().getList());
                            detailPagerAdapter.notifyDataSetChanged();
                            if(isfirst){
                                viewPager.setCurrentItem(start);
                                isfirst=false;
                            }

                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Timber.e(throwable.toString() + "错误信息");
                    }
                }, new Action0() {
                    @Override
                    public void call() {

                    }
                });

    }

    private void hideOrShowToolbar() {
        appBarLayout.animate()
                .translationY(ishide ? 0 : -appBarLayout.getHeight())
                .setInterpolator(new DecelerateInterpolator())
                .start();
        floatingActionButton.animate()
                .scaleX(ishide?1.0F:0.0F)
                .scaleY(ishide?1.0F:0.0F)
                .alpha(ishide?0.8F:0.0F)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(500)
                .start();
        linearLayout.animate()
                .translationY(ishide?0:-(appBarLayout.getHeight()+linearLayout.getHeight()))
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(1000)
                .start();
        ishide = !ishide;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(floatingActionMenu.isOpened()){
            floatingActionMenu.close();
        }else {
            super.onBackPressed();
        }

    }

    public void createBannerAd() {
        //此广告位为Demo专用，广告的展示不产生费用
        String adUnitId = "35C9BF556D3FDB0757A2E567769D51B9";
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
