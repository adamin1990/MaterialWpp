package adamin90.com.wpp.service;


import adamin90.com.wpp.model.tablist.TabList;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by LiTao on 2015-11-20-13:09.
 * Company: QD24so
 * Email: 14846869@qq.com
 * WebSite: http://lixiaopeng.top
 * 动态获取tab列表
 */
public interface TabListService {
    @GET("index.php/3/Wallpaper/subPiccates/pid/{pid}/")
    Observable<TabList> getTabList(@Path("pid") int pid);
}
