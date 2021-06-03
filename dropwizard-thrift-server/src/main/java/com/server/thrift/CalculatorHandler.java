package com.server.thrift;

import java.util.OptionalInt;

import org.apache.thrift.TException;

import com.dropwizard.thrift.services.*;
import com.server.resources.CalculatorServerResource;


public class CalculatorHandler implements Calculator.Iface{
	
	private CalculatorServerResource calculatorServerResource;
	
	public CalculatorHandler() {}
	
	public CalculatorHandler(CalculatorServerResource calculatorServerResource) {
		System.out.println("Initialising CalculatorHandler");
		this.calculatorServerResource = calculatorServerResource;
	};

	public String ping() throws TException {
		return "PONG";
	}

	public int calculate(Work work) throws TException {
		System.out.println("calculate( {" + work.op + "," + work.num1 + "," + work.num2 + "})");
	    Calculate calc = CalculatorFactory.getCalculator(work);
	    return calc.calculate(work.num1, work.num2,calculatorServerResource);
	}	
}

class CalculatorFactory{
	static Calculate getCalculator(Work work) throws InvalidOperation {
		Calculate c  = null;
		switch (work.op) {
	    case ADD:
	      c = new Add();
	      break;
	    case SUBTRACT:
	      c = new Subtract();
	      break;
	    case MULTIPLY:
	      c = new Multiply();
	      break;
	    case DIVIDE:
	      c = new Divide();
	      break;
	    default:
	      InvalidOperation io = new InvalidOperation();
	      io.whatOp = work.op.getValue();
	      io.why = "Unknown operation";
	      throw io;
	    }
		return c;
	}
}

interface Calculate{
	int calculate(int num1,int num2, CalculatorServerResource calculatorServerResource) throws InvalidOperation, TException;
	default int existsInDB(int num1,int num2, CalculatorServerResource calculatorServerResource, Operation op) {
		return calculatorServerResource.calculate(OptionalInt.of(num1), OptionalInt.of(num2), op);
	}
}

class Add implements Calculate{
	public int calculate(int num1, int num2, CalculatorServerResource calculatorServerResource) {
		int existsInDB = existsInDB(num1, num2, calculatorServerResource, Operation.ADD);
		return num1+num2;
	}
}

class Subtract implements Calculate{

	public int calculate(int num1, int num2, CalculatorServerResource calculatorServerResource) {
		int existsInDB = existsInDB(num1, num2, calculatorServerResource, Operation.SUBTRACT);
		return num1-num2;
	}
	
}

class Multiply implements Calculate{

	public int calculate(int num1, int num2, CalculatorServerResource calculatorServerResource) {
		int existsInDB = existsInDB(num1, num2, calculatorServerResource, Operation.MULTIPLY);
		return num1*num2;
	}
	
}

class Divide implements Calculate{
	public int calculate(int num1, int num2, CalculatorServerResource calculatorServerResource) {
		if(num2 == 0)
			throw new ArithmeticException("Division by 0 not allowed");
		int existsInDB = existsInDB(num1, num2, calculatorServerResource, Operation.DIVIDE);
		return num1/num2;
	}
	
}
