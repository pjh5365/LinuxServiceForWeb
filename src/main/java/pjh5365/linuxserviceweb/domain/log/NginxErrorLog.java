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
public class NginxErrorLog extends Log {
    private String day;
    private String time;
    private String type;
    private String pid;
    private String content;

    public NginxErrorLog() {
    }

    public NginxErrorLog(String day, String time, String type, String pid, String content) {
        this.day = day;
        this.time = time;
        this.type = type;
        this.pid = pid;
        this.content = content;
    }

    static public StringBuilder getLog() {
        String logPath = "/var/log/nginx/";     // nginx 의 로그가 있는 경로
        String file = "error.log";
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

    static public NginxErrorLog[] loadLog() {
        StringBuilder daySb = new StringBuilder();   // 접속시도 아이피를 담을 빌더
        StringBuilder timeSb = new StringBuilder(); // 접속시각 정보를 담을 빌더
        StringBuilder typeSb = new StringBuilder(); // http 정보를 담을 빌더
        StringBuilder pidSb = new StringBuilder();   // 응답상태를 담을 빌더
        StringBuilder contentSb = new StringBuilder();   // 클라이언트의 정보를 담을 빌더

        // 파일에 내용이 없는 경우를 대비해 빈 배열을 생성
        NginxErrorLog[] logs = new NginxErrorLog[1];
        logs[0] = new NginxErrorLog();
        StringBuilder fileReader = NginxErrorLog.getLog();

        if(!fileReader.isEmpty()) {  // 파일에 내용이 존재한다면
            String[] lines = fileReader.toString().split("\n"); // 파일을 읽은 내용을 배열로 한줄씩 저장

            for (int i = lines.length - 1; i >= 0; i--) {    // 읽은 내용을 뒤에서부터 담아 최신내용을 먼저 보여주게 하기
                StringTokenizer st = new StringTokenizer(lines[i]);
                String[] tokens = new String[st.countTokens()];

                int index = 0;
                while (st.hasMoreTokens()) {
                    tokens[index++] = st.nextToken();
                }
                // error log 인덱스별 내용
                // 0 :  날짜
                // 1 :  시간
                // 2 :  분류
                // 3 : pid
                // 나머지는 그냥 내용으로 출력하기

                daySb.append(tokens[0]).append("\n");
                timeSb.append(tokens[1]).append("\n");
                typeSb.append(tokens[2]).append("\n");
                pidSb.append(tokens[3]).append("\n");
                for(int j = 4; j < tokens.length; j++)  // 남은 내용 전부 붙이기
                    contentSb.append(tokens[j]).append(" ");
                contentSb.append("\n");
            }
            String[] day = daySb.toString().split("\n");
            String[] time = timeSb.toString().split("\n");
            String[] type = typeSb.toString().split("\n");
            String[] pid = pidSb.toString().split("\n");
            String[] content = contentSb.toString().split("\n");

            logs = new NginxErrorLog[day.length];    // 로그의 길이만큼 배열 생성
            for(int i = 0; i < day.length; i++) {
                logs[i] = new NginxErrorLog(day[i], time[i], type[i], pid[i], content[i]);
            }
        }
        return logs;   // 읽어온 내용을 리턴, 없다면 빈칸을 리턴
    }
}
