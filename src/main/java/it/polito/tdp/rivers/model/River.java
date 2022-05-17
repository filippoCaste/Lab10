package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.rivers.db.RiversDAO;

public class River {
	private int id;
	private String name;
	private double flowAvg;
	private List<Flow> flows;
	private LocalDate first;
	private LocalDate last;
	private int n;
	
	public River(int id) {
		this.id = id;
	}

	public River(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public double getFlowAvg() {
		return flowAvg;
	}

	public void setFlowAvg(double flowAvg) {
		this.flowAvg = flowAvg;
	}

	public void setFlows(List<Flow> flows) {
		this.flows = flows;
	}

	public List<Flow> getFlows() {
		if (flows == null) {
			RiversDAO dao = new RiversDAO();
			flows = new ArrayList<Flow>(dao.getAllFlowsOf(this));
		}
		return flows;
	}
	
	public LocalDate getFirst() {
		return first;
	}

	public void setFirst(LocalDate first) {
		this.first = first;
	}

	public LocalDate getLast() {
		return last;
	}

	public void setLast(LocalDate last) {
		this.last = last;
	}
	
	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		River other = (River) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
