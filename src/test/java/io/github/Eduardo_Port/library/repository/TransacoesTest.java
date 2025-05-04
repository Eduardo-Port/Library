package io.github.Eduardo_Port.library.repository;

import io.github.Eduardo_Port.library.model.Author;
import io.github.Eduardo_Port.library.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TransacoesTest {
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    TransactionService transactionService;

    /**
     * commit -> finaliza a transação com a persistência de dados no banco de dados
     * rollback -> finaliza a transação sem alterar os dados no banco de dados
     */
    @Test
    void simpleTransaction() {
        transactionService.execute();
    }

    @Test
    void transactionStateManager() {
        transactionService.updateWithoutUpdate();
    }
}
