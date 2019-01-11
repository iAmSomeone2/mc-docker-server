import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Updater{
    public static void main(String[] args){
        // Let's start off with a quick test of GSON by taking the mc_version_manifest.json file and turning it into
        // Java objects.

        if (args.length == 2) {
            Gson gson = new Gson();
            JsonReader manifest = null;
            try {
                manifest = new JsonReader(new FileReader(new File(args[1])));
            } catch (FileNotFoundException notFound){
                System.out.println("Path: '" + args[1] + "' does not point to a valid JSON file.");
                System.exit(1);
            }

            VersionManifest versionManifest = gson.fromJson(manifest, VersionManifest.class);
        } else {
            System.out.println("No version manifest specified!\nUsage: java -jar updater.jar /home/mc_version_manifest.json");
        }

    }
}