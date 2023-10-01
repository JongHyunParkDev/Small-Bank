package com.dev.was.scheduleTask;

import com.dev.was.service.DataGoService;
import com.dev.was.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;


@Component
@RequiredArgsConstructor
public class DataGoScheduleTask {
    private final DataGoService dataGoService;

    @Scheduled(cron = "${sppd.schedule.weather}")
    public void weatherSchedule() {
        String date = LocalDate.now().format(CommonUtil.LOCAL_DATE_FORMATTER);
        String time = LocalTime.now().withMinute(0).format(CommonUtil.LOCAL_TIME_FORMATTER2);

        logger.info("start weatherSchedule {} - {}", date, time);

        dataGoService.refreshWeatherListByStation(date, time, null, null);

        logger.info("end weatherSchedule");
    }

    private static final Logger logger = LoggerFactory.getLogger(DataGoScheduleTask.class);
}
