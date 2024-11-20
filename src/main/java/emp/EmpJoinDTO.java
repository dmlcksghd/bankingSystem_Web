package emp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmpJoinDTO {

	int employee_id;
	String first_name;
	double salary;
	String department_name;
	String city;
	String country_name;
	
}
