package rnd.net.aws.emp.svc.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import rnd.net.aws.emp.svc.api.model.domain.entity.Employee;
import rnd.net.aws.emp.svc.api.model.domain.request.EmployeeRequest;
import rnd.net.aws.emp.svc.api.model.domain.response.EmployeeResponse;

import java.util.List;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper {
    List<EmployeeResponse> mapEmployees(List<Employee> employees);

    EmployeeResponse mapEmployee(Employee employee);

    Employee mapEmployee(EmployeeRequest employeeRequest);
}
