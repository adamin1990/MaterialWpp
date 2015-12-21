package adamin90.com.wpp.adapter;

import android.graphics.Bitmap;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import adamin90.com.wpp.R;
import adamin90.com.wpp.model.detail.DetailData;
import timber.log.Timber;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by LiTao on 2015-11-23-17:01.
 * Company: QD24so
 * Email: 14846869@qq.com
 * WebSite: http://lixiaopeng.top
 */
public class DetailPagerAdapter extends PagerAdapter {
    private List<adamin90.com.wpp.model.detail.List> lists;
    private PhotoViewAttacher photoViewAttacher;
    private TabLisenter lisenter;


    public DetailPagerAdapter(List<adamin90.com.wpp.model.detail.List> lists, TabLisenter lisenter) {
        this.lists = lists;
        this.lisenter = lisenter;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final ImageView imageView=new ImageView(container.getContext());

        Picasso.with(container.getContext()).load(lists.get(position).getPicUrl()).config(Bitmap.Config.RGB_565)
                .noFade()
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        photoViewAttacher = new PhotoViewAttacher(imageView);
                        photoViewAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                            @Override
                            public void onPhotoTap(View view, float x, float y) {
                                if (lisenter != null) {
                                    lisenter.Tab();
                                }
                            }
                        });
                        photoViewAttacher.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                if (lisenter != null) {
                                    lisenter.LongTab();
                                    return true;
                                }
                                return false;
                            }
                        });

                    }

                    @Override
                    public void onError() {

                    }
                });

        ((ViewPager)container).addView(imageView, 0);

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((View) object);
    }

    public interface  TabLisenter{
        void Tab();
        void LongTab();
    }
}
