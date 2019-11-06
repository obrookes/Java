import java.util.Arrays;
import java.util.List;

class Display {

    private final int DEFAULT = 8;

    void showTable(Table t){

        int wd;
        if(getMaxTableStr(t) > DEFAULT){
            wd = getMaxTableStr(t);
        } else { wd = DEFAULT; }

        String[] header = t.getAllFieldNames();
        System.out.print(String.format("%-" + wd + "s %s", "Fields", "|"));
        for (String element : header) {
           System.out.print(String.format("%-" + wd + "s %s", element, "|" ));
        }
        System.out.println();

        for(int i = 0; i < t.getRowNumber(); i++){
          System.out.print(String.format("%-" + wd + "s %s", "Row: " + i, "|"));
          for(int j = 0; j < t.selectRecord(i).getFieldSize(); j++){
            System.out.print(String.format("%-" + wd + "s %s", t.selectRecord(i).getFieldData(j), "|" ));
          }
          System.out.println();
        }
    }

    int getMaxTableStr(Table t){

        int maxLen = 0;
        String maxField = null;

        for(int i = 0; i < t.getRowNumber(); i++){
          for(int j = 0; j < t.selectRecord(i).getFieldSize(); j++){
            if(t.selectRecord(i).getFieldData(j).length() > maxLen){
                maxLen = t.selectRecord(i).getFieldData(j).length();
                maxField = t.selectRecord(i).getFieldData(j);
            }
          }
        }
        String maxHeader = getMaxHeader(t.getAllFieldNames());
        if(maxField.length() >= maxHeader.length()){
            return maxField.length();
        }
        else {
            return maxHeader.length();
        }
    }

    String getMaxHeader(String[] array){
        int maxLength = 0;
        String longestString = null;
        for (String s : array) {
            if (s.length() > maxLength) {
                maxLength = s.length();
                longestString = s;
            }
        }
        return longestString;
    }

    public static void main(String[] args) {
        System.out.println("No tests required for display methods");
    }
}
