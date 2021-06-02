package com.server.thrift;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

import com.dropwizard.thrift.services.Calculator;


public class CalculatorServerThriftApplication {
	
	private CalculatorHandler calculatorHandler;
	
	public CalculatorServerThriftApplication() {
		
	}
	
	
	public CalculatorServerThriftApplication(CalculatorHandler calculatorHandler) {
		System.out.println("Initialising CalculatorServerThriftApplication");
		this.calculatorHandler = calculatorHandler;
	}
	

	public void startServer() throws TTransportException {
		TServerTransport serverTransport = new TServerSocket(9095);
		TServer server = new TSimpleServer(new TServer.Args(serverTransport)
          .processor(new Calculator.Processor(this.calculatorHandler)));
        System.out.print("Starting the server... ");
        server.serve();
        System.out.println("done.");
	}

}
