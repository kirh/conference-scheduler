package by.epam.tc.conference.web.controller.command;

public abstract class CommandFactory {

    private static final CommandFactory FACTORY = new CommandFactoryImpl();

    public static CommandFactory getInstance() {
        return FACTORY;
    }

    public abstract Command getCommand(String path);
}
