package parser;

import org.apache.xerces.xni.parser.XMLDocumentFilter;
import org.codelibs.nekohtml.filters.ElementRemover;
import org.codelibs.nekohtml.parsers.SAXParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SaxHtmlParser implements Parser {
    private static final Logger logger = LoggerFactory.getLogger(SaxHtmlParser.class);
    private final Map<String, Integer> statistics;
    private final StringBuffer contentBuffer = new StringBuffer();

    public SaxHtmlParser() {
        statistics = new TreeMap<>();
    }

    public void parseWebpageFromFile(File file) {
        try {
            ElementRemover remover = new ElementRemover();
            remover.removeElement("script");
            remover.removeElement("head");
            remover.removeElement("style");

            XMLDocumentFilter[] filters = {remover};
            SAXParser saxParser = new SAXParser();
            saxParser.setFeature("http://cyberneko.org/html/features/balance-tags/document-fragment", true);
            saxParser.setProperty("http://cyberneko.org/html/properties/filters", filters);
            saxParser.setContentHandler(new ParserContentHandler());
            saxParser.parse(new InputSource(new FileInputStream(file)));
        } catch (SAXException saxException) {
            logger.error("Parser settings error {}", saxException.getException().getClass());
        } catch (FileNotFoundException fileNotFoundException) {
            logger.error("File {} not found.", file.toPath().toAbsolutePath().toString());
        } catch (IOException ioException) {
            logger.error("Unrecognized parser's error.");
        }
    }

    public void printStatistics() {
        for (Map.Entry<String, Integer> entry : statistics.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }

    public void generateStatistics() {
        Pattern pattern = Pattern.compile("[\\w-]+", Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(contentBuffer.toString());

        while (matcher.find()) {
            String word = matcher.group().toUpperCase();
            if (isValid(word)) {
                statistics.merge(word, 1, Integer::sum);
            }
        }
    }

    private boolean isValid(String word) {
        return !word.isEmpty() && !word.equals("-") && !word.chars().allMatch(Character::isDigit);
    }

    private class ParserContentHandler implements ContentHandler {
        public void characters(char[] ch, int start, int length) {
            contentBuffer.append(ch, start, length);
        }

        @Override
        public void setDocumentLocator(Locator locator) {
            // Specific processing is not required
        }

        @Override
        public void startDocument() throws SAXException {
            // Specific processing is not required
        }

        @Override
        public void endDocument() throws SAXException {
            // Specific processing is not required
        }

        @Override
        public void startPrefixMapping(String prefix, String uri) throws SAXException {
            // Specific processing is not required
        }

        @Override
        public void endPrefixMapping(String prefix) throws SAXException {
            // Specific processing is not required
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
            // Specific processing is not required
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            // Specific processing is not required
        }

        @Override
        public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
            // Specific processing is not required
        }

        @Override
        public void processingInstruction(String target, String data) throws SAXException {
            // Specific processing is not required
        }

        @Override
        public void skippedEntity(String name) throws SAXException {
            // Specific processing is not required
        }
    }
}
