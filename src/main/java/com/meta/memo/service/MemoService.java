package com.meta.memo.service;

import com.meta.memo.dto.MemoRequestDto;
import com.meta.memo.dto.MemoResponseDto;
import com.meta.memo.entity.Memo;
import com.meta.memo.repository.MemoRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class MemoService {

    private MemoRepository memoRepository;

    public MemoService(JdbcTemplate jdbcTemplate){
        this.memoRepository = new MemoRepository(jdbcTemplate);
    }


    /**
     *CREATE
     * @param memoRequestDto
     * @return MemoRespose
     */

    public MemoResponseDto createMemo(MemoRequestDto memoRequestDto) {
        //RequestDto -> Entity
        Memo memo = new Memo(memoRequestDto);

        //DB저장
        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);
        Memo saveMemo = memoRepository.save(memo);

        // Entity-> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);


        return memoResponseDto;
    }

    public List<MemoResponseDto> getMemos() {
        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);
        List<MemoResponseDto> memoResponseDtos = memoRepository.findAll();
        return memoResponseDtos;
    }

    public Long updateMemo(Long id, MemoRequestDto memoRequestDto) {
        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);


        Memo memo = memoRepository.findById(id);

        if(memo != null) {
           //메모 수정
            memoRepository.update(id,memoRequestDto);

            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다");
        }
    }

    public Long deleteMemo(Long id) {
        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);


        Memo memo = memoRepository.findById(id);

        if(memo != null){
            memoRepository.delete(id);
            return id;
        }else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }
}
