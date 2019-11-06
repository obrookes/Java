import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Record {

    private int key;
    private String[] record;

    void DbRecord(int key, int size, String ... field){
        this.record = new String[size];
        for(int i = 0; i < field.length; i++){
            this.record[i] = field[i];
        }
        setKey(key);
    }

    void setFieldData(int index, String val){
        this.record[index] = val;
        assert(this.record[index].equals(val));
    }

    String getFieldData(int index){
        return this.record[index];
    }

    int getFieldSize(){
        return this.record.length;
    }

    int getKey(){
        return this.key;
    }

    void setKey(int i){
        this.key = i;
        assert(this.key == i);
    }

    String[] getRecord(){
        return this.record;
    }

    void extendRecord(int newSize){
        this.record = Arrays.copyOf(this.record, newSize);
    }

    public static void main(String[] args) {
        Record test = new Record();
        test.runTests();
    }

    private void runTests() {
        boolean testing = false;
        assert(testing = true);
        testDbRecord();
        testExtendRecord();
        System.out.println("Record Class - all tests pass");
    }

    void testDbRecord(){
        DbRecord(0, 3, "Good", "Better", "Great");
        assert(getFieldData(0).equals("Good"));
        assert(getFieldData(1).equals("Better"));

        assert(getKey() == 0);
        assert(getFieldSize() == 3);

        setFieldData(0, "Bad");
        assert(getFieldData(0).equals("Bad"));
    }

    void testExtendRecord(){
        DbRecord(0, 3, "Good", "Better", "Great");
        extendRecord(5);

        assert(getFieldData(0).equals("Good"));
        assert(getFieldData(1).equals("Better"));
        assert(getFieldData(3) == null);
        assert(getFieldData(4) == null);

        assert(getFieldSize() == 5);
    }

}
