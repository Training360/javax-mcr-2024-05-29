package employees;

import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "employeesinfo")
@AllArgsConstructor
public class EmployeesEndpoint {

    private EmployeesRepository employeesRepository;

    @ReadOperation
    public EmployeesInfo getEmployees() {
        return new EmployeesInfo(employeesRepository.count());
    }

    record EmployeesInfo(long numberOfEmployees) {

    }
}
