package pjh5365.linuxserviceweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import pjh5365.linuxserviceweb.domain.file.CustomFile;
import pjh5365.linuxserviceweb.service.FileService;

@Controller
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/file-list")
    public String fileMain(Model model) {
        CustomFile[] customFiles = fileService.getList(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("fileList", customFiles);
        return "file";
    }

    @PostMapping("/file-upload")
    public String fileUpload(MultipartFile file) {

        try {
            fileService.save(file, SecurityContextHolder.getContext().getAuthentication().getName());   // 파일과 사용자이름을 함께넘김
        } catch (Exception e) {
            // TODO: 2023/12/7 파일업로드 성공, 실패에 따른 이벤트추가하기
        }

        return "redirect:/file-list";
    }
}
