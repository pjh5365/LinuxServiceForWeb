package pjh5365.linuxserviceweb.service;

import org.springframework.stereotype.Service;
import pjh5365.linuxserviceweb.log.Log;
import pjh5365.linuxserviceweb.log.NginxAccessLog;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.StringTokenizer;

@Service
public class ReadNginxAccessLogService implements ReadLogService {

    private final String path = "/Users/parkjihyeok/testLog/";

    private static StringBuilder fileReaderSb;  // 파일을 읽어온 내용을 StringBuilder 에 담아 copyLog 에서 사용하기 위해 static 으로 선언

    @Override
    public NginxAccessLog[] getLog(String fileName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path + fileName));

        fileReaderSb = new StringBuilder();   // 파일의 내용을 담을 빌더

        StringBuilder ipSb = new StringBuilder();   // 접속시도 아이피를 담을 빌더
        StringBuilder timeSb = new StringBuilder(); // 접속시각 정보를 담을 빌더
        StringBuilder httpSb = new StringBuilder(); // http 정보를 담을 빌더
        StringBuilder statusSb = new StringBuilder();   // 응답상태를 담을 빌더
        StringBuilder clientSb = new StringBuilder();   // 클라이언트의 정보를 담을 빌더

        String readLine;
        while((readLine = bufferedReader.readLine()) != null) {   // 파일의 끝까지 읽기
            fileReaderSb.append(readLine).append("\n");
        }
        String[] lines = fileReaderSb.toString().split("\n"); // 파일을 읽은 내용을 배열로 한줄씩 저장

        for(int i = lines.length - 1; i >= 0; i--) {    // 읽은 내용을 뒤에서부터 담아 최신내용을 먼저 보여주게 하기
            StringTokenizer st = new StringTokenizer(lines[i]);
            String[] tokens = new String[st.countTokens()];

            int index = 0;
            while(st.hasMoreTokens()) {
                tokens[index++] = st.nextToken();
            }
            // access log 인덱스별 내용
            // 0 : 접속시도아이피
            // 3,4 : 시각
            // 5,6,7 : http 방법
            // 8 : 상태
            // 9 : 응답크기
            // 11~17 : 클라이언트 정보

            ipSb.append(tokens[0]).append("\n");
            timeSb.append(tokens[3]).append(tokens[4]).append("\n");
            httpSb.append(tokens[5]).append(tokens[6]).append(tokens[7]).append("\n");
            statusSb.append(tokens[8]).append("\n");
            clientSb.append(tokens[11]).append(tokens[12]).append(tokens[13]).append(tokens[14]).append(tokens[15]).append(tokens[16]).append(tokens[17]).append("\n");
        }
        String[] ip = ipSb.toString().split("\n"); // ip 정보를 읽은 내용을 배열로 한줄씩 저장
        String[] time = timeSb.toString().split("\n"); // 시각 정보를 읽은 내용을 배열로 한줄씩 저장
        String[] http = httpSb.toString().split("\n"); // http 정보를 읽은 내용을 배열로 한줄씩 저장
        String[] status = statusSb.toString().split("\n"); // 상태 정보를 읽은 내용을 배열로 한줄씩 저장
        String[] client = clientSb.toString().split("\n"); // 클라이언트 정보를 읽은 내용을 배열로 한줄씩 저장

        NginxAccessLog[] logs = new NginxAccessLog[ip.length];    // 로그의 길이만큼 배열 생성
        for(int i = 0; i < ip.length; i++) {
            logs[i] = new NginxAccessLog(ip[i], time[i], http[i], status[i], client[i]);
        }

        return logs;
    }

    @Override
    public StringBuilder copyLog(String copyPath) throws IOException {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년MM월dd일");
        String fileName = now.format(formatter) + ".log";    // 로그는 현재 날짜를 파일명으로 하여 저장한다.

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path + copyPath + fileName));

        bufferedWriter.write(String.valueOf(fileReaderSb));
        bufferedWriter.flush();

        return fileReaderSb;
    }
}
