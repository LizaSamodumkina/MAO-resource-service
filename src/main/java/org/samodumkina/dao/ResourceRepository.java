package org.samodumkina.dao;

import java.util.List;
import java.util.Optional;
import org.samodumkina.dao.entity.ResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<ResourceEntity, Integer> {

  Optional<ResourceEntity> findByChecksum(String checksum);

  List<ResourceEntity> deleteByIdIn(List<Integer> ids);

}
