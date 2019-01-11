
package updater;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Library {

    @SerializedName("downloads")
    @Expose
    private Downloads_ downloads;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("natives")
    @Expose
    private Natives natives;
    @SerializedName("extract")
    @Expose
    private Extract extract;
    @SerializedName("rules")
    @Expose
    private List<Rule_> rules = null;

    public Downloads_ getDownloads() {
        return downloads;
    }

    public void setDownloads(Downloads_ downloads) {
        this.downloads = downloads;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Natives getNatives() {
        return natives;
    }

    public void setNatives(Natives natives) {
        this.natives = natives;
    }

    public Extract getExtract() {
        return extract;
    }

    public void setExtract(Extract extract) {
        this.extract = extract;
    }

    public List<Rule_> getRules() {
        return rules;
    }

    public void setRules(List<Rule_> rules) {
        this.rules = rules;
    }

}
