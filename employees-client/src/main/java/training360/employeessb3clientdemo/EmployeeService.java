package training360.employeessb3clientdemo;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange("/api/employees")
public interface EmployeeService {

    @GetExchange
    List<EmployeeResource> listEmployees();

}
