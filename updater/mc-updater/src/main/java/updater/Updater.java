package updater;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.List;

// TODO: write current release value to release.json
// TODO: Compare currently downloaded version with latest available version before downloading files after VersionManifest

/**
 * The Updater class is the main class for this program. It handles checking for the latest Minecraft version manifests,
 * downloads them, and downloads the server software.
 */
public class Updater{
    public static void main(String[] args){
        // TODO: Update this to download the mc version manifest.
        // I want to simplify execution by limiting the number of files that need to run.

        if (args.length == 1) {
            //arg[0] will be used for naming the manifest file

            //Read version manifest into memory and convert into VersionManifest object.
            URL manifestUrl;
            InputStream jsonStream = null;
            BufferedReader br;
            String line;
            StringBuilder manifestStr = new StringBuilder();

            try {
                System.out.println("Downloading latest Minecraft version manifest.");
                manifestUrl = new URL("https://launchermeta.mojang.com/mc/game/version_manifest.json");
                jsonStream = manifestUrl.openStream();  // throws an IOException
                br = new BufferedReader(new InputStreamReader(jsonStream));

                while ((line = br.readLine()) != null) {
                    manifestStr.append(line);
                }
                System.out.println("\nVersion manifest download: SUCCESS");
            } catch (MalformedURLException mue) {
                System.out.println("ERROR: Bad URL for the version manifest.");
                System.out.println("\nVersion manifest download: FAIL");
                mue.printStackTrace();
            } catch (IOException ioe) {
                System.out.println("ERROR: Stream could not be created.");
                System.out.println("\nVersion manifest download: FAIL");
                ioe.printStackTrace();
            } finally {
                try {
                    if (jsonStream != null) jsonStream.close();
                } catch (IOException ioe) {
                    // nothing to see here
                }
            }

            Gson gson = new Gson();

            // Read the manifest string into a VersionManifest object.
            VersionManifest versionManifest = gson.fromJson(manifestStr.toString(), VersionManifest.class);


            // Write the JSON buffer to its own file so it can be retrieved later if needed.
            writeFile(args[0], gson.toJson(versionManifest));


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


            if (serverDlUrl == null){
                System.out.println("ERROR: Server download URL could not be grabbed.");
                System.exit(2);
            }

            // Write the URL to its own file so it can be retrieved later, if necessary.
            writeFile("server-latest.url", serverDlUrl.toString());

            //Download file with wget. Why reinvent the wheel here?
            downloadServer(serverDlUrl, "server/minecraft_server.jar", releaseInfo);

            System.exit(0);
        } else {
            System.out.println("No version manifest specified!\nUsage: java -jar updater.jar /home/mc_version_manifest.json");
            System.exit(1);
        }
    }

    private static boolean downloadServer(URL downloadUrl, String fileLocation, ReleaseInfo ri){
        boolean success = false;

        File serverJar = new File(fileLocation);
        String expectedSHA = getServerSHA1(ri);
        long expectedSize = getServerDlSize(ri);

        // Set up a downloader thread, and set up progress indicator.
        Downloader downloader = new Downloader(downloadUrl, serverJar, expectedSize, expectedSHA);
        downloader.start();

        return success;
    }

    /**
     * Writes the provided string to the specified file.
     * @param fileName A string containing the relative path for the file to write.
     * @param contents The string contents to write to the file.
     * @return true on success. false, otherwise.
     */
    private static boolean writeFile(String fileName, String contents){
        boolean writeSuccess = false;

        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(contents);
            fileWriter.close();
            System.out.println("\nContents successfully written to " + fileName);
            writeSuccess = true;
        } catch (IOException e) {
            System.out.println("ERROR: File could not be written. Are you sure you own this directory? " + fileName);
        }

        return writeSuccess;
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

        Downloads downloads = ri.getDownloads();
        //Get the server download url through the Downloads object.
        Server server = downloads.getServer();
        String serverUrlStr = server.getUrl();

        try{
            serverDlUrl = new URL(serverUrlStr);
        } catch (MalformedURLException e){
            System.out.println("ERROR: '" + serverUrlStr + "' is a malformed URL.");
        }

        return serverDlUrl;
    }

    /**
     * Gets the download size for the server software.
     *
     * @param ri ReleaseInfo object filled by GSON that contains the needed info.
     * @return A long containing the size of the download in bytes.
     */
    private static long getServerDlSize(ReleaseInfo ri){
        int size;

        Downloads downloads = ri.getDownloads();
        //Get size from the Downloads object
        Server server = downloads.getServer();
        size = server.getSize();

        return size;
    }

    /**
     * Gets the server dl SHA1 checksum from the manifest.
     *
     * @param ri ReleaseInfo object filled by GSON that contains the needed info.
     * @return A String containing the SHA1 checksum of the server file.
     */
    private static String getServerSHA1(ReleaseInfo ri){
        StringBuilder shaStr = new StringBuilder();

        Downloads downloads = ri.getDownloads();
        //Get SHA1 from the Downloads object
        Server server = downloads.getServer();
        shaStr.append(server.getSize());

        return shaStr.toString();
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
            FileUtils.copyURLToFile(releaseUrl, releaseJson, 10000, 10000);
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