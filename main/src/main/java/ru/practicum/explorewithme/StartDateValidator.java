package ru.practicum.explorewithme;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class StartDateValidator implements
        ConstraintValidator<StartDateConstraint, LocalDateTime> {
    @Override
    public void initialize(StartDateConstraint startDate) {
    }

    @Override
    public boolean isValid(LocalDateTime startDate,
                           ConstraintValidatorContext cxt) {
        if (startDate == null) return true;
        return startDate.isAfter(LocalDateTime.now().plusHours(2L));
    }
}
