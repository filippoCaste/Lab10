package it.polito.tdp.rivers.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
	
	private Map<Integer, River> idMap;
	private RiversDAO dao;
	
	public Model() {
		dao = new RiversDAO();
		this.idMap = new HashMap<>();
		
		dao.getAllRivers(idMap);
	}
	
	public Collection<River> getAllRivers() {
		return idMap.values();
	}
	
	public void calculateParameters(River r) {
		dao.calculateDatas(r);
	}

}
