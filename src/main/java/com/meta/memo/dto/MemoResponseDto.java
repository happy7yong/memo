package com.meta.memo.dto;

import com.meta.memo.entity.Memo;
import lombok.Getter;

@Getter
public class MemoResponseDto {
    private Long id;
    private String username;
    private String contents;

    public MemoResponseDto(Memo memo){

        this.id = memo.getId();
        this.username = memo.getUsername();
        this.contents = memo.getContents();
    }


    //return 생성자
    public MemoResponseDto(Long id, String username, String contents) {
        this.id = id;
        this.username = username;
        this.contents = contents;

    }
}
