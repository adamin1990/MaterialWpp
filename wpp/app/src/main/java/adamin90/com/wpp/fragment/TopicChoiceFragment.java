package adamin90.com.wpp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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
import adamin90.com.wpp.adapter.TopicChoiceAdapter;
import adamin90.com.wpp.model.topicchoice.Datum;
import adamin90.com.wpp.model.topicchoice.TopicModel;
import adamin90.com.wpp.service.TopicChoice;
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
 * Created by LiTao on 2015-11-21-12:05.
 * Company: QD24so
 * Email: 14846869@qq.com
 * WebSite: http://lixiaopeng.top
 * 专题精选通用fragment
 */
public class TopicChoiceFragment extends Fragment  implements SwipeRefreshLayout.OnRefreshListener{

    private  int cate;
    private int page=1;
    private int start =0;
    private int limit=20;
    private int pagecount=1;
    public static final String PID="pid";

    private Retrofit retrofit;
    private TopicChoice topicChoice;
    private LinearLayoutManager layoutManager;
    private List<Datum> datums;
    private TopicChoiceAdapter adapter;




    @Bind(R.id.listtopic)
    SuperRecyclerView recyclerView;



    public static TopicChoiceFragment newInstance(int pid){
        TopicChoiceFragment topicChoiceFragment=new TopicChoiceFragment();
        Bundle bundle=new Bundle();
        bundle.putInt(PID, pid);
        topicChoiceFragment.setArguments(bundle);
        return  topicChoiceFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_topicchoice,container,false);
        ButterKnife.bind(this, v);
        getParams();
        initView();
        initRetrofit();
        return v;
    }

    private void initView() {
        datums=new ArrayList<>();
        adapter=new TopicChoiceAdapter(datums);
        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setRefreshingColorResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
        recyclerView.setAdapter(adapter);
    }

    private void getParams() {
        cate=getArguments().getInt("pid");
    }

    private void initRetrofit() {
        retrofit=new Retrofit.Builder()
                .baseUrl(Constant.BASEURL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
       topicChoice= retrofit.create(TopicChoice.class);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData();
        setLisener();
    }

    private void setLisener() {
        recyclerView.setOnMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
                page++;
                start=(page-1)*limit;
             if(page<=pagecount){
                 getData();
             }
                else {
                 Snackbar.make(recyclerView,"没有更多数据哦~~",Snackbar.LENGTH_SHORT)
                         .show();
                 recyclerView.hideMoreProgress();
             }

            }
        });
    }

    private void getData() {
        recyclerView.getSwipeToRefresh().setRefreshing(true);
        topicChoice.getTopicChoice(cate,start,limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<TopicModel>() {
                    @Override
                    public void call(TopicModel topicModel) {

                        pagecount=Integer.valueOf(topicModel.getTotalCount())/limit+1;
                        datums.addAll(topicModel.getData());
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
//        datums.clear();
//        page=1;
//        getData();

    }
}
