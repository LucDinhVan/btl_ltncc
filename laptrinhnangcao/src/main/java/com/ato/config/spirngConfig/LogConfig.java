package com.ato.config.spirngConfig;

import com.ato.service.businessSerivce.LogService;

import java.util.ArrayList;

public class LogConfig extends ThreadConfig {
    @Override
    public void doProcess(ArrayList items) {
        LogService logService = new LogService();
        logService.setItems(items);
        executorService.submit(logService);
    }
}