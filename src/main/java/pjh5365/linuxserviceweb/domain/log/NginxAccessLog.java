package pjh5365.linuxserviceweb.domain.log;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

@Slf4j
@Getter
public class NginxAccessLog extends Log {
    private String ip = "";
    private String time = "";
    private String http = "";
    private String status = "";
    private String request = "";
    private String client = "";

    public NginxAccessLog() {
    }

    public NginxAccessLog(String ip, String time, String http, String status, String request, String client) {
        this.ip = ip;
        this.time = time;
        this.http = http;
        this.status = status;
        this.request = request;
        this.client = client;
    }

    static public StringBuilder getLog() {
        String logPath = "/var/log/nginx/";     // nginx 의 로그가 있는 경로
        String file = "access.log";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(logPath + file));
            StringBuilder fileReader = new StringBuilder();   // 파일의 내용을 담을 빌더
            String readLine;    // 파일을 한줄씩 읽을 문자열

            try {
                while ((readLine = bufferedReader.readLine()) != null) {   // 파일의 끝까지 읽기
                    fileReader.append(readLine).append("\n");
                }
                return fileReader;
            } catch (IOException e) {
                log.error("로그 파일이 비었거나, 로그 파일을 읽는데 실패했습니다. {}", e.getMessage());
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException ex) {
            log.error("로그파일을 여는데 실패했습니다. {}", ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    static public NginxAccessLog[] loadLog() {
        StringBuilder ipSb = new StringBuilder();   // 접속시도 아이피를 담을 빌더
        StringBuilder timeSb = new StringBuilder(); // 접속시각 정보를 담을 빌더
        StringBuilder httpSb = new StringBuilder(); // http 정보를 담을 빌더
        StringBuilder statusSb = new StringBuilder();   // 응답상태를 담을 빌더
        StringBuilder requestSb = new StringBuilder();   // 응답상태를 담을 빌더
        StringBuilder clientSb = new StringBuilder();   // 클라이언트의 정보를 담을 빌더

        // 파일에 내용이 없는 경우를 대비해 빈 배열을 생성
        NginxAccessLog[] logs = new NginxAccessLog[1];
        logs[0] = new NginxAccessLog();
        StringBuilder fileReader = NginxAccessLog.getLog();

        if(!fileReader.isEmpty()) {  // 파일에 내용이 존재한다면
            String[] lines = fileReader.toString().split("\n"); // 파일을 읽은 내용을 배열로 한줄씩 저장

            for (int i = lines.length - 1; i >= 0; i--) {    // 읽은 내용을 뒤에서부터 담아 최신내용을 먼저 보여주게 하기
                StringTokenizer st = new StringTokenizer(lines[i]);
                String[] tokens = new String[st.countTokens()];

                int index = 0;
                while (st.hasMoreTokens()) {
                    tokens[index++] = st.nextToken();
                }
                // access log 인덱스별 내용
                // 0 : 접속시도아이피
                // 3,4 : 시각
                // 5,6,7 : http 방법
                // 8 : 상태
                // 9 : 응답크기
                // 10 : 요청주소
                // 나머지 클라이언트 정보

                ipSb.append(tokens[0]).append("\n");
                timeSb.append(tokens[3]).append(tokens[4]).append("\n");
                httpSb.append(tokens[5]).append(tokens[6]).append(tokens[7]).append("\n");
                statusSb.append(tokens[8]).append("\n");
                requestSb.append(tokens[10]).append("\n");
                for(int j = 11; j < tokens.length; j++)
                    clientSb.append(tokens[j]).append(" ");
                clientSb.append("\n");
            }
            String[] ip = ipSb.toString().split("\n"); // ip 정보를 읽은 내용을 배열로 한줄씩 저장
            String[] time = timeSb.toString().split("\n"); // 시각 정보를 읽은 내용을 배열로 한줄씩 저장
            String[] http = httpSb.toString().split("\n"); // http 정보를 읽은 내용을 배열로 한줄씩 저장
            String[] status = statusSb.toString().split("\n"); // 상태 정보를 읽은 내용을 배열로 한줄씩 저장
            String[] request = requestSb.toString().split("\n"); // 상태 정보를 읽은 내용을 배열로 한줄씩 저장
            String[] client = clientSb.toString().split("\n"); // 클라이언트 정보를 읽은 내용을 배열로 한줄씩 저장

            logs = new NginxAccessLog[ip.length];    // 로그의 길이만큼 배열 생성
            for(int i = 0; i < ip.length; i++) {
                logs[i] = new NginxAccessLog(ip[i], time[i], http[i], status[i], request[i], client[i]);
            }
        }
        return logs;   // 읽어온 내용을 리턴, 없다면 빈칸을 리턴
    }
}
