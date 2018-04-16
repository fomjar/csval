# CSVal
Implementation of Java version for CSV file processing

## How easy?

```Java
CSVal csv = new CSVal();
csv.read(inputStream);   // csv.read(file);
csv.write(outputStream); // csv.write(file);
System.out.println(csv.toString());
```

## Deal with huge files?

```Java
CSVal csv = new CSVal();
csv.read(file, new CSValReader() {
    @Override
    public void line(int row, String[] vals) {
        System.out.println(String.format("[ROW %08d] %s", row, Arrays.asList(vals).toString());
    }
});
```

