package com.bit.controller;

import com.bit.model.BoardRepDTO;
import com.bit.service.BoardService;
import com.bit.session_name.MemberSessionName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/board")
public class BoardRepController implements MemberSessionName {
    @Autowired
    BoardService boardService;

    @PostMapping(value = "addReply", produces = "application/json; charset=utf-8")
    public void addReply(@RequestBody Map<String, Object> map, HttpSession session) {

        BoardRepDTO boardRepDTO = new BoardRepDTO();
        boardRepDTO.setId((String) session.getAttribute(LOGIN));
        boardRepDTO.setWrite_group(Integer.parseInt((String) map.get("write_no")));
        boardRepDTO.setTitle((String) map.get("title"));
        boardRepDTO.setContent((String) map.get("content"));

        boardService.addReply(boardRepDTO);
    }

    @GetMapping(value = "replyData/{write_group}", produces = "application/json; charset=utf-8")
    public List<BoardRepDTO> replyData(@PathVariable int write_group) {
        return boardService.getRepList(write_group);
    }
}
