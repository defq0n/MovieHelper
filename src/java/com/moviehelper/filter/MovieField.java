/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moviehelper.filter;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Stephen
 */
public class MovieField implements Serializable {
    private String movieName;
    private int ordinal = -1;
    private boolean type;
    private boolean id;
    private boolean classAttribute;
    private String dataType;
    private String dataSubType = "none";
    private String textDataSubTypeFormat;
    private int min;
    private int max;
    private String unit = "";
    private double weight = 1.0;
    private int matchingOrdinal = -1;
    private List<MovieFieldMapping> mappings;
    private String numDistFunction = "equalSoft";
    private String distAlgorithm;
    private double[] componentWeights;
    private double distThreshold = -1.0;
    private int maxDistance;
    private int maxTimeWindow;
    private String contAttrDistanceFunction = "none";
    private double functionThreshold = 0.5;
    private double implodeThreshold = -0.1;
    private double explodeThreshold = 1.1;
    private double[] partWeights;
    
    public static final String DATA_TYPE_MOVIE_NAME = "movieName";
    public static final String DATA_TYPE_GENRE = "genre";
    public static final String DATA_TYPE_INT = "int";
    public static final String DATA_TYPE_DOUBLE = "double";
    public static final String DATA_TYPE_TEXT = "text";
    public static final String DATA_TYPE_TIME_WINDOW = "timeWindow";
    public static final String DATA_TYPE_HOUR_WINDOW = "hourWindow";		
	
    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setName(String movieName) {
        this.movieName = movieName;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public boolean isId() {
        return id;
    }

    public void setId(boolean id) {
        this.id = id;
    }

    public boolean isClassAttribute() {
        return classAttribute;
    }

    public void setClassAttribute(boolean classAttribute) {
        this.classAttribute = classAttribute;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataSubType() {
        return dataSubType;
    }

    public void setDataSubType(String dataSubType) {
        this.dataSubType = dataSubType;
    }

    public String getTextDataSubTypeFormat() {
        return textDataSubTypeFormat;
    }

    public void setTextDataSubTypeFormat(String textDataSubTypeFormat) {
        this.textDataSubTypeFormat = textDataSubTypeFormat;
    }

    public int getMatchingOrdinal() {
        return matchingOrdinal;
    }

    public void setMatchingOrdinal(int matchingOrdinal) {
        this.matchingOrdinal = matchingOrdinal;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
        this.contAttrDistanceFunction = "nonLinear";
    }

    public List<MovieFieldMapping> getMappings() {
        return mappings;
    }

    public void setMappings(List<MovieFieldMapping> mappings) {
        this.mappings = mappings;
    }

    public String getNumDistFunction() {
        return numDistFunction;
    }

    public void setNumDistFunction(String numDistFunction) {
        this.numDistFunction = numDistFunction;
    }

    public String getDistAlgorithm() {
        return distAlgorithm;
    }

    public void setDistAlgorithm(String distAlgorithm) {
        this.distAlgorithm = distAlgorithm;
    }

    public double[] getComponentWeights() {
        return componentWeights;
    }

    public void setComponentWeights(double[] componentWeights) {
        this.componentWeights = componentWeights;
    }

    /**
     * Distance between int values
     *
     * @param thisValue
     * @param thatValue
     * @param diffThreshold
     * @return
     */
    public double findIntDistance(int thisValue, int thatValue, double diffThreshold) {
        double distance = 1.0;

        if (max > min) {
            distance = ((double) (thisValue - thatValue)) / (max - min);
        } else {
            int max = thisValue > thatValue ? thisValue : thatValue;
            double diff = ((double) (thisValue - thatValue)) / max;
            if (diff < 0) {
                diff = -diff;
            }
            distance = diff > diffThreshold ? 1.0 : 0.0;
        }

        if (distance < 0) {
            distance = -distance;
        }

        return distance;
    }

    /**
     * Distance between double values
     *
     * @param thisValue
     * @param thatValue
     * @param diffThreshold
     * @return
     */
    public double findDoubleDistance(double thisValue, double thatValue, double diffThreshold) {
        double distance = 1.0;

        if (max > min) {
            distance = ((thisValue - thatValue)) / (max - min);
        } else {
            double max = thisValue > thatValue ? thisValue : thatValue;
            double diff = (thisValue - thatValue) / max;
            if (diff < 0) {
                diff = -diff;
            }
            distance = diff > diffThreshold ? 1.0 : 0.0;
        }

        if (distance < 0) {
            distance = -distance;
        }

        return distance;
    }

    public double getDistThreshold() {
        return distThreshold;
    }

    public void setDistThreshold(double distThreshold) {
        this.distThreshold = distThreshold;
    }

    public boolean isDistanceThresholdCrossed(double dist) {
        return distThreshold > 0.0 && dist > distThreshold;
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }

    public int getMaxTimeWindow() {
        return maxTimeWindow;
    }

    public long getMaxTimeWindowInMili() {
        return maxTimeWindow * 60 * 1000L;
    }

    public void setMaxTimeWindow(int maxTimeWindow) {
        this.maxTimeWindow = maxTimeWindow;
    }

    public String getContAttrDistanceFunction() {
        return contAttrDistanceFunction;
    }

    public void setContAttrDistanceFunction(String contAttrDistanceFunction) {
        this.contAttrDistanceFunction = contAttrDistanceFunction;
    }

    public double getFunctionThreshold() {
        return functionThreshold;
    }

    public void setFunctionThreshold(double functionThreshold) {
        this.functionThreshold = functionThreshold;
    }

    public double getImplodeThreshold() {
        return implodeThreshold;
    }

    public void setImplodeThreshold(double implodeThreshold) {
        this.implodeThreshold = implodeThreshold;
    }

    public double getExplodeThreshold() {
        return explodeThreshold;
    }

    public void setExplodeThreshold(double explodeThreshold) {
        this.explodeThreshold = explodeThreshold;
    }

    public double[] getPartWeights() {
        return partWeights;
    }

    public void setPartWeights(double[] partWeights) {
        this.partWeights = partWeights;
    }
}
