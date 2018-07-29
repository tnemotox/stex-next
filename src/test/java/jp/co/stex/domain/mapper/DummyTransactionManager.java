package jp.co.stex.domain.mapper;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.SimpleTransactionStatus;

public class DummyTransactionManager implements PlatformTransactionManager {
  public TransactionStatus getTransaction(TransactionDefinition transactionDefinition)
      throws TransactionException {
    return new SimpleTransactionStatus();
  }

  public void commit(TransactionStatus transactionStatus) throws TransactionException {
  }

  public void rollback(TransactionStatus transactionStatus) throws TransactionException {

  }
}
