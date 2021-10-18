package parser;

import java.io.File;

public interface Parser {
    void parseWebpageFromFile(File file);
    void generateStatistics();
    void printStatistics();
}
