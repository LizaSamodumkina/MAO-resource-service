package org.samodumkina.service;

import java.io.IOException;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tika.metadata.Metadata;
import org.samodumkina.dao.ResourceRepository;
import org.samodumkina.dao.entity.ResourceEntity;
import org.samodumkina.dto.ResourceCreateResponseDTO;
import org.samodumkina.dto.ResourceDeleteResponseDTO;
import org.samodumkina.exception.NotFoundException;
import org.samodumkina.exception.ValidationException;
import org.samodumkina.integration.SongServiceCaller;
import org.samodumkina.service.metadata.MetadataManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
public class ResourceService {

  private final ResourceRepository resourceRepository;
  private final MetadataManager metadataManager;
  private final SongServiceCaller songServiceCaller;

  public ResourceService(ResourceRepository resourceRepository, MetadataManager metadataManager,
      SongServiceCaller songServiceCaller) {
    this.resourceRepository = resourceRepository;
    this.metadataManager = metadataManager;
    this.songServiceCaller = songServiceCaller;
  }

  public byte[] getResource(Integer resourceId) {
    ResourceEntity resourceEntity = resourceRepository.findById(resourceId)
        .orElseThrow(() -> new NotFoundException("No resource with id='%d' was found.".formatted(resourceId)));

    return resourceEntity.getFile();
  }

  @Transactional
  public ResourceCreateResponseDTO createResource(MultipartFile file) {
    ResourceEntity createdResource;

    try {
      byte[] fileBytes = file.getBytes();

      String checksum = DigestUtils.sha256Hex(fileBytes);
      resourceRepository.findByChecksum(checksum).ifPresent(resourceEntity -> {
        throw new ValidationException("File was already uploaded");
      });

      ResourceEntity resourceEntity = new ResourceEntity(fileBytes, checksum);
      createdResource = resourceRepository.save(resourceEntity);

      Metadata metadata = metadataManager.getMetadata(file.getInputStream());
      songServiceCaller.sendSongMetadata(metadata, createdResource.getId());

    } catch (IOException e) {
      throw new ValidationException("Invalid PDF was passed");
    }

    return new ResourceCreateResponseDTO(createdResource.getId());
  }

  @Transactional
  public ResourceDeleteResponseDTO deleteResources(List<Integer> ids) {
    List<ResourceEntity> resourceEntities = resourceRepository.deleteByIdIn(ids);
    return new ResourceDeleteResponseDTO(resourceEntities.stream().map(ResourceEntity::getId).toList());
  }

}
