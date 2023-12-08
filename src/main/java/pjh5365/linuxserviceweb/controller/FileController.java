package pjh5365.linuxserviceweb.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pjh5365.linuxserviceweb.domain.file.CustomFile;
import pjh5365.linuxserviceweb.service.FileService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class FileController {

    private final FileService fileService;
    private static Map<String, String> successMap;
    private static Map<String, String> failMap;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
        successMap = new HashMap<>();
        failMap = new HashMap<>();
        successMap.put("success", "no");  // 맵 초기화하기
        failMap.put("fail", "no"); // 맵 초기화하기
    }

    @GetMapping("/file-list")
    public String fileMain(Model model) {
        CustomFile[] customFiles = fileService.getList(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("fileList", customFiles);

        if(successMap.get("success").matches("yes")) {    // 각종 처리가 성공한 경우
            model.addAttribute("uploadSuccess", true);
            model.addAttribute("msg", successMap.get("msg"));
            successMap.put("success", "no");  // 맵 초기화하기
        }
        else if(failMap.get("fail").matches("yes")) {  // 각종 처리에 실패한 경우
            model.addAttribute("uploadFail", true);
            model.addAttribute("msg", failMap.get("msg"));
            failMap.put("fail", "no"); // 맵 초기화하기
        }

        return "file";
    }

    @PostMapping("/file-upload")
    public String fileUpload(@RequestParam("file") MultipartFile file) {
        try {
            fileService.save(file, SecurityContextHolder.getContext().getAuthentication().getName());   // 파일과 사용자이름을 함께넘김
            successMap.put("success", "yes");
            successMap.put("msg", "파일 업로드에 성공했습니다.");
        } catch (Exception e) {
            failMap.put("fail", "yes");
            failMap.put("msg", "파일 업로드에 실패했습니다.");
        }

        return "redirect:/file-list";
    }

    @GetMapping("/file-download/{fileName}")
    public void fileDownload(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        try {
            fileService.download(SecurityContextHolder.getContext().getAuthentication().getName(), fileName, response);
            successMap.put("success", "yes");
            successMap.put("msg", "파일 다운로드에 성공했습니다.");
        } catch (Exception e) {
            successMap.put("fail", "yes");
            successMap.put("msg", "파일 다운로드에 실패했습니다.");
            response.sendRedirect("/file-list");
        }
    }

    @GetMapping("/file-delete/{fileName}")
    public String fileRemove(@PathVariable String fileName) {
        if(fileService.delete(SecurityContextHolder.getContext().getAuthentication().getName(), fileName)) {
            successMap.put("success", "yes");
            successMap.put("msg", "파일 삭제에 성공했습니다.");
        }
        else {
            successMap.put("fail", "yes");
            successMap.put("msg", "파일 삭제에 실패했습니다.");
        }
        return "redirect:/file-list";
    }
}
