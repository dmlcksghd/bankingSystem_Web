package dept;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;

public class DeptDAO {

	String sql_selectAll = "select * from departments";
	String sql_selectById = "select * from departments where department_id=?";
	String sql_insert = "insert into departments values(?,?,?,?)";
	String sql_update = """
	        UPDATE departments SET
	        department_name = ?,
	        manager_id = ?,
	        location_id = ?
	        WHERE department_id = ?
	        """;
	String sql_delete = "delete from departments where department_id = ?";

	Connection conn;
	PreparedStatement st;
	ResultSet rs;
	int result;

	// 1. 모두 조회
	public List<DeptDTO> selectAll() {
		List<DeptDTO> deptList = new ArrayList<>();
        conn = DBUtil.getConnection();
		try {
			st = conn.prepareStatement(sql_selectAll);
			rs = st.executeQuery();
			while (rs.next()) {
				DeptDTO dept = makeDept(rs);
				deptList.add(dept);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return deptList;
	}
	//2. 상세보기
	public DeptDTO selectById(int deptId) {
        DeptDTO dept = null;
        conn = DBUtil.getConnection();
        try {
            st = conn.prepareStatement(sql_selectById);
            st.setInt(1, deptId);
            rs = st.executeQuery();
            if (rs.next()) {
                dept = makeDept(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbDisconnect(conn, st, rs);
        }
        return dept;
    }


	private DeptDTO makeDept(ResultSet rs) throws SQLException {
        DeptDTO dept = new DeptDTO();
        dept.setDepartment_id(rs.getInt("department_id"));
        dept.setDepartment_name(rs.getString("department_name"));
        dept.setManager_id(rs.getInt("manager_id"));
        dept.setLocation_id(rs.getInt("location_id"));
        return dept;
	}
	public int insert(DeptDTO dept) {
		int result = 0;
		String sql = sql_insert;
		conn = DBUtil.getConnection();
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(sql);
//            st.getConnection().prepareStatement(sql);
            st.setInt(1, dept.getDepartment_id());
            st.setString(2, dept.getDepartment_name());
            st.setInt(3, dept.getManager_id());
            st.setInt(4, dept.getLocation_id());
            
            result = st.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbDisconnect(conn, st, null);
        }
        return result;
	}
	public int update(DeptDTO dept) {
		int result = 0;
		String sql = sql_update;
		conn = DBUtil.getConnection();
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(sql);
//            st.getConnection().prepareStatement(sql);
            st.setInt(4, dept.getDepartment_id());
            st.setString(1, dept.getDepartment_name());
            st.setInt(2, dept.getManager_id());
            st.setInt(3, dept.getLocation_id());
            
            result = st.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbDisconnect(conn, st, null);
        }
		return result;
	}
	public int delete(int deptId) {
		int result = 0;
		String sql = sql_delete;
		conn = DBUtil.getConnection();
		PreparedStatement st = null;
		try {
			conn.setAutoCommit(false);
			st = conn.prepareStatement(sql);
//            st.getConnection().prepareStatement(sql);
            st.setInt(1, deptId);
            
            
            result = st.executeUpdate();
            conn.commit();
            
        } catch (SQLException e) {
            e.printStackTrace();
            try {
				conn.rollback();	// DB에 작업한 내용 취소
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
        } finally {
            DBUtil.dbDisconnect(conn, st, null);
        }
		return result;
	}

}
