
package updater;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Jvm {

    @SerializedName("rules")
    @Expose
    private List<Rule> rules = null;
    @SerializedName("value")
    @Expose
    private String value;

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
