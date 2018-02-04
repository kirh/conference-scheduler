package by.epam.tc.conference.services.transaction;

import java.lang.annotation.*;

/**
 * Indicates that method should be invoked in transaction context.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Transactional {
}
