import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Table {

    private String Name;
    private int fieldNumber;
    private String[] fieldNames;
    private List<Record> Table;
    private int currentKey = 0;

    void tableInit(){
        this.Table = new ArrayList<>();
    }

    void setFieldNumber(int i){
        this.fieldNumber = i;
    }

    int getFieldNumber(){
        return this.fieldNumber;
    }

    int countFieldNames(){
      return this.fieldNames.length;
    }

    void nameTable(String name){
        this.Name = name;
    }

    String getName(){
        return this.Name;
    }

    List getTable(){
      return this.Table;
    }

    void setFieldNames(String ... field){

        if(this.fieldNumber != field.length){
            System.out.println("The number of fields does not match the number of arguments\n");
            throw new IllegalArgumentException();
        }
        this.fieldNames = new String[field.length];
        this.fieldNumber = field.length;

        for(int i = 0; i < field.length; i++){
            this.fieldNames[i] = field[i];
        }
        tableInit();
    }

    String getFieldNames(int i){
        return this.fieldNames[i];
    }

    String[] getAllFieldNames(){
        return this.fieldNames;
    }

    void extendTableHeader(int numberOfNewCols, String ... newColNames){
        if(numberOfNewCols != newColNames.length){
            System.out.println("The specified number of columns does not match the number of headers\n");
            throw new IllegalArgumentException();
        }

        for(int i = 0; i < numberOfNewCols; i++){
            if(Arrays.asList(this.fieldNames).contains(newColNames[i])){
                System.out.println("The column name " + newColNames[i] + " already exists\n");
                throw new IllegalArgumentException();
            }
        }

        int oldFieldNumber = this.fieldNumber;
        this.fieldNumber = this.fieldNumber + numberOfNewCols;
        this.fieldNames = Arrays.copyOf(this.fieldNames, this.fieldNumber);

        int j = 0;
        for(int i = oldFieldNumber; i < fieldNames.length; i++){
            fieldNames[i] = newColNames[j];
            j++;
        }
    }

    void extendTable(){
        for(int i = 0; i < this.Table.size(); i++){
            this.Table.get(i).extendRecord(this.fieldNumber);
        }
    }

    int getRowNumber(){
        return this.Table.size();
    }

    int getRow(int key){
        if(key >= currentKey || key < 0){
            System.out.println("No record with this key exists\n");
            throw new IllegalArgumentException();
        }

        int i = 0;
        while(this.Table.get(i).getKey() != key){
            i++;
        }
        return i;
    }

    void deleteRecords(int ... keys){
        for(int i = 0; i < keys.length; i++){
            int rowForDeletion = getRow(keys[i]);
            if(rowForDeletion >= 0 && rowForDeletion < this.Table.size()){
                this.Table.remove(rowForDeletion);
            }
        }
    }

    void deleteRecord(int row){
      this.Table.remove(row);
    }

    void addRecord(String ... field){ // TODO: need to check data types match before insertion - part of extension!
        String test[] = new String[]{"Good", "Better", "Great"};

        if(field.length != fieldNumber){
            System.out.println("The record does not match the current table\n");
            throw new IllegalArgumentException();
        }

        Record rec = new Record();
        rec.DbRecord(getCurrentKey(), this.fieldNumber, field);

        for(int i = 0; i < this.Table.size(); i++){
          if(selectRecord(i).getKey()== rec.getKey()){
              System.out.println("The new record's key is not unique!\n");
              throw new IllegalArgumentException();
          }
        }
        this.Table.add(rec);
        generateNextKey();
    }

    Record selectRecord(int row){
        return this.Table.get(row);
    }

    void updateRecord(int[] keys, int fIndex, String newVal){
        if(fIndex > this.fieldNumber){
            System.out.println("The fields being updated are out of bounds\n");
            throw new IllegalArgumentException();
        }

        for(int i = 0; i < this.fieldNumber; i++){
            this.Table.get(getRow(keys[i])).setFieldData(fIndex, newVal);
        }
    }

    int getFieldIndex(String fieldName){
      for(int i = 0; i < this.fieldNumber; i++){
        if(this.fieldNames[i].equals(fieldName)){
          return i;
        }
      }
      System.out.println("There are no field names called " + fieldName);
      throw new IllegalArgumentException();
    }

    void generateNextKey(){
        this.currentKey++;
    }

    int getCurrentKey(){
        return this.currentKey;
    }

    /* Main */

    public static void main(String[] args) {
        Table test = new Table();
        test.runTests();
    }

    private void runTests() {
        boolean testing = false;
        assert(testing = true);
        testGetRow();
        testNameTable();
        testSetFieldNumber();
        testAddRecord();
        testDeleteRecords();
        testExtendTable();
        testExtendTable();
        System.out.println("Table Class - all tests pass");
    }

    /* Test Functions */

    void testExtendTable(){ // Includes testing for extendTableHeader too
        setFieldNumber(3);
        setFieldNames("H", "H1", "H2");

        addRecord("Hello", "Again", "World");
        addRecord("Hey", "There", "Globe");
        addRecord("Hi", "Lonely", "Planet");

        extendTableHeader(2, "Good", "Bye");
        assert(this.fieldNumber == 5);
        assert(this.fieldNames.length == 5);
        assert(this.fieldNames[3].equals("Good"));
        assert(this.fieldNames[4].equals("Bye"));

        extendTable();
        assert(this.Table.get(0).getFieldSize() == 5);
        assert(this.Table.get(1).getFieldSize() == 5);
        assert(this.Table.get(2).getFieldSize() == 5);
    String test[] = new String[]{"Good", "Better", "Great"};

        assert(this.Table.get(0).getFieldData(0).equals("Hello"));
        assert(this.Table.get(1).getFieldData(0).equals("Hey"));
        assert(this.Table.get(0).getFieldData(3) == null);
        assert(this.Table.get(1).getFieldData(4) == null);

        currentKey = 0;
    }

    void testDeleteRecords(){ // Also includes getRow testing
        setFieldNumber(3);
        setFieldNames("H", "H1", "H2");
        addRecord("Hello", "Again", "World");
        addRecord("Hey", "There", "Globe");
        addRecord("Hi", "Lonely", "Planet");
        assert(getRowNumber() == 3);
        deleteRecords(0, 1);
        assert(this.Table.get(0).getFieldData(0).equals("Hi"));
        assert(getRowNumber() == 1);
        currentKey = 0;
    }

    void testGetRow(){
        setFieldNumber(3);
        setFieldNames("H", "H1", "H2");
        addRecord("Hello", "Again", "World");
        addRecord("Hey", "There", "Globe");
        addRecord("Hi", "Lonely", "Planet");
        assert(currentKey == 3);
        currentKey = 0;
    }

    void testAddRecord(){
        setFieldNumber(3);
        setFieldNames("H", "H1", "H2");
        addRecord("Hello", "Again", "World");
        assert(this.Table.get(0).getFieldData(0).equals("Hello"));
        assert(this.Table.get(0).getFieldData(1).equals("Again"));
        assert(currentKey == 1);
        currentKey = 0;
    }

    void testSetFieldNames(){
        setFieldNumber(3);
        setFieldNames("Hello", "Again", "World");
        assert(this.fieldNames[1].equals("Again"));
        assert(this.fieldNames[2].equals("World"));
        assert(this.fieldNames.length == 3);
    }

    void testNameTable(){
        nameTable("Table");
        assert(this.Name.equals("Table"));
    }

    void testSetFieldNumber(){
        setFieldNumber(10);
        assert(this.fieldNumber == 10);
    }
}
