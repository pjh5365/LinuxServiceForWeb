package pjh5365.linuxserviceweb.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

@Service
public class ReadLogService {

    private final String path = "/Users/parkjihyeok/testLog/";

    public ModelMap getLog(String fileName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path + fileName));

        StringBuilder fileReaderSb = new StringBuilder();   // 파일의 내용을 담을 빌더

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


        ModelMap modelMap = new ModelMap();

        modelMap.addAttribute("ip", ip);
        modelMap.addAttribute("time", time);
        modelMap.addAttribute("http", http);
        modelMap.addAttribute("status", status);
        modelMap.addAttribute("client", client);

        return modelMap;
    }
}
