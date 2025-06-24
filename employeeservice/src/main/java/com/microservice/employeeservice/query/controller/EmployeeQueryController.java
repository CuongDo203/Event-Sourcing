package com.microservice.employeeservice.query.controller;

import java.util.List;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.microservice.commonservice.model.EmployeeResponseCommonModel;
import com.microservice.commonservice.queries.GetDetailEmployeeQuery;
import com.microservice.employeeservice.query.model.EmployeeResponseModel;
import com.microservice.employeeservice.query.queries.GetAllEmployeeQuery;

@RestController
@RequestMapping("/api/v1/employees")
@Tag(name = "Employee Query")
public class EmployeeQueryController {
	
	@Autowired
	private QueryGateway gateway;
	
	@Operation(
            summary = "Get List Employee",
            description = "Get endpoint for employee with filter",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized / Invalid Token"
                    )
            }
    )
	@GetMapping("")
	public List<EmployeeResponseModel> getAllEmployee(
			@RequestParam(name = "isDisciplined", required = false, defaultValue = "false") Boolean isDisciplined) {
		List<EmployeeResponseModel> list = gateway.query(new GetAllEmployeeQuery(isDisciplined), 
				ResponseTypes.multipleInstancesOf(EmployeeResponseModel.class)).join();
		return list;
	}

	@GetMapping("/{employeeId}")
    public EmployeeResponseCommonModel getDetailEmployee(
    		@PathVariable("employeeId") String employeeId){
        return gateway.query(new GetDetailEmployeeQuery(employeeId),
        		ResponseTypes.instanceOf(EmployeeResponseCommonModel.class)).join();
    }
}
