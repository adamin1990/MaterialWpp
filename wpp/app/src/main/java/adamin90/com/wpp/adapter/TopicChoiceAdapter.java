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
import adamin90.com.wpp.model.topicchoice.Datum;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by LiTao on 2015-11-21-14:16.
 * Company: QD24so
 * Email: 14846869@qq.com
 * WebSite: http://lixiaopeng.top
 * 专辑精选adapter
 */
public class TopicChoiceAdapter extends RecyclerView.Adapter<TopicChoiceAdapter.MyHolder> {

    private List<Datum> datums;

    public TopicChoiceAdapter(List<Datum> datums) {
        this.datums = datums;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        Picasso.with(holder.itemView.getContext()).load(datums.get(position).getTopImgUrl())
                .noPlaceholder()
                .into(holder.imageView);
        holder.title.setText(datums.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.itemView.getContext(), DetailActivity.class);
                intent.putExtra("id",Integer.valueOf(datums.get(position).getAid()));
                intent.putExtra("index",0);
                intent.putExtra("sourcetype",1);
                intent.putExtra("order","-1");
                intent.putExtra("update",0);
                intent.putExtra("title",datums.get(position).getTitle());
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
