package org.samodumkina.service.metadata;

import java.io.IOException;
import java.io.InputStream;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.samodumkina.exception.MetadataParsingException;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

@Component
public class MetadataManager {

  public Metadata getMetadata(InputStream stream) {
    Metadata metadata = new Metadata();
    try {
      BodyContentHandler handler = new BodyContentHandler();
      ParseContext parseContext = new ParseContext();

      Mp3Parser mp3Parser = new Mp3Parser();
      mp3Parser.parse(stream, handler, metadata, parseContext);
    } catch (TikaException | IOException | SAXException e) {
      throw new MetadataParsingException("Some problems occurred while reading metadata of the song.");
    }
    return metadata;
  }
}
