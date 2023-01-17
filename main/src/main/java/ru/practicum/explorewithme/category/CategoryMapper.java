package ru.practicum.explorewithme.category;

import ru.practicum.explorewithme.category.dto.CategoryDto;
import ru.practicum.explorewithme.category.dto.NewCategoryDto;
import ru.practicum.explorewithme.category.model.Category;

public class CategoryMapper {

    public static CategoryDto toCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto(category.getId(), category.getName());
        return categoryDto;
    }

    public static Category fromCategoryDto(CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        return category;
    }

    public static NewCategoryDto toNewCategoryDto(Category category) {
        NewCategoryDto newCategoryDto = new NewCategoryDto();
        newCategoryDto.setName(category.getName());
        return newCategoryDto;
    }

    public static Category fromNewCategoryDto(NewCategoryDto newCategoryDto) {
        Category category = new Category();
        category.setName(newCategoryDto.getName());
        return category;
    }
}
