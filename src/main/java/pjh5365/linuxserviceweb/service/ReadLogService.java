package pjh5365.linuxserviceweb.service;

import pjh5365.linuxserviceweb.domain.log.NginxAccessLog;

public interface ReadLogService {

    NginxAccessLog[] getLog();

    void copyLog();

    void sendLog();
}
