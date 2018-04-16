package com.fomjar.csval;

/**
 * use this for huge csv files.
 * 
 * @author fomjar
 */
public interface CSValReader {
    
    /**
     * consume one line data.
     * 
     * @param row row number starts with 1
     * @param vals values of this row
     */
    void line(int row, String[] vals);

}
