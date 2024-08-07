package com.meta.memo.controller;

import com.meta.memo.dto.MemoRequestDto;
import com.meta.memo.dto.MemoResponseDto;
import com.meta.memo.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")


public class MemoController {

    // 임시 DB
    private final Map<Long, Memo> memoList = new HashMap<>();


    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto memoRequestDto){
        //RequestDto -> Entity
        Memo memo = new Memo(memoRequestDto);

        // Memo 최대 번호 확인
        Long maxId = memoList.size() > 0 ? Collections.max(memoList.keySet()) + 1 : 1;
        memo.setId(maxId);

        // DB 저장
        memoList.put(memo.getId(), memo);

        // Entity-> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);
        return memoResponseDto;

    }

    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos(){
        // Map to List
        List<MemoResponseDto> responseDtoList = memoList.values().stream().map(MemoResponseDto::new).toList();
        return  responseDtoList;
    }
}
