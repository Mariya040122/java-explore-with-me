package ru.practicum.explorewithme.comment.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class NewCommentDto {
    @NotNull
    @Size(min = 20, max = 7000)
    private String text;
}
