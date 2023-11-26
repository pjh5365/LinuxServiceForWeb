package pjh5365.linuxserviceweb.service;

import pjh5365.linuxserviceweb.domain.log.Log;

public interface ReadLogService {

    Log[] loadLog();

    void copyLog();

    void sendLog();
}
