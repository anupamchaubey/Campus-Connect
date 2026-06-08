package com.campus.Campus.Connect.repository;

import com.campus.Campus.Connect.entity.Resource;
import com.campus.Campus.Connect.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Long>, JpaSpecificationExecutor<Resource> {
    List<Resource> findByUploader(User uploader);
    @Override
    @EntityGraph(attributePaths = {"uploader"})
    Page<Resource> findAll(Specification<Resource> spec, Pageable pageable);
}
