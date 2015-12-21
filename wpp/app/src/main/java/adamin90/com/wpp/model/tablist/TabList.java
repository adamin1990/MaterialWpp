
package adamin90.com.wpp.model.tablist;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import adamin90.com.wpp.model.*;

@Generated("org.jsonschema2pojo")
public class TabList {

    @SerializedName("data")
    @Expose
    private List<adamin90.com.wpp.model.tablist.Datum> data = new ArrayList<adamin90.com.wpp.model.tablist.Datum>();
    @SerializedName("total_count")
    @Expose
    private Integer totalCount;
    @SerializedName("code")
    @Expose
    private Integer code;

    /**
     * 
     * @return
     *     The data
     */
    public List<adamin90.com.wpp.model.tablist.Datum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<adamin90.com.wpp.model.tablist.Datum> data) {
        this.data = data;
    }

    /**
     * 
     * @return
     *     The totalCount
     */
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     * 
     * @param totalCount
     *     The total_count
     */
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * 
     * @return
     *     The code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 
     * @param code
     *     The code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

}
