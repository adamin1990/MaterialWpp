package adamin90.com.wpp.service;


import adamin90.com.wpp.model.topicchoice.TopicModel;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by LiTao on 2015-11-20-10:37.
 * Company: QD24so
 * Email: 14846869@qq.com
 * WebSite: http://lixiaopeng.top
 * 专题精选
 * /index.php/3/Wallpaper/banner/cate/2226/start/0/limit/20/
 */
public interface TopicChoice {
    @GET("index.php/3/Wallpaper/banner/cate/{cate}/start/{start}/limit/{limit}/")
    Observable<TopicModel> getTopicChoice(@Path("cate") int cate,@Path("start") int start,@Path("limit") int limit);
}
