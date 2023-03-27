package com.bit.controller;

import com.bit.model.NoticeRepDTO;
import com.bit.service.NoticeService;
import com.bit.session_name.MemberSessionName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notice")
public class NoticeRepController implements MemberSessionName {
    @Autowired
    NoticeService noticeService;

    @PostMapping(value = "addReply", produces = "application/json; charset=utf-8")
    public void addReply(@RequestBody Map<String, Object> map, HttpSession session) {

        NoticeRepDTO noticeRepDTO = new NoticeRepDTO();
        noticeRepDTO.setNoticeWriterId((String) session.getAttribute(LOGIN));
        noticeRepDTO.setWrite_group(Integer.parseInt((String) map.get("notice_id")));
        noticeRepDTO.setNoticeTitle((String) map.get("notice_title"));
        noticeRepDTO.setNoticeContent((String) map.get("notice_content"));

        noticeService.addReply(noticeRepDTO);
    }

    @GetMapping(value = "replyData/{write_group}", produces = "application/json; charset=utf-8")
    public List<NoticeRepDTO> replyData(@PathVariable int write_group) {
        return noticeService.getRepList(write_group);
    }
}

