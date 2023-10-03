package org.samodumkina.controller;

import java.util.List;
import org.samodumkina.dto.ResourceCreateResponseDTO;
import org.samodumkina.dto.ResourceDeleteResponseDTO;
import org.samodumkina.exception.ValidationException;
import org.samodumkina.service.ResourceService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/resources", produces = MediaType.APPLICATION_JSON_VALUE)
public class ResourceController {

  private static final String AUDION_CONTENT_TYPE = "audio/mpeg";

  private final ResourceService resourceService;

  public ResourceController(ResourceService resourceService) {
    this.resourceService = resourceService;
  }

  @GetMapping(value = "/{id}")
  public byte[] getResource(@PathVariable Integer id) {
    return resourceService.getResource(id);
  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResourceCreateResponseDTO createResource(@RequestPart MultipartFile file) {

    String actualFileContentType = file.getContentType();
    if (!AUDION_CONTENT_TYPE.equals(actualFileContentType)) {
      throw new ValidationException("File content type is different from '%s'".formatted(AUDION_CONTENT_TYPE));
    }

    return resourceService.createResource(file);
  }

  @DeleteMapping
  public ResourceDeleteResponseDTO deleteResource(@RequestParam List<Integer> ids) {
    return resourceService.deleteResources(ids);
  }
}
