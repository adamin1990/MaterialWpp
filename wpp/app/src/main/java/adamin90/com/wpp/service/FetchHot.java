package adamin90.com.wpp.service;

import adamin90.com.wpp.model.hot.HotData;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by LiTao on 2015-11-20-21:55.
 * Company: QD24so
 * Email: 14846869@qq.com
 * WebSite: http://lixiaopeng.top
 * 热门数据
 */
public interface FetchHot {
    @GET("/index.php/3/Wallpaper/picByselection/order/1/start/{start}/limit/{limit}/")
    Observable<HotData> fetchHot(@Path("start") int start,@Path("limit") int limit);
}
