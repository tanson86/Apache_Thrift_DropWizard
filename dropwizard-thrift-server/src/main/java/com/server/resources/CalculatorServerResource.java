package com.server.resources;

import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.dropwizard.thrift.services.Operation;
import com.server.CalculatorServerConfiguration;
import com.server.dao.MathOperationDAO;
import com.server.dao.MathOperationDAOProxy;
import com.server.entity.MathOperation;

import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;

@Path("/calculate")
@Produces(MediaType.APPLICATION_JSON)
public class CalculatorServerResource {

	private MathOperationDAO mathOperationDAO;
	
	private HibernateBundle<CalculatorServerConfiguration> hibernateBundle;

	public CalculatorServerResource(MathOperationDAO mathOperationDAO,HibernateBundle<CalculatorServerConfiguration> hibernateBundle) {
		super();
		this.mathOperationDAO = mathOperationDAO;
		this.hibernateBundle = hibernateBundle;
	}
	

	/*
	 * Using hibernate outside the context of jersey and hence have to use UnitOfWorkAwareProxyFactory
	 */
	@GET
    @UnitOfWork
    public int calculate(@QueryParam("num1") String num1,@QueryParam("num2") String num2,@QueryParam("op") String op) {
            try {
            	Integer i1 = Integer.parseInt(num1);
                Integer i2 = Integer.parseInt(num2);
                Operation o = Operation.valueOf(op.toString());
                System.out.println(String.format("Calculate request came with nums %s %s and operation as %s", num1,num2,op));
                MathOperationDAO mathOperationDAO = new MathOperationDAO(hibernateBundle.getSessionFactory());
                MathOperationDAOProxy mathOperationDAOProxy = new UnitOfWorkAwareProxyFactory(hibernateBundle)
                               .create(MathOperationDAOProxy.class,MathOperationDAO.class,mathOperationDAO);
                Optional<MathOperation> mo = mathOperationDAOProxy.findByNum1AndNum2AndOperation(i1, i2, op);
                if(mo.isEmpty()) {
                	mathOperationDAOProxy.persist(i1, i2, op);
                }
                return 10;
            }catch(Exception e) {
            	e.printStackTrace();
            	return -1;
            }
    }
	
	
//	@POST
//    @UnitOfWork
//    public int calculate(Work work) {
//            try {
//            	Integer i1 = work.num1;
//                Integer i2 = work.num2;
//                Operation o = work.op;
//                System.out.println(String.format("Calculate request came with nums %s %s and operation as %s", i1,i2,o.toString()));
//                Optional<MathOperation> mo = mathOperationDAO.findByNum1AndNum2AndOperation(i1, i2, o.toString());
//                if(mo.isEmpty()) {
//                	mathOperationDAO.persist(i1, i2, o.toString());
//                }
//                return 10;
//            }catch(Exception e) {
//            	e.printStackTrace();
//            	return -1;
//            }
//   }
}
