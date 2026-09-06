package com.cohkomapa.imageboard.board.service;

import com.cohkomapa.imageboard.board.dto.post.PostFilter;
import com.cohkomapa.imageboard.board.entity.Post;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public class PostSpecificationBuilder {

    public static Specification<Post> buildWithFilter(PostFilter postFilter) {
        Specification<Post> spec = Specification.unrestricted();
        if (StringUtils.isNotBlank(postFilter.title())) {
            spec = spec.and((root, query, cb) ->
                    cb.like(
                            cb.lower(root.get("title")) ,
                            "%" + postFilter.title().toLowerCase() + "%"
                    )
            );
        }
        if (postFilter.createdFrom() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("createdAt"), postFilter.createdFrom())
            );
        }
        if (postFilter.createdTo() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("createdAt"), postFilter.createdTo())
            );
        }
        return spec;
    }
}