package emp;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//DTO : Data Transfer Object 
//VO : Value Object 
@Getter@Setter@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpDTO {
	int employee_id;    
	String first_name ;    
	String last_name;      
	String email ;         
	String phone_number ;  
	Date hire_date ;     
	String job_id ;        
	double salary;         
	double commission_pct ;
	int manager_id;     
	int department_id  ;
	 
	
}
