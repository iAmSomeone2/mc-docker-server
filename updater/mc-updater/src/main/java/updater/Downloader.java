package updater;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;

// TODO: Write success of download to temporary file.

public class Downloader extends Thread {
    private double acceptedDeviation = 0.02; // This is a percentage (i.e. 0.02 is 2%)
    private URL downloadUrl;
    private File serverJar;
    private long expectedSize;
    private String expectedSHA;

    public Downloader(URL downloadUrl, File serverJar, long expectedSize, String expectedSHA){
        this.downloadUrl = downloadUrl;
        this.serverJar = serverJar;
        this.expectedSize = expectedSize;
        this.expectedSHA = expectedSHA;
    }

    public void run(){
        System.out.println("\nStarting server download in background thread...");
        try {
            System.out.println("\nDownloading latest Minecraft Java server...");
            FileUtils.copyURLToFile(downloadUrl, serverJar, 20000, 10000);
        } catch (IOException e){
            System.out.println("\nServer download failed!");
            System.out.println("ERROR: " + e.toString());
        }
        System.out.println("\nServer download Successful!\nChecking file integrity...");

        // First check that the file sizes match, then compare the checksums
        long actualSize = FileUtils.sizeOf(serverJar);
        double quot = 0.0000d;
        // TODO: compare the file sizes by determining the deviation.
    }

    /**
     * Returns the result of comparing the expected and actual SHA-1 checksums.
     *
     * @param expected The expected SHA-1 checksum value.
     * @param actual The computed SHA-1 checksum value.
     * @return 'true' if the checksums are equal, and 'false' if otherwise.
     */
    private boolean checkSHA(String expected, String actual){
        return expected.equals(actual);
    }

    /**
     * Computes a checksum for the provided file.
     * @param digest Indicator for which algorithm to use.
     * @param file File to generate checksum for.
     * @return String containing the calculated checksum.
     * @throws IOException
     */
    private static String getFileChecksum(MessageDigest digest, File file) throws IOException
    {
        //Get file input stream for reading the file content
        FileInputStream fis = new FileInputStream(file);

        //Create byte array to read data in chunks
        byte[] byteArray = new byte[1024];
        int bytesCount = 0;

        //Read file data and update in message digest
        while ((bytesCount = fis.read(byteArray)) != -1) {
            digest.update(byteArray, 0, bytesCount);
        };

        //close the stream; We don't need it now.
        fis.close();

        //Get the hash's bytes
        byte[] bytes = digest.digest();

        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        //return complete hash
        return sb.toString();
    }
}
