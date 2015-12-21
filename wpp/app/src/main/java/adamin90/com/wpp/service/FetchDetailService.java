package adamin90.com.wpp.service;

import adamin90.com.wpp.model.detail.DetailData;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by LiTao on 2015-11-23-14:25.
 * Company: QD24so
 * Email: 14846869@qq.com
 * WebSite: http://lixiaopeng.top
 * 获取图片列表详情
 */
public interface FetchDetailService {

    @GET("index.php/3/Wallpaper/picList/order/{order}/source_type/{sourcetype}/source_id/{sourceid}/update/{update}/index/{index}/limit/{limit}/pixel/1080*1920/")
    Observable<DetailData> fetchDetails(@Path("order")  String order,@Path("sourcetype") int sourcetype,@Path("sourceid") int sourceid,@Path("index")int index, @Path("limit") int limit,@Path("update") int update);

}
