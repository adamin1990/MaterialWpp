package adamin90.com.wpp.service;

import adamin90.com.wpp.model.normal.NormalModel;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by LiTao on 2015-11-21-17:21.
 * Company: QD24so
 * Email: 14846869@qq.com
 * WebSite: http://lixiaopeng.top
 * 正常tab接口
 */
public interface FetchNormalService {
    @GET("index.php/3/Wallpaper/PicByCate/cate/{cate}/order/1/start/{start}/limit/{limit}/")
    Observable<NormalModel> fetchNormal(@Path("cate") int cate,@Path("start") int start,@Path("limit") int limit);
}
