package com.meta.memo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Memo {
    private Long id;
    private String username;
    private String contents;

    public Memo(MemoRequestDto memoRequestDto){
        this.username = memoRequestDto.getUsername();
        this.contents = memoRequestDto.getContents();


    }

}
