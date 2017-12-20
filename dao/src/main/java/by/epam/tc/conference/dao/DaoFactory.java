package by.epam.tc.conference.dao;

public class DaoFactory {

    private static final DaoFactory INSTANSE = new DaoFactory();

    private DaoFactory() {

    }

    public static DaoFactory getInstanse() {
        return INSTANSE;
    }

}
