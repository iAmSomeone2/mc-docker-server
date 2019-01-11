
package updater;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Natives {

    @SerializedName("linux")
    @Expose
    private String linux;
    @SerializedName("osx")
    @Expose
    private String osx;
    @SerializedName("windows")
    @Expose
    private String windows;

    public String getLinux() {
        return linux;
    }

    public void setLinux(String linux) {
        this.linux = linux;
    }

    public String getOsx() {
        return osx;
    }

    public void setOsx(String osx) {
        this.osx = osx;
    }

    public String getWindows() {
        return windows;
    }

    public void setWindows(String windows) {
        this.windows = windows;
    }

}
