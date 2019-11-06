import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileWriter;

class Manager {

  Database db;
  Table tb = new Table();
  Display display = new Display();
  DBFile dbfile = new DBFile();

  void getCommand(){
    System.out.print(">> ");
    Scanner scan= new Scanner(System.in);
    String command = scan.nextLine();
    processCommand(cleanCommand(command));
    }

  String cleanCommand(String command){
    String str = command.trim();
    return str;
  }

  void newDB(){
    this.db = new Database();
    System.out.print(">> Please provide a DB name: ");
    Scanner scan= new Scanner(System.in);
    String name = scan.nextLine();
    db.createDb(name);
    getCommand();
  }

  void newTable(){
    if(this.db == null){
        System.out.println(">> There is currently no database selected - please create or load one from memory\n");
        getCommand();
    }
    System.out.print(">> Please provide a table name: ");
    Scanner scan= new Scanner(System.in);
    String name = scan.nextLine();
    db.createTable(name);

    System.out.print(">> Please provide number of fields: ");
    String fieldNo = scan.nextLine();
    int fieldNum = Integer.parseInt(fieldNo);
    db.selectTable(name).setFieldNumber(fieldNum);
    //TODO: need to check valid number here

    System.out.println(">> Please enter the field names below:");
    String[] inputs = new String[fieldNum];
    for (int i = 0; i < fieldNum; i++){
        inputs[i] = scan.nextLine();
    }
    db.selectTable(name).setFieldNames(inputs);
    getCommand();
  }

  void newRecord(){
    if(this.db == null){
        System.out.println(">> There is currently no database selected - please create or load one from memory\n");
        getCommand();
    }
    else {
        System.out.print(">> Please select a table: ");
        Scanner scan= new Scanner(System.in);
        String name = scan.nextLine();

        int fieldNo = db.selectTable(name).getFieldNumber(); // is there error checking here?
        System.out.println(">> Please enter data below: ");
        String []inputs = new String[fieldNo];
        for (int i = 0; i < fieldNo; i++){
            inputs[i] = scan.nextLine();
        }
        db.selectTable(name).addRecord(inputs);
        getCommand();
    }
  }

  void deleteRow(String table){
    Scanner scan= new Scanner(System.in);
    System.out.print(">> Specify record (by row) for deletion: ");
    String r = scan.nextLine();
    int row = Integer.parseInt(r);

    db.selectTable(table).deleteRecord(row);
    System.out.println(">> The record at row " + row + " has been deleted");
    System.out.println(">> Please save the db to ensure changes are permanent");
  }

  void editTable(){
    if(this.db == null){
        System.out.println(">> There is currently no database selected - please create or load one from memory\n");
        getCommand();
    }
    else {
        System.out.print(">> Please select a table: ");
        Scanner scan= new Scanner(System.in);
        String name = scan.nextLine();
        display.showTable(db.selectTable(name));

        System.out.println(">> Do you want to delete or modify records?");
        System.out.print(">> Please enter 'D' for delete or 'E' for edit: ");
        String option = scan.nextLine();
        if(option.equals("D")){
            deleteRow(name);
        }
        else {
            System.out.print(">> Specify record (by row) for editing: ");
            String r = scan.nextLine();
            int row = Integer.parseInt(r);

            System.out.print(">> Specify the column you would like to edit: ");
            String column = scan.nextLine();
            int fieldIndex = db.selectTable(name).getFieldIndex(column);

            System.out.print(">> Enter new data: ");
            String data = scan.nextLine();

            db.selectTable(name).selectRecord(row).setFieldData(fieldIndex, data);

            System.out.println(">> Updated table: ");
            display.showTable(db.selectTable(name));
       }
    }
  }

  void removeTable(){
    if(this.db == null){
        System.out.println(">> There is currently no database selected - please create or load one from memory\n");
        getCommand();
    }
    else {
        Scanner scan= new Scanner(System.in);
        System.out.print(">> Please specify directory: ");
        String folder = scan.nextLine();

        System.out.print(">> Please specify table for deletion: ");
        String name = scan.nextLine();

        db.deleteTable(name); // delete from Java store

        String filepath = folder + "/" + name + ".txt";

        File file = new File(filepath);
        if(file.delete()){
            System.out.println(">> The file has been deleted from the " + folder + " directory");
        }
        else {
            System.out.println(">> " + filepath + " doesn't exist - please check the directory is correct");
        }
    }
  }

