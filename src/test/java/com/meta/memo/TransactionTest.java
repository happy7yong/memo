package com.meta.memo;

import com.meta.memo.entity.Memo;
import com.meta.memo.repository.MemoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TransactionTest {
    @PersistenceContext
    EntityManager em;

    @Autowired
    MemoRepository memoRepository;

    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    @Rollback(value = false)  //테스트 완료후 롤백되므로 남기려고 false로 해둠
    void test1  (){
        Memo memo = new Memo();
        memo.setUsername("Meta");
        memo.setContents("@Transactional 테스트 중");

        em.persist(memo); //
    }

    @Transactional(readOnly = true)
    @Test
    @DisplayName("메모 생성 실패 테스트")
    void test2(){
        Memo memo = new Memo();
        memo.setUsername("Meta");
        memo.setContents("@Transactional 테스트 중");

        em.persist(memo);
    }
}
