package com.bit.mapper;

import com.bit.model.NoticeDTO;
import com.bit.model.NoticeRepDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoticeMapper {
    public List<NoticeDTO> selectAllNoticeList(@Param("s") int start, @Param("e") int end);

    public int writeSave(NoticeDTO noticeDTO);

    public NoticeDTO contentView(int noticeId);

    public void noticeViews(int noticeId);

    public int modify(NoticeDTO noticeDTO);

    public void addReply(NoticeRepDTO noticeRepDTO); //답글 달기 답글

    public List<NoticeRepDTO> getRepList(int write_group);

    public int delete(int noticeId);

    public int selectNoticeCount();
}
