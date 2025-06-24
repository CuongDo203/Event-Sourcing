package com.microservice.employeeservice.query.projection;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microservice.commonservice.model.EmployeeResponseCommonModel;
import com.microservice.commonservice.queries.GetDetailEmployeeQuery;
import com.microservice.employeeservice.command.data.Employee;
import com.microservice.employeeservice.command.data.EmployeeRepository;
import com.microservice.employeeservice.query.model.EmployeeResponseModel;
import com.microservice.employeeservice.query.queries.GetAllEmployeeQuery;

@Component
public class EmployeeProjection {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@QueryHandler
	private List<EmployeeResponseModel> handle(GetAllEmployeeQuery query) {
		List<Employee> listEmployee = employeeRepository.findAllByIsDisciplined(query.getIsDisciplined());
		return listEmployee.stream().map(e -> {
			EmployeeResponseModel model = new EmployeeResponseModel();
			BeanUtils.copyProperties(e, model);
			return model;
		}).toList();
	}
	
	@QueryHandler
	private EmployeeResponseCommonModel handle(GetDetailEmployeeQuery query) throws Exception {
		Employee employee = employeeRepository.findById(query.getId()).orElseThrow(
				() -> new Exception("Employee not found"));
		EmployeeResponseCommonModel model = new EmployeeResponseCommonModel();
		BeanUtils.copyProperties(employee, model);
		return model;
	}
}
