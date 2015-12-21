
package adamin90.com.wpp.model.piclist;

import java.util.ArrayList;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Data {

    @SerializedName("is_next_left")
    @Expose
    private Integer isNextLeft;
    @SerializedName("is_next_right")
    @Expose
    private Integer isNextRight;
    @SerializedName("list")
    @Expose
    private java.util.List<adamin90.com.wpp.model.List> list = new ArrayList<adamin90.com.wpp.model.List>();
    @SerializedName("source_type")
    @Expose
    private Integer sourceType;
    @SerializedName("source_id")
    @Expose
    private Integer sourceId;

    /**
     * 
     * @return
     *     The isNextLeft
     */
    public Integer getIsNextLeft() {
        return isNextLeft;
    }

    /**
     * 
     * @param isNextLeft
     *     The is_next_left
     */
    public void setIsNextLeft(Integer isNextLeft) {
        this.isNextLeft = isNextLeft;
    }

    /**
     * 
     * @return
     *     The isNextRight
     */
    public Integer getIsNextRight() {
        return isNextRight;
    }

    /**
     * 
     * @param isNextRight
     *     The is_next_right
     */
    public void setIsNextRight(Integer isNextRight) {
        this.isNextRight = isNextRight;
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

    /**
     * 
     * @return
     *     The sourceType
     */
    public Integer getSourceType() {
        return sourceType;
    }

    /**
     * 
     * @param sourceType
     *     The source_type
     */
    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    /**
     * 
     * @return
     *     The sourceId
     */
    public Integer getSourceId() {
        return sourceId;
    }

    /**
     * 
     * @param sourceId
     *     The source_id
     */
    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

}
