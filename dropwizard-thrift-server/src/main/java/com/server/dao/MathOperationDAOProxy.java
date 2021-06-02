package com.server.dao;

import java.util.Optional;

import com.server.entity.MathOperation;

import io.dropwizard.hibernate.UnitOfWork;

public class MathOperationDAOProxy {
	
	private MathOperationDAO mathOperationDAO;
	
	public MathOperationDAOProxy(MathOperationDAO mathOperationDAO) {
		this.mathOperationDAO = mathOperationDAO;
	}
	
	@UnitOfWork
	public Optional<MathOperation> findByNum1AndNum2AndOperation(int num1,int num2,String operation) {
		return mathOperationDAO.findByNum1AndNum2AndOperation(num1, num2, operation);
	}

	@UnitOfWork
	public void persist(Integer i1, Integer i2, String op) {
		mathOperationDAO.persist(i1, i2, op);
	}
}
