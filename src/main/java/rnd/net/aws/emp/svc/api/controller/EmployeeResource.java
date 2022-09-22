package rnd.net.aws.emp.svc.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rnd.net.aws.emp.svc.api.model.domain.response.EmployeeResponse;
import rnd.net.aws.emp.svc.api.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/employee-directory/v1")
@RequiredArgsConstructor
public class EmployeeResource {

    private final EmployeeService employeeService;

    @GetMapping("/info")
    public ResponseEntity<String> welcome(){
        return ResponseEntity.ok("Employee Directory Service");
    }

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeResponse>> employees(){
        return ResponseEntity.ok(employeeService.getEmployees());
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeResponse> employee(@PathVariable("id") String empId){
        return ResponseEntity.ok(employeeService.getEmployee(empId));
    }

    @PostMapping(value = "/employee", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Void> addEmployee(@RequestPart("employee") String employeeRequest, @RequestPart(value = "photo", required = false) MultipartFile empPhoto){
        employeeService.addEmployee(employeeRequest,empPhoto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/employee", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<EmployeeResponse> editEmployee(@RequestPart("employee") String employeeRequest, @RequestPart(value = "photo", required = false) MultipartFile empPhoto){
        employeeService.editEmployee(employeeRequest, empPhoto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") String empId){
        employeeService.deleteEmployee(empId);
        return ResponseEntity.noContent().build();
    }
}
