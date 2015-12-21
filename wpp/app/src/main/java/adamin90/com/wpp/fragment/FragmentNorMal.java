package adamin90.com.wpp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import adamin90.com.wpp.Constant;
import adamin90.com.wpp.R;
import adamin90.com.wpp.adapter.NormalAdapter;
import adamin90.com.wpp.model.normal.Datum;
import adamin90.com.wpp.model.normal.NormalModel;
import adamin90.com.wpp.service.FetchNormalService;
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

/**
 * Created by LiTao on 2015-11-21-17:18.
 * Company: QD24so
 * Email: 14846869@qq.com
 * WebSite: http://lixiaopeng.top
 */
public class FragmentNorMal extends Fragment  implements SwipeRefreshLayout.OnRefreshListener,OnMoreListener{

    private int cate;   //id
    private int start=0;
    private int limit=20;
    private int page=1;
    private int pagecount;


    private Retrofit retrofit;
    private FetchNormalService fetchNormalService;

    private List<Datum> datums;

    private LinearLayoutManager linearLayoutManager;
    @Bind(R.id.listtopic)
    SuperRecyclerView recyclerView;

    private NormalAdapter adapter;


    public static FragmentNorMal newInstance(int cate){

        FragmentNorMal fragmentNorMal=new FragmentNorMal();
        Bundle bundle=new Bundle();
        bundle.putInt("cate",cate);
        fragmentNorMal.setArguments(bundle);
        return fragmentNorMal;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_topicchoice,container,false);
        ButterKnife.bind(this, view);
        init();

        return view;
    }

    private void init() {
        cate=getArguments().getInt("cate");
        datums=new ArrayList<>();
        adapter=new NormalAdapter(datums,cate);
        linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setRefreshingColorResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
        recyclerView.setOnMoreListener(this);
        recyclerView.getSwipeToRefresh().setOnRefreshListener(this);
        recyclerView.setAdapter(adapter);
        retrofit=new Retrofit.Builder()
                .baseUrl(Constant.BASEURL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        fetchNormalService=retrofit.create(FetchNormalService.class);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDatas();
    }

    private void getDatas() {
        recyclerView.getSwipeToRefresh().setRefreshing(true);
        fetchNormalService.fetchNormal(cate,start,limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NormalModel>() {
                    @Override
                    public void call(NormalModel normalModel) {
                        pagecount=Integer.valueOf(normalModel.getTotalCount())/limit+1;
                        Timber.e("pagecount是"+pagecount);
                        datums.addAll(normalModel.getData());
                        adapter.notifyDataSetChanged();

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        recyclerView.getSwipeToRefresh().setRefreshing(false);
                    }
                });

    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        page++;
        if(page<=pagecount){
            getDatas();
        }else {
            Snackbar.make(recyclerView,"没有更多数据哦~~",Snackbar.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onRefresh() {

    }
}
