package vacancyaggregator.service;

import java.util.Timer;
import java.util.TimerTask;

public class AutoUpdateService {

    private final UpdateService updateService = new UpdateService();
    private Timer timer;

    public void startAutoUpdate(String searchText, int intervalSeconds) {
        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int count = updateService.updateFromAllSources(searchText);
                System.out.println("Auto update completed. Loaded: " + count);
            }
        }, 0, intervalSeconds * 1000L);
    }

    public void stopAutoUpdate() {
        if (timer != null) {
            timer.cancel();
            System.out.println("Auto update stopped");
        }
    }
}