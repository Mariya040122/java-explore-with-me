package ru.practicum.explorewithme;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StartDateValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StartDateConstraint {
    String message() default "Invalid start date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
