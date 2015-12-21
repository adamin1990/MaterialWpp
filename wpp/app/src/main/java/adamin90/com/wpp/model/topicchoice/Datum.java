
package adamin90.com.wpp.model.topicchoice;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Datum {

    @SerializedName("aid")
    @Expose
    private String aid;
    @SerializedName("pid")
    @Expose
    private String pid;
    @SerializedName("top_img_url")
    @Expose
    private String topImgUrl;
    @SerializedName("title")
    @Expose
    private String title;

    /**
     * 
     * @return
     *     The aid
     */
    public String getAid() {
        return aid;
    }

    /**
     * 
     * @param aid
     *     The aid
     */
    public void setAid(String aid) {
        this.aid = aid;
    }

    /**
     * 
     * @return
     *     The pid
     */
    public String getPid() {
        return pid;
    }

    /**
     * 
     * @param pid
     *     The pid
     */
    public void setPid(String pid) {
        this.pid = pid;
    }

    /**
     * 
     * @return
     *     The topImgUrl
     */
    public String getTopImgUrl() {
        return topImgUrl;
    }

    /**
     * 
     * @param topImgUrl
     *     The top_img_url
     */
    public void setTopImgUrl(String topImgUrl) {
        this.topImgUrl = topImgUrl;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

}
