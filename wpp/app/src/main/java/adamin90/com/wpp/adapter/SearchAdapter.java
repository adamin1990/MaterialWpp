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

import adamin90.com.wpp.Activity.SingleImageActivity;
import adamin90.com.wpp.R;
import adamin90.com.wpp.model.search.Datum;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by LiTao on 2015-11-25-13:35.
 * Company: QD24so
 * Email: 14846869@qq.com
 * WebSite: http://lixiaopeng.top
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyHolder> {

    private List<Datum> list;

    public SearchAdapter(List<Datum> list) {
        this.list = list;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        Picasso.with(holder.itemView.getContext()).load(list.get(position).getPageImgUrl()).noPlaceholder()
                .into(holder.imageView);
        holder.title.setText(list.get(position).getDownCount());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.itemView.getContext(), SingleImageActivity.class);
                intent.putExtra("image",list.get(position).getOrginUrl());
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
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
