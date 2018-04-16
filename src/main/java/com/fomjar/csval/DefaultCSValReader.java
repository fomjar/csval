package com.fomjar.csval;

/**
 * default csv reader implementation.
 * 
 * @author fomjar
 */
class DefaultCSValReader implements CSValReader {
    
    private CSVal csv;
    
    public DefaultCSValReader(CSVal csv) {
        this.csv = csv;
    }

    @Override
    public void line(int row, String[] vals) {
        switch (row) {
        case 0:
            this.csv.head(vals);
            break;
        default:
            this.csv.body().add(vals);
            break;
        }
    }

}
