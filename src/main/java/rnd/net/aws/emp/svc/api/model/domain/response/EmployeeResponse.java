package rnd.net.aws.emp.svc.api.model.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private String location;
}
