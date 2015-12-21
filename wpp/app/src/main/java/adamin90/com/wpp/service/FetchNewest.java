package adamin90.com.wpp.service;

import android.database.Observable;

import adamin90.com.wpp.model.newest.NewestData;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by LiTao on 2015-11-20-18:13.
 * Company: QD24so
 * Email: 14846869@qq.com
 * WebSite: http://lixiaopeng.top
 * 获取最新数据
 */
public interface FetchNewest {
@GET("index.php/3/Wallpaper/picLatest/start/{start}/limit/{limit}/")
    Observable<NewestData> fetchNewestData(@Path("start") int start,@Path("limit") int limit);
}
