
package updater;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Client_ {

    @SerializedName("argument")
    @Expose
    private String argument;
    @SerializedName("file")
    @Expose
    private File file;
    @SerializedName("type")
    @Expose
    private String type;

    public String getArgument() {
        return argument;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
