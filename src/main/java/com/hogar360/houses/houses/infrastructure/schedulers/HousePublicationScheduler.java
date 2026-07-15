package com.hogar360.houses.houses.infrastructure.schedulers;

import com.hogar360.houses.commons.configurations.utils.LogMessages;
import com.hogar360.houses.houses.domain.model.HouseModel;
import com.hogar360.houses.houses.domain.ports.out.HousePersistencePort;
import com.hogar360.houses.houses.domain.utils.PublicationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

@Component
public class HousePublicationScheduler {

    private static final Logger logger = LoggerFactory.getLogger(HousePublicationScheduler.class);

    private final HousePersistencePort housePersistencePort;

    public HousePublicationScheduler(HousePersistencePort housePersistencePort) {
        this.housePersistencePort = housePersistencePort;
    }

    @PostConstruct
    public void publishOnStartup() {
        logger.info(LogMessages.CHECK_PENDING_PUBLICATIONS_ON_STARTUP);
        publishPendingHouses();
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void publishHousesScheduled() {
        logger.info(LogMessages.CHECK_PENDING_PUBLICATIONS_SCHEDULED);
        publishPendingHouses();
    }

    private void publishPendingHouses() {
        List<HouseModel> housesToPublish = housePersistencePort.findAllPendingToPublish(LocalDate.now());

        if (housesToPublish.isEmpty()) {
            logger.info(LogMessages.NO_HOUSES_PENDING_TO_PUBLISH);
            return;
        }

        for (HouseModel house : housesToPublish) {
            house.setStatus(PublicationStatus.PUBLISHED);
            housePersistencePort.save(house);
        }

        logger.info(LogMessages.HOUSES_UPDATED_TO_PUBLISHED, housesToPublish.size());
    }
}
