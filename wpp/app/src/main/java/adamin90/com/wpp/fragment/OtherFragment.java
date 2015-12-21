package adamin90.com.wpp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import adamin90.com.wpp.Constant;
import adamin90.com.wpp.R;
import adamin90.com.wpp.adapter.OtherAdapter;
import adamin90.com.wpp.model.other.Datum;
import adamin90.com.wpp.model.other.OtherModel;
import adamin90.com.wpp.service.OtherService;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by LiTao on 2015-11-21-15:36.
 * Company: QD24so
 * Email: 14846869@qq.com
 * WebSite: http://lixiaopeng.top
 */
public class OtherFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,OnMoreListener{

    private int pid;
    private int start =0;
    private int limit=20;
    private int pagecount=1;
    public static final String PID="pid";
    private int page=1;

    private Retrofit retrofit;
    private OtherService otherService;

    private List<Datum> otherdatums;
    private GridLayoutManager layoutManager;
    private OtherAdapter adapter;

    private int sourceid,sourcetype;

    @Bind(R.id.listtopic)
    SuperRecyclerView recyclerView;

    public static OtherFragment newInstance(int pid){
        OtherFragment otherFragment=new OtherFragment();
        Bundle bundle=new Bundle();
        bundle.putInt(PID, pid);
        otherFragment.setArguments(bundle);
        return  otherFragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_topicchoice,container,false);
        ButterKnife.bind(this,v);
        pid=getArguments().getInt("pid");
        initViews();
        initRetrofit();
        initlisener();
        return v;
    }

    private void initlisener() {
        recyclerView.getSwipeToRefresh().setOnRefreshListener(this);
        recyclerView.setupMoreListener(this,1);
    }

    private void initViews() {

        layoutManager=new GridLayoutManager(getActivity(),2);
        otherdatums=new ArrayList<>();
        adapter=new OtherAdapter(pid,otherdatums);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setRefreshingColorResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
        recyclerView.setAdapter(adapter);
    }

    private void initRetrofit() {
        retrofit=new Retrofit.Builder()
                .baseUrl(Constant.BASEURL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        otherService=retrofit.create(OtherService.class);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData();
    }

    private void getData() {
        recyclerView.getSwipeToRefresh().setRefreshing(true);
        otherService.fetchOther(pid,start,limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<OtherModel>() {
                    @Override
                    public void call(OtherModel otherModel) {
                        sourceid=otherModel.getSourceType();
                        pagecount=Integer.valueOf(otherModel.getTotalCount())/limit+1;
                        otherdatums.addAll(otherModel.getData());
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
    public void onRefresh() {

    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        page++;
        start=(page-1)*limit;
        if(page<=pagecount){
            getData();
        }
        else {
            Snackbar.make(recyclerView,"没有数据了哦",Snackbar.LENGTH_SHORT).show();
        }

    }
}
