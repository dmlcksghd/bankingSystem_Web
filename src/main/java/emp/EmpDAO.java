package emp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dept.DeptDTO;
import util.DBUtil;

//DAO(Data Access Object)
//Service---->DB에간다. 
//       <--- 
public class EmpDAO {
	
	public Map<String, Object> selectJoin2(String jobid) {
		//1.DTO만든다 2.MAP사용한다. 
		String sql = """
				select employee_id, first_name, salary, department_name, city, country_name
				from employees join departments using(department_id)
				                       join locations using(location_id)
				                       join countries USING (country_id)
				where  job_id = ? 
				""";
		Map<String, Object> map = new HashMap<>();
		List<EmpDTO> emplist = new ArrayList<>();
		List<DeptDTO> deptlist = new ArrayList<>();
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(sql);
			st.setString(1, jobid);
			rs = st.executeQuery();
			while(rs.next()) {
				EmpDTO emp = new EmpDTO();
				emp.setEmployee_id(rs.getInt("Employee_id"));
				emp.setFirst_name(rs.getString("First_name"));
				emp.setSalary(rs.getDouble("salary"));
				emplist.add(emp);
				
				DeptDTO dept = new DeptDTO();
				dept.setDepartment_name(rs.getString("Department_name"));
				deptlist.add(dept);
			}
			map.put("emp", emplist);
			map.put("dept", deptlist);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return map;
		
	}
	
	//Jobs테이블의 모든 data가져오기 
	public List<JobDTO> selectAllJob() {
		//1.DTO만든다 2.MAP사용한다. 
		String sql = """
				 select * from jobs
				""";
		List<JobDTO> joblist = new ArrayList<>();
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next()) {
				JobDTO emp = JobDTO.builder()
						.job_id(rs.getString("job_id"))
						.job_title(rs.getString("job_title"))
						.min_salary(rs.getInt("min_salary"))
						.max_salary(rs.getInt("max_salary"))
						.build();
				joblist.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return joblist;
		
	}
	
