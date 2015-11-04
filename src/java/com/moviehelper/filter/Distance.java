/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moviehelper.filter;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Stephen
 */
public class Distance {
    
    protected double sumWt;
	protected int scale;
	protected double power;
	protected double totalWt;
	protected int count;
	
	protected enum DistanceStatus {
	    DistanceImploded, 
	    DistanceExploded, 
	    DistanceUntouched 
	}
	
	private Map<Integer, DistanceStatus> attributeDistanceStatus = new HashMap<Integer, DistanceStatus>();
	
	/**
	 * @param scale
	 */
	public void setScale(int scale) {
		this.scale = scale;
	}

	/**
	 * 
	 */
	public void initialize() {
		sumWt = 0.0;
		totalWt = 0.0;
		count = 0;
		attributeDistanceStatus.clear();
	}
	
	/**
	 * @param distance
	 * @param field
	 */
	protected double getEffectiveDistance(double distance, MovieField field){
		double effectDist = 0;
		String distanceFunction = field.getContAttrDistanceFunction();
		double weight = field.getWeight(); 
		double threshold = field.getFunctionThreshold();

		if (distanceFunction.equals("none")) {
			effectDist = distance;
		} else if (distanceFunction.equals("nonLinear")) {
			//if weight < 1 then convex i.e. effective distance greater than distance otherwise concave
			effectDist =  (1 / weight) * distance  + ( 1 - 1 / weight) * distance * distance;
		} else if (distanceFunction.equals("sigmoid")) {
			//transtion at threshold, higher weight will simulate step function
			effectDist = 1.0 / (1 + Math.exp(-weight * (distance - threshold)));
		} else if (distanceFunction.equals("step")) {
			//transition at threshold
			effectDist = distance < threshold ? 0 : 1;
		} else if (distanceFunction.equals("ramp")) {
			//transtion at threshold
			effectDist = distance < threshold ? 0 : distance;
		} 
		
		//check for distance implosion and explosion
		if (effectDist < field.getImplodeThreshold()) {
			attributeDistanceStatus.put(field.getOrdinal(), DistanceStatus.DistanceImploded);
		} else if (effectDist > field.getExplodeThreshold()) {
			attributeDistanceStatus.put(field.getOrdinal(), DistanceStatus.DistanceExploded);
		}
		
		return effectDist;
	}
	
	/**
	 * @return
	 */
	protected DistanceStatus getDistanceStatus() {
		DistanceStatus status = DistanceStatus.DistanceUntouched;
		int explodedCount = 0;
		int implodedCount = 0;
		
		for (int fieldOrd : attributeDistanceStatus.keySet()) {
			if (attributeDistanceStatus.get(fieldOrd) == DistanceStatus.DistanceExploded) {
				++explodedCount;
			} else {
				++implodedCount;
			}
		}
		
		//explode or implode only when there is consensus
		if (explodedCount > 0 && implodedCount == 0) {
			status = DistanceStatus.DistanceExploded;
		} else if (implodedCount > 0 && explodedCount == 0) {
			status = DistanceStatus.DistanceImploded;
		}
		return status;
	}
	
	/**
	 * @return
	 */
	public int getScale() {
		return scale;
	}

	/**
	 * @return
	 */
	public double getPower() {
		return power;
	}

	/**
	 * @param power
	 */
	public void setPower(double power) {
		this.power = power;
	}

	/* (non-Javadoc)
	 * @see org.sifarish.feature.DistanceStrategy#accumulate(double, double)
	 */
	public void accumulate(double distance, double weight) {
		distance = Math.abs(distance);
		double effectDist =  (1 / weight) * distance  + ( 1 - 1 / weight) * distance * distance;
		sumWt += Math.pow(effectDist, power);
		++count;
	}

	/**
	 * @param distance
	 * @param field
	 */
	public void accumulate(double distance, MovieField field){
		distance = Math.abs(distance);
		double effectDist = getEffectiveDistance(distance, field);
		sumWt += Math.pow(effectDist, power);
		++count;
	}

	
	
	/* (non-Javadoc)
	 * @see org.sifarish.feature.DistanceStrategy#getSimilarity()
	 */
	public int getSimilarity() {
		int sim = 0;
		DistanceStatus status = getDistanceStatus();
		if (status == DistanceStatus.DistanceUntouched) {
			sim = (int)((Math.pow(sumWt, 1.0/power)  * scale) / count);
		} else if (status == DistanceStatus.DistanceImploded) {
			sim = 0;
		} else if (status == DistanceStatus.DistanceExploded) {
			sim = scale;
		}
		
		return sim;
	}

	public double getScaledSimilarity(boolean isScaled) {
		double dist = 0;
		if (isScaled) {
			dist = ((Math.pow(sumWt, 1.0/power)  * scale) / count);
		} else {
			dist = ((Math.pow(sumWt, 1.0/power) ) / count);
		}
		return 0;
	}
}
