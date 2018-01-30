package by.epam.tc.conference.services.transaction;

import by.epam.tc.conference.dao.DaoException;
import by.epam.tc.conference.dao.DaoFactory;
import by.epam.tc.conference.dao.TransactionManager;
import by.epam.tc.conference.services.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class TransactionAspect {

    private static final Logger logger = LogManager.getLogger(TransactionAspect.class);

    @Around("@annotation(Transactional)")
    public Object manageTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        DaoFactory daoFactory = DaoFactory.getInstance();
        TransactionManager transactionManager = daoFactory.getTransactionManager();
        try {
            logger.debug("Starting transaction");
            transactionManager.startTransaction();
            Object result = joinPoint.proceed();
            transactionManager.commit();
            logger.debug("Transaction finished");
            return result;
        } catch (Throwable e) {
            try {
                logger.debug("Transaction rollback");
                transactionManager.rollback();
            } catch (DaoException rollbackException) {
                logger.debug("Error during transaction", e);
                throw new ServiceException(rollbackException.getMessage(), rollbackException);
            }
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
