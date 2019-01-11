
package updater;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Downloads_ {

    @SerializedName("artifact")
    @Expose
    private Artifact artifact;
    @SerializedName("classifiers")
    @Expose
    private Classifiers classifiers;

    public Artifact getArtifact() {
        return artifact;
    }

    public void setArtifact(Artifact artifact) {
        this.artifact = artifact;
    }

    public Classifiers getClassifiers() {
        return classifiers;
    }

    public void setClassifiers(Classifiers classifiers) {
        this.classifiers = classifiers;
    }

}
