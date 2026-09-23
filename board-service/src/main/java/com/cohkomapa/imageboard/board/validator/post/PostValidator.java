package com.cohkomapa.imageboard.board.validator.post;

import com.cohkomapa.imageboard.board.exception.InvalidRequestException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class PostValidator {

    private static final Set<String> ALLOWED_POST_SORT_FIELDS = Set.of(
            "createdAt", "title"
    );

    public void validateSort(Pageable pageable) {
        pageable.getSort().forEach(order -> {
            if (!ALLOWED_POST_SORT_FIELDS.contains(order.getProperty())) {
                throw new InvalidRequestException(
                        "Sorting by field " + order.getProperty() + " is not allowed"
                );
            }
        });
    }
}