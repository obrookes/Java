import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileWriter;

class DBFile {
    private String fileName;
    private ArrayList<String> exportFile;
    private List<String> tableFile;
    private int lineCounter = 0;
    private String[] headerArray;
    private String[] tableName;

    private final int NAME = 2;
    private final int TABLENAME = 0;
    private final int HEADER = 1;
    private final String EXTENSION = "txt";

    /* Export */

    void exportTable(String folder, String fileName){
        String ext = fileName.substring(fileName.lastIndexOf('.') + 1);

        if(ext.equals(EXTENSION) == false){
            System.out.println("Error - please ensure the filename entered is terminated by '.txt'\n");
            throw new IllegalArgumentException();
        }
        if(!folder.endsWith("/")){
          folder = folder + "/";
        }
        try {
            FileWriter writer = new FileWriter(folder + fileName);
            for(String str: this.exportFile) {
                writer.write(str);
            }
            writer.close();
        }
        catch(Exception e){
            throw new Error(e);
        }
    }

    void inputTable(Table t){

        this.fileName = t.getName();

        this.exportFile = new ArrayList<String>();
        exportFile.add("Name: " + t.getName() + System.getProperty("line.separator"));
        exportFile.add(stringFromHeader(t.getAllFieldNames()) + System.getProperty("line.separator"));

        for(int i = 0; i < t.getRowNumber(); i++){
            exportFile.add(stringFromRec(t.selectRecord(i)) + System.getProperty("line.separator"));
        }
    }

    String stringFromHeader(String[] header){
        String str = String.join(" ", header);
        return str;
    }

    String stringFromRec(Record rec){
        String[] array = rec.getRecord();
        String str = String.join(" ", array);
        return str;
    }

    /* Import */

  void getTable(String fileName){
    try {
      File file = new File(fileName);
      this.tableFile = new ArrayList<String>();
      Scanner in = new Scanner(file);
      while (in.hasNextLine()) {
        String line = in.nextLine();
        this.tableFile.add(line);
      }
      in.close();
    }
    catch (Exception e){
      throw new Error(e);
    }
    getName();
    processHeader();
  }

    void getName(){
        String tableName = new String();
        tableName = tableFile.get(TABLENAME);
        this.tableName = tableName.trim().split(":|\\s+");
        lineCounter++;
    }

    void processHeader(){
        String headerString = new String();
        headerString = tableFile.get(HEADER);
        this.headerArray = headerString.trim().split("\\s+");
        lineCounter++;
    }

    int getHeaderSize(){
      return this.headerArray.length;
    }

    String[] returnHeader(){
        return this.headerArray;
    }

    String[] processLine(int i){
        String dirtyLine = new String();
        dirtyLine = tableFile.get(i);
        String[] cleanLine = dirtyLine.trim().split("\\s+");
        return cleanLine;
    }

    void completeTable(Table t){
        t.nameTable(tableName[NAME]);
        t.setFieldNames(this.headerArray);
        if(lineCounter == 0){
            System.out.println("Cannot process Header as a Record\n");
            throw new IllegalArgumentException();
        }
        for(int i = lineCounter; i < tableFile.size(); i++){
            t.addRecord(processLine(i));
        }
        this.lineCounter = 0;
    }

    int getLineCount(){
      return this.lineCounter;
    }

    // Main

    void runTests(){
        testExport();
        testCompleteTable();
        testGetHeader();
        System.out.println("File Class - all tests pass");
    }

    public static void main(String[] args){
        DBFile program = new DBFile();
        program.runTests();
    }

    // Testing

    void testExport(){
        Table test = new Table();
        test.nameTable("Test");
        test.setFieldNumber(3);
        test.setFieldNames("H", "H1", "H2");

        test.addRecord("Hello", "Again", "World");
        test.addRecord("Hey", "There", "Globe");
        test.addRecord("Hi", "Lonely", "Planet");
        inputTable(test);
        exportTable("test", "test.txt");
    }

    void testGetHeader(){
        getTable("table.txt");
        processHeader();
        assert(headerArray[0].equals("Id"));
        assert(headerArray[1].equals("Name"));
        assert(headerArray[2].equals("Kind"));
        assert(headerArray.length == 4);
    }

    void testCompleteTable(){
        getTable("table.txt");
        Table test = new Table();
        test.setFieldNumber(4);
        completeTable(test);
        assert(test.getName().equals("Test"));
        assert(test.getFieldNames(0).equals("Id"));
        assert(test.getFieldNames(1).equals("Name"));
        assert(test.getFieldNames(2).equals("Kind"));

        assert(test.selectRecord(0).getFieldData(0).equals("1"));
        assert(test.selectRecord(0).getFieldData(1).equals("Fido"));
        assert(test.selectRecord(1).getFieldData(0).equals("2"));
    }
}
