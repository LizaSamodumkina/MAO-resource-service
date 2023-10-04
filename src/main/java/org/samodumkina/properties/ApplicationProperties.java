package org.samodumkina.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("application")
public class ApplicationProperties {

  private String apiSchema;
  private String host;
  private String port;
  private String metadataPath;

  public String getApiSchema() {
    return apiSchema;
  }

  public void setApiSchema(String apiSchema) {
    this.apiSchema = apiSchema;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getPort() {
    return port;
  }

  public void setPort(String port) {
    this.port = port;
  }

  public String getMetadataPath() {
    return metadataPath;
  }

  public void setMetadataPath(String metadataPath) {
    this.metadataPath = metadataPath;
  }
}
