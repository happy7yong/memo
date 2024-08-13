package com.meta.memo.controller;

import com.meta.memo.dto.MemoRequestDto;
import com.meta.memo.dto.MemoResponseDto;
import com.meta.memo.entity.Memo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@RestController
@RequestMapping("/api")


public class MemoController {

    private final JdbcTemplate jdbcTemplate;

    public MemoController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    // CREATE
    /**
     *
     * @param memoRequestDto
     * @return
     */

    //CREATE
    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto memoRequestDto){
        //RequestDto -> Entity
        Memo memo = new Memo(memoRequestDto);

        // DB 저장
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO memo(username, contents) VALUES (?, ?)";

        jdbcTemplate.update( con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, memo.getUsername());
            preparedStatement.setString(2, memo.getContents());
            return preparedStatement;
        }, keyHolder);

        // DB insert후 받아온 키 확인
        Long id = keyHolder.getKey().longValue();
        memo.setId(id);

        // Entity-> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);
        return memoResponseDto;
    }

    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos(){
        //DB조회
        String sql = "SELECT * from memo";

        return jdbcTemplate.query(sql, new RowMapper<MemoResponseDto>(){
            @Override
            public MemoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("id");
                String username = rs.getString("username");
                String contents = rs.getString("contents");

                return new MemoResponseDto(id, username, contents);
            }
        });
    }



//    @GetMapping("/memos")
//    public List<MemoResponseDto> getMemos(){
//        // Map to List
//        List<MemoResponseDto> responseDtoList = memoList.values().stream().map(MemoResponseDto::new).toList();
//        return  responseDtoList;
//    }
//
//    @PutMapping("/memos/{id}")
//    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto memoRequestDto){
//        if(memoList.containsKey(id)) {
//            Memo memo = memoList.get(id);
//
//            memo.update(memoRequestDto);
//            return memo.getId();
//
//        }else {
//
//            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
//        }
//
//    }
//
//    //DELETE
//    @DeleteMapping("/memos/{id}")
//    public Long deleteMemo(@PathVariable Long id) {
//        // 특정 id의 메모가 데이터베이스에 존재하는지 확인
//        if(memoList.containsKey(id)){
//            memoList.remove(id);
//            return id;
//        }else {
//
//            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
//        }
//    }
//
}
