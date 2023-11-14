package pjh5365.linuxserviceweb.service;

import pjh5365.linuxserviceweb.log.NginxAccessLog;

public interface ReadLogService {

    NginxAccessLog[] getLog();

    void copyLog();

    void sendLog();
}
