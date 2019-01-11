
package updater;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rule_ {

    @SerializedName("action")
    @Expose
    private String action;
    @SerializedName("os")
    @Expose
    private Os_ os;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Os_ getOs() {
        return os;
    }

    public void setOs(Os_ os) {
        this.os = os;
    }

}
