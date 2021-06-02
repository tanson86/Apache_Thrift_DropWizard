package com.client.dropwizard.controller;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.client.thrift.application.CalculatorThriftClient;
import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;
import com.dropwizard.thrift.services.Operation;

import io.dropwizard.jersey.caching.CacheControl;


@Path("/calculator")
@Produces(MediaType.APPLICATION_JSON)
public class CalculatorController {
		
	@GET
    @Timed(name = "get-requests-timed")
    @Metered(name = "get-requests-metered")
    @CacheControl(maxAge = 1, maxAgeUnit = TimeUnit.DAYS)
    public Response calculate(@QueryParam("num1") String num1,@QueryParam("num2") String num2,@QueryParam("op") String op) {
        System.out.println(String.format("Calculate request came with nums %s %s and operation as %s", num1,num2,op));
        try {
        	Integer i1 = Integer.parseInt(num1);
            Integer i2 = Integer.parseInt(num2);
            Operation o = Operation.valueOf(op.toString());    
            CalculatorThriftClient ctca = new CalculatorThriftClient();
            System.out.println("------>");
            int res = ctca.calculate(i1, i2, o);
            return Response.ok(String.format("Response for request with nums %s %s and operation as %s is %d", num1,num2,op,res)).build();
        }catch (Exception e){
        	return Response.status(Status.BAD_REQUEST).build();
        }
    }

}
