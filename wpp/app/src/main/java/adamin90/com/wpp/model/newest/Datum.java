
package adamin90.com.wpp.model.newest;

import java.util.ArrayList;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Datum {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("list")
    @Expose
    private java.util.List<adamin90.com.wpp.model.List> list = new ArrayList<adamin90.com.wpp.model.List>();

    /**
     * 
     * @return
     *     The date
     */
    public String getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *     The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * 
     * @return
     *     The list
     */
    public java.util.List<adamin90.com.wpp.model.List> getList() {
        return list;
    }

    /**
     * 
     * @param list
     *     The list
     */
    public void setList(java.util.List<adamin90.com.wpp.model.List> list) {
        this.list = list;
    }

}
