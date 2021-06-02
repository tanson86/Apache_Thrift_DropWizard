package com.client;

import com.client.dropwizard.controller.CalculatorController;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class CalculatorClientApplication extends Application<CalculatorConfiguration>{


	public static void main(String[] args) throws Exception {
	        new CalculatorClientApplication().run(args);
	    }
	
	
	@Override
    public void run(CalculatorConfiguration configuration, Environment environment) {
		environment.jersey().register(new CalculatorController());
	}

}
