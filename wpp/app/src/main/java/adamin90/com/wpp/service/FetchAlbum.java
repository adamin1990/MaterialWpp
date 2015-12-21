package adamin90.com.wpp.service;

import java.util.List;

import adamin90.com.wpp.model.Album;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by LiTao on 2015-11-17-9:58.
 * Company: QD24so
 * Email: 14846869@qq.com
 * WebSite: http://lixiaopeng.top
 * 首页每日精选专辑
 */
public interface FetchAlbum {

    @GET("3/Wallpaper/album/start/{star}/limit/{end}/")
    Observable<Album> getAlbum(@Path("star") int star,@Path("end") int end);
}
