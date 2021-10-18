package launcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import fetcher.Fetcher;
import fetcher.WebPageFetcher;
import parser.Parser;
import parser.SaxHtmlParser;
import util.Util;

import java.io.File;
import java.net.URL;

public class ApplicationLauncher implements Launcher {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationLauncher.class);
    private final Fetcher fetcher = new WebPageFetcher();
    private final Parser parser = new SaxHtmlParser();

    @Override
    public void launch(String[] args) {
        try {
            if (args == null || args[0].isEmpty()) {
                throw new Exception();
            }

            URL requestedUrl = new URL(args[0]);

            if (Util.isValidUrl(requestedUrl)) {
                File htmlPage = fetcher.fetchWebpageAndReturnFile(requestedUrl);
                parser.parseWebpageFromFile(htmlPage);
                parser.generateStatistics();
                parser.printStatistics();
            }
        } catch (Exception e) {
            logger.error("Invalid URL: {}", e.getClass());
            System.err.println("Invalid URL. Read more: out/logfile.log");
        }
    }
}
