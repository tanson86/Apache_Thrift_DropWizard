package com.server.thrift;

import org.apache.thrift.TException;

import com.dropwizard.thrift.services.*;
import com.server.resources.CalculatorServerResource;


public class CalculatorHandler implements Calculator.Iface{
	
	private CalculatorServerResource calculatorServerResource;
	
	public CalculatorHandler() {
		
	}
	
	public CalculatorHandler(CalculatorServerResource calculatorServerResource) {
		System.out.println("Initialising CalculatorHandler");
		this.calculatorServerResource = calculatorServerResource;
	};

	public String ping() throws TException {
		// TODO Auto-generated method stub
		return "PONG";
	}

	public int calculate(Work work) throws InvalidOperation, TException {
		System.out.println("calculate( {" + work.op + "," + work.num1 + "," + work.num2 + "})");
		calculatorServerResource.calculate(String.valueOf(work.num1), String.valueOf(work.num2), work.op.toString());
	    int val = 0;
	    switch (work.op) {
	    case ADD:
	      val = work.num1 + work.num2;
	      break;
	    case SUBTRACT:
	      val = work.num1 - work.num2;
	      break;
	    case MULTIPLY:
	      val = work.num1 * work.num2;
	      break;
	    case DIVIDE:
	      if (work.num2 == 0) {
	        InvalidOperation io = new InvalidOperation();
	        io.whatOp = work.op.getValue();
	        io.why = "Cannot divide by 0";
	        throw io;
	      }
	      val = work.num1 / work.num2;
	      break;
	    default:
	      InvalidOperation io = new InvalidOperation();
	      io.whatOp = work.op.getValue();
	      io.why = "Unknown operation";
	      throw io;
	    }
	    return val;
	}	
	

}