  void showTable(){
    if(this.db == null){
      System.out.println(">> Cannot show table as there is currently no database selected - please create or load one from memory");
      getCommand();
    }
    else {
      System.out.print(">> Choose table to display: ");
      Scanner scan= new Scanner(System.in);
      String name = scan.nextLine();
      display.showTable(db.selectTable(name));
      getCommand();
    }
  }

  void listTables(){
    if(this.db == null){
      System.out.println(">> The db is empty");
      getCommand();
    }
    String[] list = db.listTables();
    for(int i = 0; i < list.length; i++){
      System.out.println("Table " + i + ": " + list[i]);
    }
    getCommand();
  }

  void saveDB(){
    if(this.db == null){
        System.out.println(">> There is currently no database selected - please create or load one from memory\n");
        getCommand();
    }
    else {
        System.out.print(">> Please detail the database directory: ");
        Scanner scan= new Scanner(System.in);
        String dirName = scan.nextLine();
        if(doesDirExist(dirName)){
            for (int i = 0; i < db.getSize(); i++){
                dbfile.inputTable(db.getTable(i));
                dbfile.exportTable(dirName, db.getTable(i).getName() + ".txt");
                getCommand();
            }
        }
        else {
            System.out.println(">> The directory you specified didn't exist so we've made you a new one...");
            File dir = new File(dirName);
            boolean created = dir.mkdir();
            for (int i = 0; i < db.getSize(); i++){
                dbfile.inputTable(db.getTable(i));
                dbfile.exportTable(dirName, db.getTable(i).getName() + ".txt");
                getCommand();
            }
        }
    }
  }

  boolean doesDirExist(String filepath){
    File dir = new File(filepath);
    boolean exists = dir.exists();
    if(exists){
      return true;
    }
    else {
      return false;
    }
  }

   void loadDB(){
    System.out.print(">> Please detail the correct dir: ");
    Scanner scan= new Scanner(System.in);
    String dirName = scan.nextLine();

    if(!doesDirExist(dirName)){
        System.out.println(">> The directory " + dirName + " does not exist\n");
        getCommand();
    }
    else {
        this.db = new Database();
        db.createDb(dirName);

        File dir = new File(dirName + "/");
        String[] filesInDir = dir.list();

        for(int i = 0; i < filesInDir.length; i++){
            dbfile.getTable(dirName + "/" + filesInDir[i]);
            Table t = new Table();
            t.setFieldNumber(dbfile.getHeaderSize());
            dbfile.completeTable(t);
            db.addTable(t);
        }
        getCommand();
    }
  }

  void help(){
    String information =
    "\nCommand help - please see below for a list of commands and how they work:\n" +
    "1) create db - initialise a new database in the current working directory\n" +
    "2) add table - add a new table to the current database\n" +
    "3) delete table - delete a table from the current database\n" +
    "4) add record - add a new record to a table in the current database\n" +
    "5) edit table - modify the existing records or delete records from a table\n" +
    "6) list tables - lists all tabes in the current database\n" +
    "7) show table - displays the full table and its current data\n" +
    "8) save db - saves the tables in the database as .txt files in a specified directory\n" +
    "9) load db - loads the tables in a specified directory into the database\n" +
    "10) help - displays the Command help\n" +
    "11) exit - exits the database\n";
    System.out.println(information);
    getCommand();
  }

  void processCommand(String command){
    switch (command) {
      case "create db":
        newDB();
        break;
      case "add table":
        newTable();
        break;
      case "add record":
        newRecord();
        break;
      case "edit table":
        editTable();
        break;
      case "delete table":
        removeTable();
        break;
      case "list tables":
        listTables();
        break;
      case "show table":
        showTable();
        break;
      case "save db":
        saveDB();
        break;
      case "load db":
        loadDB();
        break;
      case "help":
        help();
        break;
      case "exit":
        break;
      default:
      System.out.print(">> Invalid command\n>> Type 'help' for a list of valid commands\n");
      getCommand();
    }
  }

  public static void main(String[] args) {
      Manager manager = new Manager();
      manager.welcome();
  }

  private void welcome() {
      System.out.println("Welcome! Please type 'help' to get started\n");
      getCommand();

  }
}
