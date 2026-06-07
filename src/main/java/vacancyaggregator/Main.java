package vacancyaggregator;

import vacancyaggregator.database.DatabaseInitializer;
import vacancyaggregator.ui.ConsoleMenu;

public class Main {

    public static void main(String[] args) {
        DatabaseInitializer.initialize();
        ConsoleMenu menu = new ConsoleMenu();
        menu.start();
    }
}