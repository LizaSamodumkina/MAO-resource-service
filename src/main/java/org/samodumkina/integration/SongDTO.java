package org.samodumkina.integration;

//TODO should be migrated to separated repository as shared model
public record SongDTO(String name, String artist, String album, String length, Integer resourceId, Integer year) {

}
