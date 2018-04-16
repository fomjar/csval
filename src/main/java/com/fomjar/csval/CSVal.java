package com.fomjar.csval;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * indicate to a csv structure.
 * 
 * @author fomjar
 */
public abstract class CSVal {
    
    /**
     * csv configuration class.
     * 
     * @author fomjar
     */
    public static class Conf {
        public String   saparator   = ",";
        public String   charset     = "utf-8";
        public String   wordends    = "\"";
    }
    
    private Conf            conf;
    private String[]        head;
    private List<String[]>  body;
    
    /** get csv configuration. */
    public Conf             conf()              {return conf;}
    /** get head string array*/
    public String[]         head()              {return head;}
    /** get some head string at the given index */
    public String           head(int c)         {return head[c];}
    /** reset head string array */
    public void             head(String[] head) {this.head = head;}
    /** get body list */
    public List<String[]>   body()              {return body;}
    /** get body string array at given row number*/
    public String[]         body(int r)         {return body.get(r);}
    /** get body string array at given row and column number */
    public String           body(int r, int c)  {return body.get(r)[c];}
    
    public CSVal() {
        this(new Conf());
    }
    
    public CSVal(Conf conf, String... head) {
        this.conf = conf;
        this.head = head;
        this.body = new ArrayList<>(0);
    }
    
    /**
     * read from a file.
     * 
     * @param file the file to read from
     * @throws IOException
     */
    public void read(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        try     {read(fis);}
        finally {fis.close();}
    }
    
    /**
     * read from an input stream.
     * 
     * @param is input stream to read from
     * @throws IOException
     */
    public void read(InputStream is) throws IOException {
        read(is, new DefaultCSValReader(this));
    }
    
    /**
     * read from a file, and consume csv data with a customized reader.
     * 
     * @param file file to read from
     * @param reader the reader which gives an implementation to consume data
     * @throws IOException
     */
    public void read(File file, CSValReader reader) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        try     {read(fis, reader);}
        finally {fis.close();}
    }
    
    /**
     * read from an input stream, and consume csv data with a customized reader.
     * 
     * @param is input stream to read from
     * @param reader the reader which gives an implementation to consume data
     * @throws IOException
     */
    public void read(InputStream is, CSValReader reader) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is, this.conf.charset));
        String line = null;
        int row = 0;
        while (null != (line = br.readLine())) {
            if (0 == line.trim().length()) continue;    // skip empty line
            
            String[] vals = line.split(this.conf.saparator);
            for (int i = 0; i < vals.length; i++) {
                String val = vals[i];
                val = val.trim();
                int beg = val.indexOf(this.conf.wordends);
                int end = val.lastIndexOf(this.conf.wordends);
                if (0 > beg) {                          // no word ends
                    // do nothing
                } else if (0 <= beg && beg == end) {    // only one word end
                    // do nothing
                } else {                                // more than word ends
                    // cut out string
                    val = val.substring(beg + 1, end);
                }
                val = val.intern(); // save memory
                vals[i] = val;
            }
            reader.line(++row, vals);
        }
    }
    
    /**
     * write csv data to a file.
     * 
     * @param file file to write.
     * @throws IOException
     */
    public void write(File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        try     {write(fos);}
        finally {fos.close();}
    }
    
    private String buildLine(String[] vals) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vals.length; i++) {
            if (0 == i) sb.append(String.format("%s%s%s", this.conf.wordends, vals[i], this.conf.wordends));
            else        sb.append(String.format(",%s%s%s", this.conf.wordends, vals[i], this.conf.wordends));
        }
        return sb.toString();
    }
    
    /**
     * write csv data to an output stream.
     * 
     * @param os output stream to write
     * @throws IOException
     */
    public void write(OutputStream os) throws IOException {
        String lineSeparator = System.getProperty("line.separator");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, this.conf.charset));
        bw.write(buildLine(head()));
        for (String[] vals : body()) {
            bw.write(lineSeparator);
            bw.write(buildLine(vals));
        }
        bw.flush();
    }
    
}
