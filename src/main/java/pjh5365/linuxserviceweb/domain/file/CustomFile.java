package pjh5365.linuxserviceweb.domain.file;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.StringTokenizer;

@Slf4j
@Getter
public class CustomFile {

    private String fileSize;
    private String time;
    private String fileName;

    public CustomFile() {
    }

    public CustomFile(String fileSize, String time, String fileName) {
        this.fileSize = fileSize;
        this.time = time;
        this.fileName = fileName;
    }

    public void save(MultipartFile file, String username) throws Exception {
        java.io.File path = new java.io.File("/home/pibber/file/" + username);

        if(!path.exists()) {    // 파일을 처음 저장해서 폴더가 없다면
            try{
                path.mkdir(); // 사용자명으로 폴더 생성
                log.info("파일을 저장하기 위해 사용자 [{}]의 폴더 생성", username);
            }
            catch(Exception e){
                e.getStackTrace();
                throw new Exception();
            }
        }

        try {
            java.io.File to = new java.io.File(path + "/" + file.getOriginalFilename());    // 사용자명으로 생성된 폴더에 이름을 그대로 올림
            file.transferTo(to);
            log.info("파일 업로드 성공 {}", to);
        } catch (IOException e) {
            log.error("파일 업로드에 실패했습니다. : {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public StringBuilder getSb(String username) {
        String[] cmd = {"/bin/sh", "-c", "ls -al /home/pibber/file/" + username};
        try {
            Process process = Runtime.getRuntime().exec(cmd);   // 저장된 파일을 이용해 메일을 전송한다.
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String readLine;

            while((readLine = br.readLine()) != null) {
                sb.append(readLine).append("\n");
            }
            return sb;
        } catch (IOException e) {
            log.error("파일 정보들을 읽어오는데 실패했습니다. : {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public CustomFile[] getList(String username) {
        StringBuilder result = this.getSb(username);

        // 파일에 내용이 없는 경우를 대비해 빈 배열을 생성
        CustomFile[] file = new CustomFile[1];
        file[0] = new CustomFile();

        String[] list = result.toString().split("\n");

        StringBuilder fileSizeSb = new StringBuilder();
        StringBuilder timeSb = new StringBuilder();
        StringBuilder fileNameSb = new StringBuilder();

        // 0 권한
        // 1 은 안에 파일 개수
        // 2 ~ 3 생성자, 소유자?
        // 4 파일크기
        // 5 월
        // 6 일
        // 7 시간
        // 8 파일명

        for(int i = 3; i < list.length; i++) {
            StringTokenizer st = new StringTokenizer(list[i]);
            String[] tokens = new String[st.countTokens()];

            int index = 0;
            while (st.hasMoreTokens())
                tokens[index++] = st.nextToken();

            fileSizeSb.append(tokens[4]).append("\n");
            timeSb.append(tokens[5]).append(" ").append(tokens[6]).append("일 ").append(tokens[7]).append("\n");
            fileNameSb.append(tokens[8]).append("\n");
        }
        String[] fileSize = fileSizeSb.toString().split("\n");
        String[] time = timeSb.toString().split("\n");
        String[] fileName = fileNameSb.toString().split("\n");

        file = new CustomFile[fileSize.length];
        for(int i = 0; i < file.length; i++)
            file[i] = new CustomFile(fileSize[i], time[i], fileName[i]);

        return file;
    }

    public void download(String username, String fileName, HttpServletResponse response) throws Exception {
        File file = new File("/home/pibber/file/" + username + "/" + fileName);

        response.setContentType("application/download");
        response.setContentLength((int)file.length());
        response.setHeader("Content-disposition", "attachment;filename=\"" + fileName + "\"");

        try {
            OutputStream os = response.getOutputStream();

            FileInputStream fis = new FileInputStream(file);
            FileCopyUtils.copy(fis, os);
            fis.close();
            os.close();
        } catch (Exception e) {
            log.error("파일 다운로드에 실패했습니다. : {}", e.getMessage());
            throw new Exception();
        }
    }
}
