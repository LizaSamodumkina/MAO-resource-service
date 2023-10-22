package org.samodumkina.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("application")
public record ApplicationProperties(String apiSchema, String metadataPath, String gatewayName, String songServiceName) {

}
