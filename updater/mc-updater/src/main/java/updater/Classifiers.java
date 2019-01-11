
package updater;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Classifiers {

    @SerializedName("javadoc")
    @Expose
    private Javadoc javadoc;
    @SerializedName("natives-linux")
    @Expose
    private NativesLinux nativesLinux;
    @SerializedName("natives-macos")
    @Expose
    private NativesMacos nativesMacos;
    @SerializedName("natives-windows")
    @Expose
    private NativesWindows nativesWindows;
    @SerializedName("sources")
    @Expose
    private Sources sources;
    @SerializedName("natives-osx")
    @Expose
    private NativesOsx nativesOsx;

    public Javadoc getJavadoc() {
        return javadoc;
    }

    public void setJavadoc(Javadoc javadoc) {
        this.javadoc = javadoc;
    }

    public NativesLinux getNativesLinux() {
        return nativesLinux;
    }

    public void setNativesLinux(NativesLinux nativesLinux) {
        this.nativesLinux = nativesLinux;
    }

    public NativesMacos getNativesMacos() {
        return nativesMacos;
    }

    public void setNativesMacos(NativesMacos nativesMacos) {
        this.nativesMacos = nativesMacos;
    }

    public NativesWindows getNativesWindows() {
        return nativesWindows;
    }

    public void setNativesWindows(NativesWindows nativesWindows) {
        this.nativesWindows = nativesWindows;
    }

    public Sources getSources() {
        return sources;
    }

    public void setSources(Sources sources) {
        this.sources = sources;
    }

    public NativesOsx getNativesOsx() {
        return nativesOsx;
    }

    public void setNativesOsx(NativesOsx nativesOsx) {
        this.nativesOsx = nativesOsx;
    }

}
