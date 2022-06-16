package com.alzios.api.utils;

public class Utils {

    public static double gauss(double x) {
        return Math.exp(-Math.pow((x-1),2)/2) / Math.sqrt(2*Math.PI);
    }
}
