package it.polito.tdp.rivers.model;

public class Event implements Comparable<Event>{
	
	private Flow flow;
	
	public Event(Flow flow) {
		this.flow = flow;
	}

	public Flow getFlowObject() {
		return flow;
	}

	@Override
	public int compareTo(Event o) {
		return this.flow.getDay().compareTo(o.getFlowObject().getDay());
	}
	
}
