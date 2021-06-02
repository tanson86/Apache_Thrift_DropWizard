package com.server;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServlet;
import org.eclipse.jetty.server.Server;

import com.dropwizard.thrift.services.Calculator;
import com.server.dao.MathOperationDAO;
import com.server.entity.MathOperation;
import com.server.resources.CalculatorServerResource;
import com.server.thrift.CalculatorHandler;
import com.server.thrift.CalculatorServerThriftApplication;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.lifecycle.ServerLifecycleListener;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class CalculatorServerApplication extends Application<CalculatorServerConfiguration>{

	public static void main(String[] args) throws Exception {
		new CalculatorServerApplication().run(args);

	}
	
    /**
     * Hibernate bundle.
     */
    private final HibernateBundle<CalculatorServerConfiguration> hibernateBundle
            = new HibernateBundle<CalculatorServerConfiguration>(
                    MathOperation.class
            ) {

                public DataSourceFactory getDataSourceFactory(
                		CalculatorServerConfiguration configuration
                ) {
                    return configuration.getDataSourceFactory();
                }

            };
            
            
    @Override
    public void initialize(
            final Bootstrap<CalculatorServerConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
    }


	@Override
	public void run(CalculatorServerConfiguration configuration, Environment environment) throws Exception {
		final MathOperationDAO mathOperationDAO = new MathOperationDAO(hibernateBundle.getSessionFactory());
		final CalculatorServerResource csr = new CalculatorServerResource(mathOperationDAO,hibernateBundle);
		environment.jersey().register(csr);
		
		//Registering the TServlet for handling Thrift requests.
		TServlet ts = new TServlet(new Calculator.Processor<CalculatorHandler>(new CalculatorHandler(csr)), new TBinaryProtocol.Factory());
		environment.servlets().addServlet("calculator", ts).addMapping("/calculator");
		

	
	}
}
