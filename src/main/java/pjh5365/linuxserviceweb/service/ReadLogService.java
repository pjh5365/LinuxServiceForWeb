package pjh5365.linuxserviceweb.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Service
public class ReadLogService {

    private final String path = "/Users/parkjihyeok/serviceTest/";

    public StringBuilder getLog(String fileName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path + fileName));
        StringBuilder sb = new StringBuilder();
        String readLine;
        while((readLine = bufferedReader.readLine()) != null) {   // 파일의 끝까지 읽기
            sb.append(readLine).append("\n");
        }
        return sb;
    }
}
