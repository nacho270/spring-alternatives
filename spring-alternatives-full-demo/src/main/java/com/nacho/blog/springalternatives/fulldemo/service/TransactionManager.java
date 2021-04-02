package com.nacho.blog.springalternatives.fulldemo.service;

import org.jooq.DSLContext;
import org.jooq.TransactionalCallable;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TransactionManager {

  private final DSLContext dslContext;

  @Inject
  public TransactionManager(DSLContext dslContext) {
    this.dslContext = dslContext;
  }

  public <T> T doInTransaction(TransactionalCallable<T> transactionalCallable){
    return dslContext.transactionResult(transactionalCallable);
  }
}
