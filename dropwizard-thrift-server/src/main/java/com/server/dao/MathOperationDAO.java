package com.server.dao;

import java.util.Optional;

import org.hibernate.SessionFactory;

import com.server.entity.MathOperation;

import io.dropwizard.hibernate.AbstractDAO;

public class MathOperationDAO extends AbstractDAO<MathOperation>{

	public MathOperationDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	
	public Optional<MathOperation> findByNum1AndNum2AndOperation(int num1,int num2,String operation) {
		return (Optional<MathOperation>) namedQuery("com.server.entity.MathOperation.findByNum1AndNum2AndOperation")
        .setInteger("num1", num1)
        .setInteger("num2", num2)
        .setParameter("operation", operation)
        .uniqueResultOptional();
	}


	public void persist(Integer i1, Integer i2, String op) {
		MathOperation mo = new MathOperation(op, i1, i2);
		persist(mo);
	}

}
