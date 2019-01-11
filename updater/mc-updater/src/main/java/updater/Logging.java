
package updater;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Logging {

    @SerializedName("client")
    @Expose
    private Client_ client;

    public Client_ getClient() {
        return client;
    }

    public void setClient(Client_ client) {
        this.client = client;
    }

}
