package pjh5365.linuxserviceweb.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadLogService {

    private final String path;
    public ReadLogService(String path) {
        this.path = path;
    }

    public String getLog(String fileName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path + fileName));
        String line = bufferedReader.readLine();
        return line;
    }
}
