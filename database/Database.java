import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.FileSystemException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Database {

    private String name;
    private ArrayList<Table> tables;

    void createDb(String name){
        this.name = name;
        assert(this.name.equals(name));
        this.tables = new ArrayList<>();
    }

    void createTable(String name){
        Table t = new Table();
        t.nameTable(name);
        tables.add(t);
    }

    void addTable(Table t){
      this.tables.add(t);
    }

    Table deleteTable(String name){
        for(int i = 0; i < this.tables.size(); i++){
            if(this.tables.get(i).getName().equals(name)){
                return this.tables.remove(i);
            }
        }
        System.out.println("There are no tables titled" + name + "\n");
        throw new IllegalArgumentException();
    }

    Table getTable(int i){
      return tables.get(i);
    }

    int getSize(){
      return this.tables.size();
    }

    Table selectTable(String name){
        for(int i = 0; i < this.tables.size(); i++){
            if(this.tables.get(i).getName().equals(name)){
                return this.tables.get(i);
            }
        }
        System.out.println("There are no tables titled" + name + "\n");
        throw new IllegalArgumentException();
    }

    String[] listTables(){
        String[] list = new String[this.tables.size()];
        for(int i = 0; i < this.tables.size(); i++){
            list[i] = this.tables.get(i).getName();
        }
        return list;
    }

    public static void main(String[] args) {
        Database test = new Database();
        test.runTests();
    }

    private void runTests() {
        boolean testing = false;
        assert(testing = true);
        testDeleteTable();
        testListTable();
        System.out.println("Database Class - all tests pass");
    }

    void testDeleteTable(){
        createDb("Test");
        createTable("T0");
        assert(this.tables.get(0).getName().equals("T0"));
        deleteTable("T0");
        assert(this.tables.size() == 0);
    }

    void testListTable(){
        createDb("Test");
        createTable("T0");
        createTable("T1");
        createTable("T2");

        assert(this.tables.get(0).getName().equals("T0"));
        assert(this.tables.get(1).getName().equals("T1"));
        assert(this.tables.get(2).getName().equals("T2"));

        String[] list = listTables();
        assert(list[0].equals("T0"));
        assert(list[1].equals("T1"));
        assert(list[2].equals("T2"));
    }





}
