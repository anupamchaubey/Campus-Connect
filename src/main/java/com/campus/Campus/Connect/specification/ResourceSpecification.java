package com.campus.Campus.Connect.specification;

import com.campus.Campus.Connect.dto.ResourceFilterDTO;
import com.campus.Campus.Connect.entity.Resource;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class ResourceSpecification {

    public static Specification<Resource> filterResources(ResourceFilterDTO filter) {
        return (root, query, cb) ->{
            List<Predicate> predicates = new ArrayList<>();

            if(filter.getResourceType() != null) {
                predicates.add(cb.equal(root.get("resourceType"), filter.getResourceType()));
            }
            if(filter.getBranch()!=null && !filter.getBranch().isBlank()){
                predicates.add(cb.equal(
                        cb.lower(root.get("branch")), filter.getBranch().toLowerCase()
                ));
            }
            if(filter.getCollege()!=null && !filter.getCollege().isBlank()){
                predicates.add(cb.equal(
                        cb.lower(root.get("college")), filter.getCollege().toLowerCase()
                ));
            }
            if(filter.getSemester()!=null && !filter.getSemester().isBlank()){
                predicates.add(cb.equal(
                        cb.lower(root.get("semester")), filter.getSemester().toLowerCase()
                ));
            }
            if(filter.getSubject()!=null && !filter.getSubject().isBlank()){
                predicates.add(cb.equal(
                        cb.lower(root.get("subject")), filter.getSubject().toLowerCase()                ));
            }
            if(filter.getKeyword() != null &&
                    !filter.getKeyword().isBlank()) {

                Predicate titlePredicate =
                        cb.like(
                                cb.lower(root.get("title")),
                                "%" + filter.getKeyword().toLowerCase() + "%"
                        );

                Predicate descriptionPredicate =
                        cb.like(
                                cb.lower(root.get("description")),
                                "%" + filter.getKeyword().toLowerCase() + "%"
                        );

                predicates.add(
                        cb.or(titlePredicate, descriptionPredicate)
                );
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
