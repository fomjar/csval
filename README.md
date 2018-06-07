# CSVal
Implementation of Java version for CSV file processing

## How easy?

```Java
CSVal csv = new CSVal();
// read & write
csv.read(inputStream);   // csv.read(file);
csv.write(outputStream); // csv.write(file);
// value access
System.out.println(Arrays.asList(csv.head()).toString());
csv.body().forEach(vals -> System.out.println(Arrays.asList(vals).toString()));
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

