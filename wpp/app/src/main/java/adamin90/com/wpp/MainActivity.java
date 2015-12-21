package adamin90.com.wpp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

import java.util.ArrayList;
import java.util.List;

import adamin90.com.wpp.Activity.SearchActivity;
import adamin90.com.wpp.Activity.ZhuanTiActivity;
import adamin90.com.wpp.model.Album;
import adamin90.com.wpp.model.Datum;
import adamin90.com.wpp.service.FetchAlbum;
import adamin90.com.wpp.util.NetWorkUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMoreListener {

    private Retrofit retrofit;
    List<Datum> datas;
    private FetchAlbum fetchAlbum;

    private MainAdapter mainAdapter;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    @Bind(R.id.list)
    SuperRecyclerView mRecycler;
    private int start = 0;
    private int end = 20;
    private int page = 1;
    private int pagecount = 20;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        UmengUpdateAgent.update(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("专辑");
        retrofit = new Retrofit.Builder()
                .baseUrl("http://client.pic.hiapk.com/index.php/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        datas = new ArrayList<>();
        mainAdapter = new MainAdapter(datas);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecycler.setLayoutManager(staggeredGridLayoutManager);
        mRecycler.setAdapter(mainAdapter);
        mRecycler.setupMoreListener(this, 1);
        fetchAlbum = retrofit.create(FetchAlbum.class);
        if(NetWorkUtils.testNet(this)){
            fetchAlbum.getAlbum(start, end)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Album>() {
                        @Override
                        public void call(Album album) {
                            pagecount = Integer.valueOf(album.getTotalCount()) / end;
                            datas.addAll(album.getData());
                            mainAdapter.notifyDataSetChanged();

                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            makeSnake("抱歉，出错了："+throwable.toString());
                        }
                    });
        }


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                staggeredGridLayoutManager.scrollToPositionWithOffset(0, 2);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (null != manager) {
                    manager.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                }
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("keyword",query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intent = null;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_meinv) {
            intent = new Intent();
            intent.setClass(MainActivity.this, ZhuanTiActivity.class);
            intent.putExtra("pid", 2226);
            intent.putExtra("pname", "美女");
            // Handle the camera action
        } else if (id == R.id.nav_mengchong) {
            intent = new Intent();
            intent.setClass(MainActivity.this, ZhuanTiActivity.class);
            intent.putExtra("pid", 2231);  //萌宠
            intent.putExtra("pname", "萌宠");
        } else if (id == R.id.nav_fengjing) {
            intent = new Intent();
            intent.setClass(MainActivity.this, ZhuanTiActivity.class);
            intent.putExtra("pid", 2262);  //风景
            intent.putExtra("pname", "风景");

        } else if (id == R.id.nav_zhiwu) {
            intent = new Intent();
            intent.setClass(MainActivity.this, ZhuanTiActivity.class);
            intent.putExtra("pid", 2233);  //植物
            intent.putExtra("pname", "植物");
        } else if (id == R.id.nav_shenghuo) {
            intent = new Intent();
            intent.setClass(MainActivity.this, ZhuanTiActivity.class);
            intent.putExtra("pid", 2241);  //生活
            intent.putExtra("pname", "生活");
        } else if (id == R.id.nav_renwu) {
            intent = new Intent();
            intent.setClass(MainActivity.this, ZhuanTiActivity.class);
            intent.putExtra("pid", 2225);  //人物
            intent.putExtra("pname", "人物");
        } else if (id == R.id.nav_xingqing) {
            intent = new Intent();
            intent.setClass(MainActivity.this, ZhuanTiActivity.class);
            intent.putExtra("pid", 2251);  //心情
            intent.putExtra("pname", "心情");
        } else if (id == R.id.nav_fengge) {
            intent = new Intent();
            intent.setClass(MainActivity.this, ZhuanTiActivity.class);
            intent.putExtra("pid", 2246);  //风格
            intent.putExtra("pname", "风格");
        } else if (id == R.id.nav_dongman) {
            intent = new Intent();
            intent.setClass(MainActivity.this, ZhuanTiActivity.class);
            intent.putExtra("pid", 2236);  //动漫
            intent.putExtra("pname", "动漫");
        }else if(id==R.id.nav_about){
            final AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
      AlertDialog dialog=  builder.setTitle("关于")
                    .setIcon(R.mipmap.ic_launcher)
                    .setMessage("本应用数据来源于互联网。开发：Adam /n Email:adamin1990@gmail.com")
                    .setCancelable(true)
                    .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           dialog.dismiss();
                        }
                    })
                    .create();
            dialog.show();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        if (null != intent) {
            startActivity(intent);

        }
        return true;
    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        page++;
        if (page <= pagecount + 1) {
            fetchAlbum.getAlbum(20 * (page - 1), end)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Album>() {
                        @Override
                        public void call(Album album) {
                            datas.addAll(album.getData());
                            mainAdapter.notifyDataSetChanged();

                        }
                    });
        } else {
            Snackbar.make(mRecycler, "没有数据了哦~~", Snackbar.LENGTH_SHORT).show();
            mRecycler.getMoreProgressView().setVisibility(View.GONE);
        }

    }

   public void makeSnake(String content){
       Snackbar.make(fab,content,Snackbar.LENGTH_INDEFINITE).show();
   }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
