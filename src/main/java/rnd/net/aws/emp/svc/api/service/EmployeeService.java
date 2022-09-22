package rnd.net.aws.emp.svc.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rnd.net.aws.emp.svc.api.client.AwsDynamoDbServiceClient;
import rnd.net.aws.emp.svc.api.client.AwsSimpleStorageServiceClient;
import rnd.net.aws.emp.svc.api.mapper.EmployeeMapper;
import rnd.net.aws.emp.svc.api.model.domain.entity.Employee;
import rnd.net.aws.emp.svc.api.model.domain.request.EmployeeRequest;
import rnd.net.aws.emp.svc.api.model.domain.response.EmployeeResponse;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private static final String AWS_DYNAMO_DB_EMP_TABLE_NAME = "Employee";

    private final EmployeeMapper employeeMapper;
    private final AwsSimpleStorageServiceClient simpleStorageServiceClient;
    private final AwsDynamoDbServiceClient awsDynamoDbServiceClient;

    public List<EmployeeResponse> getEmployees(){
        return employeeMapper.mapEmployees(awsDynamoDbServiceClient
                .getAllItems(AWS_DYNAMO_DB_EMP_TABLE_NAME, Employee.class));
    }

    public EmployeeResponse getEmployee(String empId) {
        Employee employee = new Employee();
        employee.setId(empId);
        return employeeMapper.mapEmployee(awsDynamoDbServiceClient
                .getItemById(AWS_DYNAMO_DB_EMP_TABLE_NAME, employee, Employee.class));
    }

    public void addEmployee(String employeeRequest, MultipartFile empPhoto){
        Employee employee = employeeMapper.mapEmployee(convertJsonStringToEmployeeRequest(employeeRequest));
        awsDynamoDbServiceClient.addItem(AWS_DYNAMO_DB_EMP_TABLE_NAME, employee, Employee.class);
        if(!employee.getId().isBlank() && empPhoto != null){
            simpleStorageServiceClient.uploadFileIntoS3Bucket(empPhoto, employee.getId());
        }
    }

    public void editEmployee(String employeeRequest, MultipartFile empPhoto){
        Employee employee = employeeMapper.mapEmployee(convertJsonStringToEmployeeRequest(employeeRequest));
        awsDynamoDbServiceClient.updateItem(AWS_DYNAMO_DB_EMP_TABLE_NAME, employee, Employee.class);
        if(!employee.getId().isBlank() && empPhoto != null){
            simpleStorageServiceClient.uploadFileIntoS3Bucket(empPhoto, employee.getId());
        }
    }

    public void deleteEmployee(String empId){
        Employee employee = new Employee();
        employee.setId(empId);
        awsDynamoDbServiceClient.deleteItem(AWS_DYNAMO_DB_EMP_TABLE_NAME, employee, Employee.class);
    }

    private EmployeeRequest convertJsonStringToEmployeeRequest(String employeeRequestStr){
        EmployeeRequest employeeRequest = null;
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            employeeRequest = objectMapper.readValue(employeeRequestStr, EmployeeRequest.class);
        }catch (IOException exception){
            exception.printStackTrace();
        }
        return employeeRequest;
    }
}
