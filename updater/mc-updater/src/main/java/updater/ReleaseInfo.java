
package updater;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReleaseInfo {

    @SerializedName("arguments")
    @Expose
    private Arguments arguments;
    @SerializedName("assetIndex")
    @Expose
    private AssetIndex assetIndex;
    @SerializedName("assets")
    @Expose
    private String assets;
    @SerializedName("downloads")
    @Expose
    private Downloads downloads;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("libraries")
    @Expose
    private List<Library> libraries = null;
    @SerializedName("logging")
    @Expose
    private Logging logging;
    @SerializedName("mainClass")
    @Expose
    private String mainClass;
    @SerializedName("minimumLauncherVersion")
    @Expose
    private Integer minimumLauncherVersion;
    @SerializedName("releaseTime")
    @Expose
    private String releaseTime;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("type")
    @Expose
    private String type;

    public Arguments getArguments() {
        return arguments;
    }

    public void setArguments(Arguments arguments) {
        this.arguments = arguments;
    }

    public AssetIndex getAssetIndex() {
        return assetIndex;
    }

    public void setAssetIndex(AssetIndex assetIndex) {
        this.assetIndex = assetIndex;
    }

    public String getAssets() {
        return assets;
    }

    public void setAssets(String assets) {
        this.assets = assets;
    }

    public Downloads getDownloads() {
        return downloads;
    }

    public void setDownloads(Downloads downloads) {
        this.downloads = downloads;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Library> getLibraries() {
        return libraries;
    }

    public void setLibraries(List<Library> libraries) {
        this.libraries = libraries;
    }

    public Logging getLogging() {
        return logging;
    }

    public void setLogging(Logging logging) {
        this.logging = logging;
    }

    public String getMainClass() {
        return mainClass;
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    public Integer getMinimumLauncherVersion() {
        return minimumLauncherVersion;
    }

    public void setMinimumLauncherVersion(Integer minimumLauncherVersion) {
        this.minimumLauncherVersion = minimumLauncherVersion;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
