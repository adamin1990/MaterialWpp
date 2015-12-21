
package adamin90.com.wpp.model.mostsearch;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Datum {

    @SerializedName("word")
    @Expose
    private String word;

    /**
     * 
     * @return
     *     The word
     */
    public String getWord() {
        return word;
    }

    /**
     * 
     * @param word
     *     The word
     */
    public void setWord(String word) {
        this.word = word;
    }

}
