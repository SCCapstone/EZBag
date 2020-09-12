package org.services;

public class Utils {
    public static boolean isEAN(String barcode)
    {
        return barcode.length()==13;
    }
    public static boolean isUPC(String barcode)
    {
        return barcode.length()==12;
    }
}
