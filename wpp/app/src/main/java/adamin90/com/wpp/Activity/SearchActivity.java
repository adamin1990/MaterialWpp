package adamin90.com.wpp.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import adamin90.com.wpp.Constant;
import adamin90.com.wpp.R;
import adamin90.com.wpp.adapter.SearchAdapter;
import adamin90.com.wpp.model.search.Datum;
import adamin90.com.wpp.model.search.SearchData;
import adamin90.com.wpp.service.FecthSearch;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity implements OnMoreListener {
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.recyclerview)
    SuperRecyclerView recyclerView;

    private String keyword;
    private int page=1;
    private int limit=24;
    private int start=0;
    private int count=0;


    private Retrofit retrofit;
    private FecthSearch fecthSearch;
    private GridLayoutManager gridLayoutManager;
    private List<Datum> datumList;
    private SearchAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        init();
        getData();
            }

    private void init() {
        setSupportActionBar(toolbar);
        keyword=getIntent().getStringExtra("keyword");
        getSupportActionBar().setTitle(keyword);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        datumList=new ArrayList<>();
        adapter=new SearchAdapter(datumList);
        retrofit=new Retrofit.Builder()
                .baseUrl(Constant.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        fecthSearch=retrofit.create(FecthSearch.class);
        gridLayoutManager=new GridLayoutManager(SearchActivity.this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setupMoreListener(this,1);
        getData();

    }

    private void getData() {
        fecthSearch.fetchSearchData(keyword,start,limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<SearchData>() {
                    @Override
                    public void call(SearchData searchData) {
                        count= Integer.parseInt(searchData.getTotalCount());
                        datumList.addAll(searchData.getData());
                        adapter.notifyDataSetChanged();
                    }
                });

    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        page++;
        start=limit*(page-1);
        getData();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

