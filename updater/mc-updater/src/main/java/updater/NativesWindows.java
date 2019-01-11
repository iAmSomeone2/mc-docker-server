
package updater;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NativesWindows {

    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("sha1")
    @Expose
    private String sha1;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("url")
    @Expose
    private String url;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
