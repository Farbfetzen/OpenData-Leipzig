package henz.sebastian.opendataleipzig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    StreetService streetService;

    @Autowired
    StreetRepository streetRepository;

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        // This runs when the app initialization has finished.
        if (streetRepository.count() == 0) {
            streetService.updateStreetData();
        }
    }
}
