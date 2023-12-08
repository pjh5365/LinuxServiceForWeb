package pjh5365.linuxserviceweb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pjh5365.linuxserviceweb.domain.file.CustomFile;

@Slf4j
@Service
public class FileService {
    public void save(@RequestParam("file") MultipartFile file, String username) throws Exception {
        CustomFile customFile = new CustomFile();
        try {
            customFile.save(file, username);
        }
        catch(Exception e){
            e.getStackTrace();
            log.error("파일 업로드 실패 : {}", e.getMessage());
            throw new Exception();
        }
    }

    public CustomFile[] getList(String username) {
        CustomFile customFile = new CustomFile();
        return customFile.getList(username);
    }
}
