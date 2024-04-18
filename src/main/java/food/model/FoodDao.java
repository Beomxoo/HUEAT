package food.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mysql.db.DbConnect;

public class FoodDao {

	DbConnect db=new DbConnect();
	
	public void insertFood(FoodDto dto) {
		Connection conn=db.getConnection();
		PreparedStatement pstmt=null;
		
		String sql="insert into food(h_num,f_name,f_photo,f_price) values(?,?,?,?)";
		
		try {
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getH_num());
			pstmt.setString(2, dto.getF_name());
			pstmt.setString(3, dto.getF_photo());
			pstmt.setString(4, dto.getF_price());
			
			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.dbClose(pstmt, conn);
		}
	}
	
	//유지)) 휴게소별 메뉴판 구현을 위해서 작성
	public List<FoodDto> getMenu(String h_num){
		List<FoodDto> list=new ArrayList<FoodDto>();
		Connection conn=db.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		String sql="select * from food where h_num=?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, h_num);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				FoodDto dto=new FoodDto();
				dto.setF_num(rs.getString("f_num"));
				dto.setF_name(rs.getString("f_name"));
				dto.setF_photo(rs.getString("f_photo"));
				dto.setF_price(rs.getString("f_price"));
				dto.setH_num(rs.getString("h_num"));
				
				list.add(dto);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			db.dbClose(rs, pstmt, conn);
		}
		return list;
	}
	
	//유지)) f_num을 얻기위해서 h_num과 f_name사용
	public String getF_num(String h_num, String f_name) {
		String f_num="";
		Connection conn=db.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		String sql="select f_num from food where h_num=? and f_name=?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, h_num);
			pstmt.setString(2, f_name);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				f_num=rs.getString("f_num");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			db.dbClose(rs, pstmt, conn);
		}	
		return f_num;
	}
}
