package ch.guengel.memberberry.model;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

final public class ConstraintViolationUtils {
    private ConstraintViolationUtils() {
        // intentionally empty
    }

    static <T> List<String> toStringList(Set<ConstraintViolation<T>> constraintViolations) {
        return constraintViolations.stream().map(constraintViolation -> {
            return constraintViolation.getPropertyPath().toString() + ":" + constraintViolation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();
        }).collect(Collectors.toList());
    }


}
