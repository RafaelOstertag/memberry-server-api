package ch.guengel.memberberry.model;

import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class BerryAuditTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void shouldNotFailOnValidBerryAudit() {
        var berryAudit = createValidBerryAudit();
        var constraintViolations = validator.validate(berryAudit);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
    void shouldFailOnEmptyBerryAudit() {
        var berryAudit = new BerryAudit();

        var constraintViolations = validator.validate(berryAudit);
        var constraintViolationStrings = ConstraintViolationUtils.toStringList(constraintViolations);
        assertThat(constraintViolationStrings)
                .containsExactlyInAnyOrder("created:NotNull");
    }

    private BerryAudit createValidBerryAudit() {
        var berryAudit = new BerryAudit();
        berryAudit.created(OffsetDateTime.now());
        berryAudit.updated(null);
        return berryAudit;
    }
}
