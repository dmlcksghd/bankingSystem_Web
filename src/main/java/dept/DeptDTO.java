package dept;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString @NoArgsConstructor @AllArgsConstructor
public class DeptDTO {
	int department_id;
	String department_name;
	int manager_id;
	int location_id;
}
