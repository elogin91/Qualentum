package command;


import cmd.Menu;

public class MenuCommand implements Command {
    private final Command[] commands;
    private final String description;

    public MenuCommand(String description, Command... commands) {
        this.description = description;
        this.commands = commands;
    }

    @Override
    public String description() {
        return this.description;
    }

    @Override
    public void run() {
        Menu menu = new Menu(commands);
        menu.runMenu();
    }

}