	public List<EmpJoinDTO> selectJoin(String jobid) {
		//1.DTO만든다 2.MAP사용한다. 
		String sql = """
				select employee_id, first_name, salary, department_name, city, country_name
				from employees join departments using(department_id)
				                       join locations using(location_id)
				                       join countries USING (country_id)
				where  job_id = ? 
				""";
		List<EmpJoinDTO> emplist = new ArrayList<EmpJoinDTO>();
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(sql);
			st.setString(1, jobid);
			rs = st.executeQuery();
			while(rs.next()) {
				EmpJoinDTO emp = EmpJoinDTO.builder()
						.city(rs.getString("city"))
						.employee_id(rs.getInt("employee_id"))
						.first_name(rs.getString("first_name"))
						.country_name(rs.getString("country_name"))
						.department_name(rs.getString("department_name"))
						.build();
				emplist.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emplist;
		
	}
	
	
	
	
	//1.특정부서의 직원조회      where department_id = ? 
	public List<EmpDTO> selectByDept(int dept_id) {
		// 모든 직원을 조회하기
		String sql = "select * from employees where department_id = ?" ;
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		List<EmpDTO> emplist = new ArrayList<EmpDTO>();
		try {
			st = conn.prepareStatement(sql); //SQL문 준비 
			st.setInt(1, dept_id);  //?에 값을 채우기 
			rs = st.executeQuery(); //DB에 가서 실행하고 결과를 가져온다. 
			while (rs.next()) {
				EmpDTO emp = makeEmp2(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emplist;
	}

	
	//2.특정job_id인 직원조회   where job_id = ? 
	public List<EmpDTO> selectByJob(String job_id) {
		// 모든 직원을 조회하기
		String sql = "select * from employees where job_id = ?" ;
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		List<EmpDTO> emplist = new ArrayList<EmpDTO>();
		try {
			st = conn.prepareStatement(sql); //SQL문 준비 
			st.setString(1, job_id);  //?에 값을 채우기 
			rs = st.executeQuery(); //DB에 가서 실행하고 결과를 가져온다. 
			while (rs.next()) { //다음data가 있는지?
				EmpDTO emp = makeEmp2(rs); //한건을 DTO만든다. 
				emplist.add(emp);          //여러건이므로 Collection에 담기  
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emplist;
	}
	
	//3.급여가 ?이상인 직원조회   where salary >= ? 
	public List<EmpDTO> selectBySalary(double salary) {
		// 모든 직원을 조회하기
		String sql = "select * from employees where salary >= ?" ;
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		List<EmpDTO> emplist = new ArrayList<EmpDTO>();
		try {
			st = conn.prepareStatement(sql); //SQL문 준비 
			st.setDouble(1, salary);  //?에 값을 채우기 
			rs = st.executeQuery(); //DB에 가서 실행하고 결과를 가져온다. 
			while (rs.next()) { //다음data가 있는지?
				EmpDTO emp = makeEmp2(rs); //한건을 DTO만든다. 
				emplist.add(emp);          //여러건이므로 Collection에 담기  
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emplist;
	}
	
	//4.부서, 직책, 급여, 입사일 조건으로 조회 
	//     where department_id = ? and job_id = ? and salary >= ? and  hire_date >= ?
	public List<EmpDTO> selectByCondition(Map<String,Object> map) {
		// 모든 직원을 조회하기
		String sql = "select * "
				+ " from employees "
				+ " where department_id = ? "
				+ " and job_id = ? "
				+ " and salary >= ? "
				+ " and  hire_date >= ?" ;
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		List<EmpDTO> emplist = new ArrayList<EmpDTO>();
		try {
			st = conn.prepareStatement(sql); //SQL문 준비 
			st.setInt(1, (Integer)map.get("department_id"));  //?에 값을 채우기 
			st.setString(2, (String)map.get("job_id"));  //?에 값을 채우기 
			st.setDouble(3, (Double)map.get("salary"));  //?에 값을 채우기 
			st.setDate(4, (Date)map.get("hire_date"));  //?에 값을 채우기 
			
			rs = st.executeQuery(); //DB에 가서 실행하고 결과를 가져온다. 
			while (rs.next()) { //다음data가 있는지?
				EmpDTO emp = makeEmp2(rs); //한건을 DTO만든다. 
				emplist.add(emp);          //여러건이므로 Collection에 담기  
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emplist;
	}	
	
	public List<EmpDTO> selectAll() {
		// 모든 직원을 조회하기
		String sql = "select * from employees order by 1";
		Connection conn = DBUtil.getConnection();
		Statement st = null;
		ResultSet rs = null;
		List<EmpDTO> emplist = new ArrayList<EmpDTO>();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				EmpDTO emp = makeEmp(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emplist;
	}

	public EmpDTO selectById(int empid) {
		// 특정 직원을 조회하기
		String sql = "select  *  from employees where employee_id = " + empid;
		Connection conn = DBUtil.getConnection();
		Statement st = null;
		ResultSet rs = null;
		EmpDTO emp = null;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				emp = makeEmp(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emp;
	}

	// DB에 입력
	public int insert(EmpDTO emp) {
		int result = 0;
		String sql = "insert into employees values (?,?,?,?,?,?,?,?,?,?,?)";
		Connection conn = DBUtil.getConnection();
		// Statement는 ?(binding변수 지원안함) <------PreparedStatement는 지원
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, emp.getEmployee_id());
			st.setString(2, emp.getFirst_name());
			st.setString(3, emp.getLast_name());
			st.setString(4, emp.getEmail());
			st.setString(5, emp.getPhone_number());
			st.setDate(6, emp.getHire_date());
			st.setString(7, emp.getJob_id());
			st.setDouble(8, emp.getSalary());
			st.setDouble(9, emp.getCommission_pct());
			st.setInt(10, emp.getManager_id());
			st.setInt(11, emp.getDepartment_id());

			result = st.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, null);
		}

		return result;
	}

	// 수정
	public int update(EmpDTO emp) {
		int result = 0;
		String sql = """
				update employees set
						FIRST_NAME=?,
						LAST_NAME=?,
						EMAIL=?,
						PHONE_NUMBER=?,
						HIRE_DATE=?,
						JOB_ID=?,
						SALARY=?,
						COMMISSION_PCT=?,
						MANAGER_ID=?,
						DEPARTMENT_ID=?
				where EMPLOYEE_ID=?
				""";
		Connection conn = DBUtil.getConnection();
		// Statement는 ?(binding변수 지원안함) <------PreparedStatement는 지원
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(sql);
			st.setInt(11, emp.getEmployee_id());
			st.setString(1, emp.getFirst_name());
			st.setString(2, emp.getLast_name());
			st.setString(3, emp.getEmail());
			st.setString(4, emp.getPhone_number());
			st.setDate(5, emp.getHire_date());
			st.setString(6, emp.getJob_id());
			st.setDouble(7, emp.getSalary());
			st.setDouble(8, emp.getCommission_pct());
			st.setInt(9, emp.getManager_id());
			st.setInt(10, emp.getDepartment_id());

			result = st.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, null);
		}

		return result;
	}

	// 삭제
	public int delete(int empid) {
		int result = 0;
		String sql = """
				delete from employees  
				where EMPLOYEE_ID=?
				""";
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, empid);
			result = st.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, null);
		}

		return result;
	}

	private static EmpDTO makeEmp2(ResultSet rs) throws SQLException {
		EmpDTO emp = EmpDTO.builder()
				.commission_pct(rs.getDouble("Commission_pct"))
				.department_id(rs.getInt("Department_id"))
				.email(rs.getString("email"))
				.employee_id(rs.getInt("Employee_id"))
				.first_name(rs.getString("first_name"))
				.last_name(rs.getString("Last_name"))
				.hire_date(rs.getDate("Hire_date"))
				.job_id(rs.getString("job_id"))
				.manager_id(rs.getInt("Manager_id"))
				.phone_number(rs.getString("Phone_number"))
				.salary(rs.getDouble("salary"))
				.build();
		return emp;
	}
	
	private static EmpDTO makeEmp(ResultSet rs) throws SQLException {
		EmpDTO emp = new EmpDTO();
		emp.setCommission_pct(rs.getDouble("Commission_pct"));
		emp.setDepartment_id(rs.getInt("Department_id"));
		emp.setEmail(rs.getString("email"));
		emp.setEmployee_id(rs.getInt("Employee_id"));
		emp.setFirst_name(rs.getString("First_name"));
		emp.setLast_name(rs.getString("Last_name"));
		emp.setHire_date(rs.getDate("Hire_date"));
		emp.setJob_id(rs.getString("job_id"));
		emp.setManager_id(rs.getInt("Manager_id"));
		emp.setPhone_number(rs.getString("Phone_number"));
		emp.setSalary(rs.getDouble("salary"));
		 
		return emp;
	}

}
