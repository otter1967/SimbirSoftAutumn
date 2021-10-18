package fetcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class WebPageFetcher implements Fetcher {
    private static final int BUFFER_READER_SIZE_IN_BYTES = 16 * 1024;
    private static final Logger logger = LoggerFactory.getLogger(WebPageFetcher.class);

    public File fetchWebpageAndReturnFile(URL requestedUrl) {
        String downloadedFileName = Util.constructDownloadedFileName(requestedUrl);
        File result = new File("out" + File.separatorChar + downloadedFileName + ".html");

        try (BufferedInputStream reader = new BufferedInputStream(requestedUrl.openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(result)) {
            byte[] dataBuffer = new byte[BUFFER_READER_SIZE_IN_BYTES];
            int bytesRead;
            while ((bytesRead = reader.read(dataBuffer, 0, BUFFER_READER_SIZE_IN_BYTES)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            fileOutputStream.flush();
        } catch (MalformedURLException e) {
            logger.error("Failed server connection via link: {} cause of {}", requestedUrl, e.getClass());
            System.err.println("Invalid link. Read more: out/logfile.log");
        } catch (IOException e) {
            logger.error("Unrecognized IO error {}", e.getClass());
            System.err.println("Unrecognized IO error. Read more: out/logfile.log");
        }

        return result;
    }
}
