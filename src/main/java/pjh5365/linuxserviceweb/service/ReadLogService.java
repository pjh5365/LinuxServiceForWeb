package pjh5365.linuxserviceweb.service;

import pjh5365.linuxserviceweb.log.Log;

import java.io.IOException;

public interface ReadLogService {
    
    Log[] getLog(String fileName) throws IOException;
}
