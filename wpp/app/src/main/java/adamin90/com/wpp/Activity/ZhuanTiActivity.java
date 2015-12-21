package adamin90.com.wpp.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;


import java.util.ArrayList;
import java.util.List;

import adamin90.com.wpp.R;
import adamin90.com.wpp.adapter.OtherAdapter;
import adamin90.com.wpp.fragment.FragmentNorMal;
import adamin90.com.wpp.fragment.OtherFragment;
import adamin90.com.wpp.fragment.TopicChoiceFragment;
import adamin90.com.wpp.fragment.ZhuanTiFragment;
import adamin90.com.wpp.fragment.dummy.DummyContent;
import adamin90.com.wpp.model.tablist.Datum;
import adamin90.com.wpp.model.tablist.TabList;
import adamin90.com.wpp.service.TabListService;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class ZhuanTiActivity extends AppCompatActivity  {
    @Bind(R.id.viewpager)
    ViewPager pager;
    @Bind(R.id.tablayout)
    TabLayout tabLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;


    private int pid = 2226;
    private ZhuanTiAdapter adapter;
    private TabListService tabListService;
    private Retrofit retrofit;
    private TabList tabList;
    private List<adamin90.com.wpp.model.tablist.Datum> tabdatums;

    private String name ;

    private boolean defaluttab=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuan_ti);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        tabdatums = new ArrayList<>();
        pid = getIntent().getIntExtra("pid", 2262);
        name =getIntent().getStringExtra("pname");
        getSupportActionBar().setTitle(name+"");
        getTabs();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
                Intent intent = new Intent(ZhuanTiActivity.this, SearchActivity.class);
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

    private void getTabs() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://client.pic.hiapk.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())

                .build();
        tabListService = retrofit.create(TabListService.class);
        tabListService.getTabList(pid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<TabList>() {
                               @Override
                               public void call(TabList tabList) {
                                   tabdatums = tabList.getData();
                                   if (tabdatums.size() > 0) {
                                       defaluttab = false;
                                   } else {
                                       defaluttab = true;
                                   }
                                   setupViewPager();
                                   tabLayout.setupWithViewPager(pager);
                               }
                           }, new Action1<Throwable>() {
                               @Override
                               public void call(Throwable throwable) {

                               }
                           }
                        , new Action0() {
                            @Override
                            public void call() {

                            }
                        }
                );

    }

    private void setupViewPager() {
        ZhuanTiAdapter adapter = new ZhuanTiAdapter(getSupportFragmentManager());
        if(defaluttab){
            adapter.addFragment(TopicChoiceFragment.newInstance(pid),"专题精选");
            adapter.addFragment(OtherFragment.newInstance(pid),"其他");
        }
        else {
            for (int i = 0; i < tabdatums.size()+1; i++) {
                if(i==0){
                    adapter.addFragment(TopicChoiceFragment.newInstance(pid),"专题精选");
                }else{
                    adapter.addFragment(FragmentNorMal.newInstance(Integer.valueOf(tabdatums.get(i-1).getId())), tabdatums.get(i-1).getTitle());
                    Timber.e("cateid是多少"+tabdatums.get(i-1).getId());
                }

            }
        }


        pager.setAdapter(adapter);
    }




    class ZhuanTiAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public ZhuanTiAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
