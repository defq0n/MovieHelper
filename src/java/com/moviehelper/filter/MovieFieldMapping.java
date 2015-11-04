/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moviehelper.filter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Stephen
 */
public class MovieFieldMapping implements Serializable {
    private int matchingOrdinal = -1;
    private List<MovieFieldMapping.ValueMapping> valueMappings;

    public int getMatchingOrdinal() {
        return matchingOrdinal;
    }

    public void setMatchingOrdinal(int matchingOrdinal) {
        this.matchingOrdinal = matchingOrdinal;
    }

    public List<MovieFieldMapping.ValueMapping> getValueMappings() {
        return valueMappings;
    }

    public void setValueMappings(List<MovieFieldMapping.ValueMapping> valueMappings) {
        this.valueMappings = valueMappings;
    }

    public static class ValueMapping {

        private String thisValue;
        private int[] thisValueRange;
        private String thatValue;

        public String getThisValue() {
            return thisValue;
        }

        public void setThisValue(String thisValue) {
            this.thisValue = thisValue;
        }

        public int[] getThisValueRange() {
            return thisValueRange;
        }

        public void setThisValueRange(int[] thisValueRange) {
            this.thisValueRange = thisValueRange;
        }

        public String getThatValue() {
            return thatValue;
        }

        public void setThatValue(String thatValue) {
            this.thatValue = thatValue;
        }
    }
}
