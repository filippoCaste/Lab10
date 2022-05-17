package it.polito.tdp.rivers.model;

public class TestSimulator {

	public static void main(String[] args) {
		Simulator s = new Simulator();
		s.initialize(null, 10);
		s.run();
		
		System.out.println("Fail days: " + s.getFail_days() + "\n"
				+ "C_med: " + s.getC_med());
		

	}

}
