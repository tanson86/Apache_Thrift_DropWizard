package com.client.dropwizard.controller;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.concurrent.TimeUnit;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
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
import io.dropwizard.jersey.params.IntParam;


@Path("/calculator")
@Produces(MediaType.APPLICATION_JSON)
public class CalculatorController {
		
	@GET
    @Timed(name = "get-requests-timed")
    @Metered(name = "get-requests-metered")
    @CacheControl(maxAge = 1, maxAgeUnit = TimeUnit.DAYS)
    public Response calculate(@QueryParam("num1") @NotNull OptionalInt num1,@QueryParam("num2") @NotNull OptionalInt num2,@QueryParam("op") @NotNull Operation op) {
        System.out.println(String.format("Calculate request came with nums %s %s and operation as %s", num1,num2,op));
        try {
        	Integer i1 = num1.getAsInt();
            Integer i2 = num2.getAsInt();
//            Operation o = Operation.valueOf(op.toString());    
            CalculatorThriftClient ctca = new CalculatorThriftClient();
            System.out.println("------>");
            int res = ctca.calculate(i1, i2, op);
            return Response.ok(String.format("Response for request with nums %s %s and operation as %s is %d", num1,num2,op,res)).build();
        }catch (Exception e){
        	return Response.status(Status.BAD_REQUEST).build();
        }
    }

}
