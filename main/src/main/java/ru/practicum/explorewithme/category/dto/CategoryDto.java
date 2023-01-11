package ru.practicum.explorewithme.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
public class CategoryDto {

    @Positive
    @NotNull
    Long id; //индивидуальный номер категории
    @NotNull
    String name; //название категории
}

