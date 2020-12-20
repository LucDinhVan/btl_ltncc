package com.ato.service.businessSerivce;

import com.ato.config.spirngConfig.ApplicationContextHolder;
import com.ato.dao.LogsDAO;
import com.ato.model.baseModel.Logs;
import com.ato.model.baseModel.Task;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Log4j2
public class LogService extends Task {
    @Autowired

    LogsDAO logsDAO = ApplicationContextHolder.getContext().getBean(LogsDAO.class);

    @Override
    public Integer call() throws Exception {
        List lstLog = getItems();
        try {
            if (lstLog != null && !lstLog.isEmpty()) {
                save(lstLog);
            }
        } catch (Exception e) {
            log.error(e.toString(), e);
            return 0;
        }
        return 1;
    }

    public void save(List<Logs> lst) {
        try {
            for (Logs logs : lst) {
                logsDAO.insert(logs);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        } finally {
//           // dong ket noi
        }
    }
}