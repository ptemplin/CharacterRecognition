package com.petertemplin;

import java.util.List;
import java.util.ArrayList;

public class NetworkNode {
	
	public static double WEIGHT_INIT_FACTOR = 0.5;
	public static double LEARNING_RATE = 1;
	
	public int layer;
	public int nodeNumber;
	public double currentActivation;
	public double currentDelta;
	public List<NetworkNode> children;
	public List<Double> weights;
	public List<NetworkNode> parents;
	
	public NetworkNode() {
		layer = -1;
		nodeNumber = -1;
		currentActivation = 0;
		currentDelta = 0;
		children = new ArrayList<NetworkNode>();
		weights = new ArrayList<Double>();
		parents = new ArrayList<NetworkNode>();
	}
	
	public NetworkNode(int layer, int nodeNumber, int numChildren, int numParents) {
		this.layer = layer;
		this.nodeNumber = nodeNumber;
		currentActivation = 0;
		currentDelta = 0;
		children = new ArrayList<NetworkNode>(numChildren);
		weights = new ArrayList<Double>(numChildren);
		parents = new ArrayList<NetworkNode>(numParents);
	}
	
	public void addChild(NetworkNode child) {
		children.add(child);
		weights.add(generateRandomWeight());
		child.addParent(this);
	}
	
	public void addChild(NetworkNode child, double weight) {
		children.add(child);
		weights.add(weight);
		child.addParent(this);
	}
	
	private void addParent(NetworkNode parent) {
		parents.add(parent);
	}
	
	public int getNumChildren(){
		if (children != null) {
			return children.size();
		}
		return 0;
	}
	
	public int getNumWeights(){
		if (weights != null) {
			return weights.size();
		}
		return 0;
	}
	
	public int getNumParents(){
		if (parents != null) {
			return parents.size();
		}
		return 0;
	}
	
	public double getActivation(){
		return currentActivation;
	}
	
	public void setActivation(double activation){
		this.currentActivation = activation;
	}
	
	public void calculateActivation() {
		double activation = 0;
		for (NetworkNode parent : parents) {
			double parentWeight = parent.getWeightForNode(nodeNumber);
			activation += parent.currentActivation*parentWeight;
		}
		currentActivation = applySigmoidFunction(activation);
	}
	
	public double getWeightForNode(int index) {
		return weights.get(index-1);
	}
	
	public static double applySigmoidFunction(double input) {
		return (1 / (1 + Math.exp(-input)));
	}
	
	public void learn() {
		double delta = 0;
		NetworkNode current = null;
		int numChildren = children.size();
		// for each child...
		for (int i = 0; i < numChildren; i++) {
			current = children.get(i);
			// increase the delta count
			delta += current.currentDelta*weights.get(i);
			// change the weight of the connection to reflect the past 
			// forward propagation
			double newWeight = weights.get(i) + LEARNING_RATE*currentActivation*current.currentDelta;
			weights.set(i, newWeight);
		}
		// set this nodes delta
		currentDelta = delta * currentActivation * (1 - currentActivation);
	}
	
	/*
	 * Returns a random weight between +/- WEIGHT_INIT_FACTOR
	 */
	public static double generateRandomWeight() {
		return Math.random()*(2*WEIGHT_INIT_FACTOR) - WEIGHT_INIT_FACTOR;
	}
}
