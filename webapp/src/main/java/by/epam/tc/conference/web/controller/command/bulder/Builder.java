package by.epam.tc.conference.web.controller.command.bulder;

import by.epam.tc.conference.entity.Identifiable;

import javax.servlet.http.HttpServletRequest;

/**
 * Represents logic to build Identifiable object from request parameters.
 *
 * @param <T> the type of result object
 */

@FunctionalInterface
public interface Builder<T extends Identifiable> {

    /**
     * Builds object from request parameters. If request parameter for property
     * name was not specified.
     * then result property value will be null
     *
     * @param request with specified parameters to build Identifiable object
     * @return a result
     */
    T build(HttpServletRequest request);

}
