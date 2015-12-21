package adamin90.com.wpp.service;

import adamin90.com.wpp.model.mostsearch.MostSearchData;
import adamin90.com.wpp.model.search.SearchData;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;
import rx.Observer;

/**
 * Created by LiTao on 2015-11-20-22:01.
 * Company: QD24so
 * Email: 14846869@qq.com
 * WebSite: http://lixiaopeng.top
 * 搜索标签
 */
public interface FecthSearch {
    /**
     * 大家都在搜
     * @return
     */
    @GET("/index.php/3/Keyword/HotKeyword/type/0/start/0/limit/15/")
    Observable<MostSearchData> fetchMostSeach();

    /**
     *用户壁纸查询
     * @return
     */
    @GET("/index.php/3/Keyword/HotKeyword/type/1/start/0/limit/6/")
    Observable<MostSearchData> fetchMemberSearch();

    /**
     * 用户查询接口
     * @return
     */
@GET("/index.php/3/Wallpaper/picByKeyword/word/{keyword}/start/{start}/limit/{limit}/")
    Observable<SearchData> fetchSearchData(@Path("keyword")  String keyword,@Path("start") int start,
                                           @Path("limit") int limit);
}
