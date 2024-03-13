package com.freesia.imyourfreesia.domain.community;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.freesia.imyourfreesia.except.UnexpectedValueException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CategoryTest {
    @Test
    @DisplayName("올바른 카테고리 열거형 값 테스트")
    void testFindByValidCategoryName() {
        String validCategory = Category.WORRIES.getCategoryName();

        Category category = Category.findByCategoryName(validCategory);

        assertNotNull(category);
        assertEquals(Category.WORRIES, category);
    }

    @Test
    @DisplayName("올바르지 않은 카테고리 열거형 값 테스트")
    void testFindByInvalidCategoryName() {
        String invalidCategory = "news";

        assertThatThrownBy(() -> Category.findByCategoryName(invalidCategory))
                .isInstanceOf(UnexpectedValueException.class);
    }
}