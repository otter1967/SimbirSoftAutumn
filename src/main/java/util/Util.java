package util;

import app.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

public class Util {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static boolean isValidUrl(URL url) {
        try {
            url.openStream().close();
            return true;
        } catch (Exception e) {
            logger.error("Failed server connection via link: {} cause of {}", url, e.getClass());
            System.err.println("Invalid link. Read more: out/logfile.log");
        }
        return false;
    }

    public static String constructDownloadedFileName(URL webpageUrl) {
        String filename = webpageUrl.toString()
                .replaceAll("https://", "")
                .replaceAll("http://", "")
                .replaceAll("[/\\.]", "-")
                .replaceAll("\\.html", "");
        if (filename.endsWith("-")) {
            filename = filename.substring(0, filename.length() - 1);
        }
        return filename;
    }
}
