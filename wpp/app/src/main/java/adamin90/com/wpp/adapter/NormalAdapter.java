package adamin90.com.wpp.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import adamin90.com.wpp.Activity.DetailActivity;
import adamin90.com.wpp.R;
import adamin90.com.wpp.model.normal.Datum;
import adamin90.com.wpp.model.normal.NormalModel;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by LiTao on 2015-11-21-17:36.
 * Company: QD24so
 * Email: 14846869@qq.com
 * WebSite: http://lixiaopeng.top
 */
public class NormalAdapter extends RecyclerView.Adapter<NormalAdapter.MyHolder> {
    public static  final  int TYPE_FIRST=0X1;
    public static  final  int TYPE_NORMAL=0X2;

    private List<Datum> datums;
    private int cate;

    public NormalAdapter(List<Datum> datums, int cate) {
        this.datums = datums;
        this.cate = cate;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return  TYPE_FIRST;
        }
        return  TYPE_NORMAL;

    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        Picasso.with(holder.itemView.getContext()).load(datums.get(position).getSmallImgUrl())
                .noPlaceholder()
                .into(holder.imageView);
        holder.title.setText(datums.get(position).getDownCount());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.itemView.getContext(), DetailActivity.class);
                intent.putExtra("id",cate);
                intent.putExtra("index",position);
                intent.putExtra("sourcetype",3);
                intent.putExtra("order","1");
                intent.putExtra("update",0);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datums.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.album_image)
        ImageView imageView;

        @Bind(R.id.album_title)
        TextView title;
        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
