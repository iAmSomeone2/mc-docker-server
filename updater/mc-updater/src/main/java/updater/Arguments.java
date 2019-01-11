
package updater;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Arguments {

    @SerializedName("game")
    @Expose
    private List<String> game = null;
    @SerializedName("jvm")
    @Expose
    private List<Jvm> jvm = null;

    public List<String> getGame() {
        return game;
    }

    public void setGame(List<String> game) {
        this.game = game;
    }

    public List<Jvm> getJvm() {
        return jvm;
    }

    public void setJvm(List<Jvm> jvm) {
        this.jvm = jvm;
    }

}
