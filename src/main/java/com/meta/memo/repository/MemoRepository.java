package com.meta.memo.repository;

import com.meta.memo.dto.MemoRequestDto;
import com.meta.memo.dto.MemoResponseDto;
import com.meta.memo.entity.Memo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Component
public class MemoRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Memo save(Memo memo) {

        //DB저장
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO memo(username, contents) VALUES (?, ?)";

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement
                    =con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,memo.getUsername());
            preparedStatement.setString(2,memo.getContents());
            return preparedStatement;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        memo.setId(id);

        return memo;

    }


    public List<MemoResponseDto> findAll() {
        //DB조회
        String sql = "SELECT * FROM memo";

        return jdbcTemplate.query(sql, new RowMapper<MemoResponseDto>() {
            @Override
            public MemoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {

                Long id = rs.getLong("id");
                String username = rs.getString("username");
                String contents = rs.getString("contents");
                return new MemoResponseDto(id, username, contents);
            }
        });

    }

    public void update(Long id, MemoRequestDto memoRequestDto) {

        String sql = "UPDATE memo SET username = ?, contents = ? WHERE id = ?";

        jdbcTemplate.update(sql, memoRequestDto.getUsername(), memoRequestDto.getContents(), id);

    }

    public void delete(Long id) {
        //DB삭제
        String sql = "DELETE FROM memo WHERE id = ?";
        jdbcTemplate.update(sql, id);

    }


    /**
     * 특정 ID의 메모 존재 여부 확인
     * @param id
     * @return Memo
     *
     * */

    public Memo findById(Long id) {
        String sql = "SELECT * FROM memo WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if(resultSet.next()) {
                Memo memo = new Memo();
                memo.setUsername(resultSet.getString("username"));
                memo.setContents(resultSet.getString("contents"));
                return memo;
            } else {
                return null;
            }
        }, id);
    }
}
