package by.epam.tc.conference.web.controller.command;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum CommandEnum {

    LOGIN("login"),
    REGISTER("register"),
    ADMIN_DASHBOARD("admin-dashboard"),
    PARTICIPANT_DASHBOARD("user-dashboard"),
    CONFERENCE("conference"),
    SECTION("section"),
    PROPOSAL("proposal"),
    MESSAGE("message"),
    QUESTION("question"),
    LOGOUT("logout"),
    CHANGE_LOCALE("change-locale"),
    UNKNOWN("");

    private final String commandLine;

    CommandEnum(String commandLine) {
        this.commandLine = commandLine;
    }

    public static CommandEnum getCommand(String commandLine) {
        CommandEnum command = commandsMap.get(commandLine);
        return command != null ? command : UNKNOWN;
    }

    private static final Map<String, CommandEnum> commandsMap = Collections.unmodifiableMap(createMap());

    private static Map<String, CommandEnum> createMap() {
        Map<String, CommandEnum> map = new HashMap<>();
        for (CommandEnum command : CommandEnum.values()) {
            map.put(command.commandLine, command);
        }
        return map;
    }
}
