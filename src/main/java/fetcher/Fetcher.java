package fetcher;

import java.io.File;
import java.net.URL;

public interface Fetcher {
    File fetchWebpageAndReturnFile(URL requestedUrl);
}

