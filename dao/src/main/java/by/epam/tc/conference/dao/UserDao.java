package by.epam.tc.conference.dao;

import by.epam.tc.conference.entity.User;

import java.util.Optional;

/**
 * UserDao performs persistence operations with User
 */
public interface UserDao extends GenericDao<User> {

    /**
     * Finds user by given username and returns optional user when found or empty optional otherwise
     * @param username
     * @return Optional user if was found or empty optional otherwise
     * @throws DaoException when error during data access occurs
     */
    Optional<User> findByUsername(String username) throws DaoException;

    /**
     * Finds Administrator of conference for specified proposal
     * @param proposalId proposal identifier
     * @return Optional user if was found or empty optional otherwise
     * @throws DaoException when error during data access occurs
     */
    Optional<User> findAdministratorByProposalId(long proposalId) throws DaoException;

    /**
     * finds Administrator of conference by section id
     * @param sectionId section identifier
     * @return Optional user if was found or empty optional otherwise
     * @throws DaoException when error during data access occurs
     */
    Optional<User> findAdministratorBySectionId(long sectionId) throws DaoException;

    /**
     * Finds Administrator related to question with specified id
     * @param questionId question identifier
     * @return Optional user if was found or empty optional otherwise
     * @throws DaoException when error during data access occurs
     */
    Optional<User> findAdministratorByQuestionId(long questionId) throws DaoException;
}
