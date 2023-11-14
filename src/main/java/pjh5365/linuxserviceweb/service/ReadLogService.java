package pjh5365.linuxserviceweb.service;

import pjh5365.linuxserviceweb.log.Log;

import java.io.IOException;

public interface ReadLogService {
    
    Log[] getLog(String fileName) throws IOException;

    StringBuilder copyLog(String copyPath) throws IOException;

    void sendLog() throws IOException;
}
