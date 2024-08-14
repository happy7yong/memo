package com.meta.memo.controller;

import com.meta.memo.dto.MemoRequestDto;
import com.meta.memo.dto.MemoResponseDto;
import com.meta.memo.entity.Memo;
import com.meta.memo.service.MemoService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")


public class MemoController {
    //멤버변수선언
    private final MemoService memoService;

    //생성자 : MemoController가 생성될 때 생성됨.
    public MemoController(JdbcTemplate jdbcTemplate){
        this.memoService = new MemoService(jdbcTemplate);
    }


    /**
     *CREATE
     * @param memoRequestDto
     * @return
     */

    //CREATE
    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto memoRequestDto){
        return memoService.createMemo(memoRequestDto);
    }


    /**
     * Read
    * */

    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos(){
        return memoService.getMemos();
    }




    @PutMapping("/memos/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto memoRequestDto) {
        return memoService.updateMemo(id, memoRequestDto);
    }


    @DeleteMapping("/memos/{id}")
    public Long deleteMemo(@PathVariable Long id){
       MemoService memoService = new MemoService(jdbcTemplate);
       return memoService.deleteMemo(id);
    }
}
