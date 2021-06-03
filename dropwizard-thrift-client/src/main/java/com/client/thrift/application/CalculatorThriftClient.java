package com.client.thrift.application;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.dropwizard.thrift.services.Calculator;
import com.dropwizard.thrift.services.InvalidOperation;
import com.dropwizard.thrift.services.Operation;
import com.dropwizard.thrift.services.Work;


public class CalculatorThriftClient {

	
	public static void main(String... a) throws InvalidOperation, TException {
		System.out.println("In CalculatorThriftClient---->calculate");
		TTransport transport = new THttpClient("http://localhost:9090/calculator");
		TProtocol protocol = new TBinaryProtocol(transport);
		Calculator.Client client = new Calculator.Client(protocol);
		int num1 = 80,num2 = 10;
		Operation op = Operation.DIVIDE;
		int res = client.calculate(new Work(num1, num2, op));
		System.out.println(String.format("Result of calling calculate with %d,%d and operation %s is %d", num1,num2,op.toString(),res));
		System.out.println(res);
	}
	
	
	public int calculate(int num1,int num2,Operation op) throws TException {
		System.out.println("In CalculatorThriftClient---->calculate");
		TTransport transport = new THttpClient("http://localhost:9090/calculator");
		TProtocol protocol = new TBinaryProtocol(transport);
		Calculator.Client client = new Calculator.Client(protocol);
		int res = client.calculate(new Work(num1, num2, op));
		System.out.println(String.format("Result of calling calculate with %d,%d and operation %s is %d", num1,num2,op.toString(),res));
		return res;
	}

}
