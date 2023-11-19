package pjh5365.linuxserviceweb.service;

import pjh5365.linuxserviceweb.domain.log.Log;

public interface ReadLogService {

    Log[] getLog();

    void copyLog();

    void sendLog();
}
