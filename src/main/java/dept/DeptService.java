package dept;

import java.util.List;

public class DeptService {
	
	DeptDAO deptDAO = new DeptDAO();
	
	public List<DeptDTO> selectAllService() {
		return deptDAO.selectAll();
	}
	public DeptDTO selectByIdService(int deptId) {
		return deptDAO.selectById(deptId);
	}
	//추가
	public int insertService(DeptDTO dept) {
		return deptDAO.insert(dept);
	}
	//수정
	public int updateService(DeptDTO dept) {
		return deptDAO.update(dept);
	}
	//삭제
	public int deleteService(int deptId) {
		return deptDAO.delete(deptId);
	}
}
