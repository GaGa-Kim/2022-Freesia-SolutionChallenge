package com.freesia.imyourfreesia.domain.community;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    WORRIES("worries"),
    REVIEW("review"),
    GATHERING("gathering");

    private static final Map<String, Category> CATEGORY_MAP = new HashMap<>();

    static {
        for (Category category : Category.values()) {
            CATEGORY_MAP.put(category.getCategoryName(), category);
        }
    }

    private final String categoryName;

    public static Category findByCategoryName(String category) {
        Category foundCategory = CATEGORY_MAP.get(category);
        if (foundCategory == null) {
            throw new RuntimeException();
        }
        return foundCategory;
    }
}
