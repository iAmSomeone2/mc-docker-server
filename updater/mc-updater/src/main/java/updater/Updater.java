package updater;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Updater{
    public static void main(String[] args){
        // Let's start off with a quick test of GSON by taking the mc_version_manifest.json file and turning it into
        // Java objects.

        if (args.length == 1) {
            Gson gson = new Gson();
            JsonReader manifest = null;
            try {
                manifest = new JsonReader(new FileReader(new File(args[0])));
            } catch (FileNotFoundException notFound){
                System.out.println("Path: '" + args[0] + "' does not point to a valid JSON file.");
                System.exit(1);
            }

            VersionManifest versionManifest = gson.fromJson(manifest, VersionManifest.class);
            // At this point, fromJson is working perfectly.
            URL releaseJsonUrl = getReleaseJsonUrl(versionManifest);
            // If a null object is returned, the program should display the error and exit.
            if (releaseJsonUrl == null){
                System.out.println("ERROR: URL for release manifest could not be retrieved.");
                System.exit(2);
            }

            System.out.println("Latest release JSON can be found at: \n" + releaseJsonUrl);
            System.out.println("Downloading now...");
            getReleaseJson(releaseJsonUrl);

            // The release info file should now be downloaded and cleaned at json/release-info-clean.json
            // Use GSON to create a ReleaseInfo object and get the server dl URL.

            JsonReader releaseManifest = null;
            try {
                releaseManifest = new JsonReader(new FileReader(new File("json/release-info-clean.json")));
            } catch (FileNotFoundException notFound){
                System.out.println("Path: '" + "json/release-info-clean.json" + "' does not point to a valid JSON file.");
                System.exit(1);
            }

            ReleaseInfo releaseInfo = gson.fromJson(releaseManifest, ReleaseInfo.class);
            URL serverDlUrl = getServerDlUrl(releaseInfo);

            // Write the URL to its own file so that wget can handle the dl.
            try {
                FileWriter fileWriter = new FileWriter("server-latest.url");
                fileWriter.write(serverDlUrl.toString());
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("ERROR: File could not be written. Are you sure you own this directory?");
            }


            System.exit(0);
        } else {
            System.out.println("No version manifest specified!\nUsage: java -jar updater.jar /home/mc_version_manifest.json");
            System.exit(1);
        }
    }

    /**
     * Searches through the release manifest to find the url for the latest stable release JSON file.
     *
     * @param vm The VersionManifest object to read from.
     * @return a String containing the url.
     */
    private static URL getReleaseJsonUrl(VersionManifest vm){
        String urlStr = "";
        String releaseVer = vm.getLatest().getRelease();
        List<Version> versions = vm.getVersions();

        for (Version version : versions){
            String id = version.getId();
            if (id.equals(releaseVer)){
                // We found the correct version. Get the url and exit.
                urlStr = version.getUrl();
                break;
            }
        }
        URL url = null;
        try{
            url = new URL(urlStr);
        } catch (MalformedURLException mfe){
            System.out.println("ERROR: Malformed URL.");
        }
        return url;
    }

    /**
     * Searches through the release info file to find the url that leads to the server software.
     *
     * @param ri ReleaseInfo object filled by GSON that contains the needed info.
     * @return A URL pointing to the latest stable release of the server software.
     */
    private static URL getServerDlUrl(ReleaseInfo ri){
        URL serverDlUrl = null;


        return serverDlUrl;
    }

    /**
     * Retrieves the JSON file for the current release.
     * @param url The URL pointing to the JSON file.
     */
    private static void getReleaseJson(URL url){
        File releaseJson = new File("json/release-info.json");
        URL releaseUrl = url;

        try {
            releaseJson.createNewFile();
        } catch (IOException io){
            System.out.println("ERROR: File could not be written. Are you sure you own this directory?");
            return;
        }
        FileOutputStream jsonFile;
        try {
            jsonFile = new FileOutputStream(releaseJson);
        } catch (FileNotFoundException fnfe){
            System.out.println("release-info.json could not be found.");
            return;
        }

        // Get the file with FileUtils
        try {
            FileUtils.copyURLToFile(releaseUrl, releaseJson);
        } catch (IOException ioe){
            System.out.println("ERROR: File could not be opened for writing.");
            System.exit(1);
        }


        ProcessBuilder cleanJson = new ProcessBuilder("./clean-release-json.sh");
        try {
            Process p = cleanJson.start();
        } catch (IOException e) {
            System.out.println("Command './clean-release-json.sh' not found.");
        }

        System.out.println("\nDownload Successful!");
    }
}