package employees;

import io.micrometer.core.annotation.Timed;
import io.micrometer.observation.annotation.Observed;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeesService {

    private EmployeesRepository repository;

    private ApplicationEventPublisher publisher;

    @Observed(name = "list.employees", contextualName = "list.employees", lowCardinalityKeyValues = {"framework", "spring"})
    public List<EmployeeResource> listEmployees() {
        return repository.findAllResources();
    }

    public EmployeeResource findEmployeeById(long id) {
        return toDto(repository.findById(id).orElseThrow(notFountException(id)));
    }

    @Timed(value="employee.create")
    public EmployeeResource createEmployee(EmployeeResource command) {
        log.info("Create employee");
        log.debug("Create employee with name: {}", command.name());
        Employee employee = new Employee(command.name());
        repository.save(employee);

        publisher.publishEvent(
                new AuditApplicationEvent("anonymous",
                        "employee_has_been_created",
                        Map.of("name", command.name())));

        return toDto(employee);
    }

    @Transactional
    public EmployeeResource updateEmployee(long id, EmployeeResource command) {
        Employee employee = repository.findById(id).orElseThrow(notFountException(id));
        employee.setName(command.name());
        return toDto(employee);
    }

    public void deleteEmployee(long id) {
        repository.deleteById(id);
    }

    private EmployeeResource toDto(Employee employee) {
        return new EmployeeResource(employee.getId(), employee.getName());
    }

    private Supplier<EmployeeNotFoundException> notFountException(long id) {
        return () -> new EmployeeNotFoundException("Employee not found with id: %d".formatted(id));
    }

}
