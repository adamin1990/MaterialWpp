
package adamin90.com.wpp.model.detail;

import java.util.ArrayList;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Data {

    @SerializedName("list")
    @Expose
    private java.util.List<List> list = new ArrayList<List>();
    @SerializedName("is_next_right")
    @Expose
    private Integer isNextRight;
    @SerializedName("source_type")
    @Expose
    private Integer sourceType;
    @SerializedName("source_id")
    @Expose
    private Integer sourceId;

    /**
     * 
     * @return
     *     The list
     */
    public java.util.List<List> getList() {
        return list;
    }

    /**
     * 
     * @param list
     *     The list
     */
    public void setList(java.util.List<List> list) {
        this.list = list;
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
