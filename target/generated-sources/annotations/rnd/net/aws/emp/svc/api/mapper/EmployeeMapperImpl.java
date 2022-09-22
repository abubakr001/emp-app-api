package rnd.net.aws.emp.svc.api.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import rnd.net.aws.emp.svc.api.model.domain.entity.Employee;
import rnd.net.aws.emp.svc.api.model.domain.request.EmployeeRequest;
import rnd.net.aws.emp.svc.api.model.domain.response.EmployeeResponse;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-22T01:49:34-0400",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.15 (Oracle Corporation)"
)
@Component
public class EmployeeMapperImpl implements EmployeeMapper {

    @Override
    public List<EmployeeResponse> mapEmployees(List<Employee> employees) {
        if ( employees == null ) {
            return null;
        }

        List<EmployeeResponse> list = new ArrayList<EmployeeResponse>( employees.size() );
        for ( Employee employee : employees ) {
            list.add( mapEmployee( employee ) );
        }

        return list;
    }

    @Override
    public EmployeeResponse mapEmployee(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        EmployeeResponse employeeResponse = new EmployeeResponse();

        employeeResponse.setId( employee.getId() );
        employeeResponse.setFirstName( employee.getFirstName() );
        employeeResponse.setLastName( employee.getLastName() );
        employeeResponse.setJobTitle( employee.getJobTitle() );
        employeeResponse.setLocation( employee.getLocation() );

        return employeeResponse;
    }

    @Override
    public Employee mapEmployee(EmployeeRequest employeeRequest) {
        if ( employeeRequest == null ) {
            return null;
        }

        Employee employee = new Employee();

        employee.setId( employeeRequest.getId() );
        employee.setFirstName( employeeRequest.getFirstName() );
        employee.setLastName( employeeRequest.getLastName() );
        employee.setJobTitle( employeeRequest.getJobTitle() );
        employee.setLocation( employeeRequest.getLocation() );

        return employee;
    }
}
