package org.samodumkina.integration;

import org.apache.commons.lang3.StringUtils;
import org.apache.tika.metadata.Metadata;
import org.samodumkina.properties.ApplicationProperties;
import org.samodumkina.service.metadata.MetadataField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SongServiceCaller {

  private static final Logger LOGGER = LoggerFactory.getLogger(SongServiceCaller.class);

  private final ApplicationProperties applicationProperties;

  public SongServiceCaller(ApplicationProperties applicationProperties) {
    this.applicationProperties = applicationProperties;
  }

  public void sendSongMetadata(Metadata metadata, Integer resourceId) {
    String uri = "%s://%s:%s%s".formatted(applicationProperties.getApiSchema(), applicationProperties.getHost(),
        applicationProperties.getPort(), applicationProperties.getMetadataPath());
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<Object> responseEntity = restTemplate
        .postForEntity(uri, mapMetadata(metadata, resourceId), Object.class);

    LOGGER.info("Response {} was received", responseEntity.getBody());
  }

  private SongDTO mapMetadata(Metadata metadata, Integer resourceId) {
    String title = metadata.get(MetadataField.TITLE);
    String artist = metadata.get(MetadataField.ARTIST);
    String album = metadata.get(MetadataField.ALBUM);
    String duration = metadata.get(MetadataField.DURATION);
    String year = metadata.get(MetadataField.RELEASE_DATE);

    return new SongDTO(title, artist, album, mapLength(duration), resourceId, Integer.parseInt(year));
  }

  private String mapLength(String duration) {
    if (StringUtils.isBlank(duration)) {
      return duration;
    }

    int value = Double.valueOf(duration).intValue();
    int seconds = value % 60;
    int minutes = (value % 3600) / 60;

    return "%02d:%02d".formatted(minutes, seconds);
  }
}
