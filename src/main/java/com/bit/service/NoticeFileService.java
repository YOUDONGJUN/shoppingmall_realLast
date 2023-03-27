package com.bit.service;

import com.bit.model.MessageDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class NoticeFileService {
    public static final String IMAGE_REPO = "D:\\image_repo\\notice_image_repo";
//    private final String NAMESPACE = "mapper.NoticeMapper";
//    private SqlSession session;


    public String getMessage(int num, HttpServletRequest request) {
        String message = null;
        if (num == 1) {
            message = "<script>alert('새글이 추가 되었습니다');";
            message += "location.href='" + request.getContextPath() + "/notice/noticeAllList';</script>";
        } else {
            message = "<script>alert('문제가 발생했습니다!!!');";
            message +=
                    "location.href='" + request.getContextPath() + "/notice/writeForm';</script>";
        }
        return message;
    }


    public String saveFile(MultipartFile file) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss-");
        Calendar calendar = Calendar.getInstance();
        String sysFileName =
                sdf.format(calendar.getTime()) + file.getOriginalFilename();
        File saveFile = new File(IMAGE_REPO + "/" + sysFileName);
        try {
            file.transferTo(saveFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sysFileName;
    }


    public String getMessage(MessageDTO messageDTO) {
        String message = null;
        String path = messageDTO.getRequest().getContextPath();
        if (messageDTO.getResult() == 1) {
            message = "<script>alert('" + messageDTO.getSuccessMessage() + "');";
            message += "location.href='" + path + messageDTO.getSuccessURL() + "'</script>";
        } else {
            message = "<script>alert('" + messageDTO.getFailMessage() + "');";
            message += "location.href='" + path + messageDTO.getFailURL() + "'</script>";
        }
        return message;
    }


    public void deleteImage(String noticeImageFileName) {
        File file = new File(IMAGE_REPO + "/" + noticeImageFileName);
        file.delete();

    }


}