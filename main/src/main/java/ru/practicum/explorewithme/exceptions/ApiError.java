package ru.practicum.explorewithme.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static ru.practicum.explorewithme.Constants.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiError {

    private String[] errors;
    private String path;
    private String message;
    private String reason;
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DT_PATTERN)
    private LocalDateTime timestamp;


}
