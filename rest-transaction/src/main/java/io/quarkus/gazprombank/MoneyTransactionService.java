package io.quarkus.gazprombank;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;


import java.util.List;

@ApplicationScoped
@Transactional(REQUIRED)
public class MoneyTransactionService {
    @Transactional(SUPPORTS)
    public List<MoneyTransaction> findAllTransactions(){
        return MoneyTransaction.listAll();
    }

    @Transactional(SUPPORTS)
    public MoneyTransaction findTransactionById(Long id){
        return MoneyTransaction.findById(id);
    }

    public MoneyTransaction persistTransaction(@Valid MoneyTransaction transaction){
        MoneyTransaction.persist(transaction);
        return transaction;
    }

    public MoneyTransaction updateTransaction(@Valid MoneyTransaction transaction){
        MoneyTransaction entity = MoneyTransaction.findById(transaction.id);
        entity.client_id = transaction.client_id;
        entity.CARD_AMOUNT_EQV_CBR = transaction.CARD_AMOUNT_EQV_CBR;
        entity.MCC_CD = transaction.MCC_CD;
        entity.MCC_KIND_CD = transaction.MCC_KIND_CD;
        entity.TRANSACTION_DT = transaction.TRANSACTION_DT;

        return entity;
    }

    public void deleteTransaction(Long id){
        MoneyTransaction transaction = MoneyTransaction.findById(id);
        transaction.delete();
    }
}
