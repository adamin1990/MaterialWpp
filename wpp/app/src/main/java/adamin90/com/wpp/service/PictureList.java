package adamin90.com.wpp.service;

import adamin90.com.wpp.model.piclist.PicList;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by LiTao on 2015-11-17-10:45.
 * Company: QD24so
 * Email: 14846869@qq.com
 * WebSite: http://lixiaopeng.top
 */
public interface PictureList {
    @GET("3/Wallpaper/picList/order/-1/source_type/1/source_id/{sourceid}/update/0/index/{index}/limit/{limit}/pixel/1080*1920/")
    Observable<PicList> getPicList(@Path("sourceid") int id,@Path("index") int index,@Path("limit") int limit);
}
