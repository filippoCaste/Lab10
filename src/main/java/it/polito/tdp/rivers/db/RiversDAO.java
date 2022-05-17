package it.polito.tdp.rivers.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RiversDAO {

	public void getAllRivers(Map<Integer, River> idMap) {
		
		final String sql = "SELECT id, name FROM river";

		//List<River> rivers = new ArrayList<River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				if(idMap.get(res.getInt("id"))==null)
					idMap.put(res.getInt("id"), new River(res.getInt("id"), res.getString("name")));
			}

			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		//return rivers;
	}
	
	public void calculateDatas(River r) {
		String sql = "SELECT MAX(f.day) AS first, MIN(f.day) AS last, COUNT(*) AS tot, AVG(flow) AS favg "
				+ "FROM flow f "
				+ "WHERE f.river=?";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, r.getId());
			
			ResultSet res = st.executeQuery();

			if(res.first()) {
				r.setFirst(res.getDate("first").toLocalDate());
				r.setLast(res.getDate("last").toLocalDate());
				r.setFlowAvg(res.getDouble("favg"));
				r.setN(res.getInt("tot"));
			}

			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
	}
	
	public List<Flow> getAllFlowsOf(River r) {
		String sql = "SELECT * FROM flow WHERE river=?";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, r.getId());
			
			ResultSet res = st.executeQuery();
			
			List<Flow> result = new ArrayList<>();

			while(res.next()) {
				result.add(new Flow(res.getDate("day").toLocalDate(), res.getDouble("flow"), r));
			}

			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
	}
}
