package it.polito.tdp.rivers.model;

import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Simulator {

	// Coda degli eventi
	private Queue<Event> queue;

	// Parametri di simulazione
	private double k;
	private double f_out_min;
	private River river;
	private List<Flow> flows;
	private Double Q;

	// Output della simulazione (giorno per giorno)
	private Double C_med;
	private int fail_days;		

	// STATO DEL MONDO simulato
	private Double capienza;

	public Simulator() {
		this.queue = new PriorityQueue<Event>();
		this.C_med = 0.0;
		this.fail_days = 0;
	}
	
	public void initialize(River river, double k) {
		this.river = river;
		this.k = k;
		
		this.flows = river.getFlows();
		Collections.sort(flows);
		this.f_out_min = this.river.getFlowAvg() * 0.8 * 3600*24;
		this.Q = this.k * this.river.getFlowAvg() * 30 * 3600*24; //converto il f_med da m^3/s a m^3/giorno
		this.capienza = Q/2; 
		
		for(Flow f : this.flows)
			this.queue.add(new Event(f));

	}
	
	public void run() {
		while(!this.queue.isEmpty()) { // non ci sono altri elementi che fanno concludere la simulazione se non l'esaurimento dei flussi
			Event e = this.queue.poll();
//			System.out.println(e);
			processEvent(e);
		}
		this.C_med = this.C_med/river.getN();
	}

	private void processEvent(Event e) {
		double f_in = e.getFlowObject().getFlow() * 3600*24; //conversione in totale quantità
		double f_out = f_out_min;
		
		if(Math.random()<=0.05)
			f_out += 9 * f_out_min; // 5% prob ne venga richiesto 10 volte tanto
		
		if((capienza + f_in - f_out) > this.Q) {
			f_out += Q-capienza-f_in; //scarico l'eventuale flusso in eccesso
		}
		if(this.capienza + f_in - f_out < 0) {
			this.fail_days++;
		} else {
			this.capienza += f_in - f_out;
			this.C_med += this.capienza;
		}
	}

	public Double getC_med() {
		return C_med;
	}

	public int getFail_days() {
		return fail_days;
	}
	
	
}
