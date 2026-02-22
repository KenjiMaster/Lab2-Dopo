import java.util.*;
public class DataFrame {
    
    private String [][] data;
    private String [] columns;
    
    public DataFrame(){
        data = new String[][] {};
        columns = new String [] {};
    }
    
    public DataFrame(String [][] data, String [] columns){
        List<String[]> repaso = new ArrayList<>();
        for(int i=0; i<data.length;i++){
            if(data[i].length == columns.length){
                repaso.add(data[i]);
            }
        }
        String [][] newData = new String[repaso.size()][columns.length];
        for(int i=0; i<repaso.size();i++) newData[i] = repaso.get(i); 
        this.data = newData;
        this.columns = columns;
    }
    
    public DataFrame loc(int [] rows, String columns){
        return null;
    }    
    
    public DataFrame select(String [] values){
        return null;
    }      


    public DataFrame concat(DataFrame [] dfs, byte axis){
        return null;
    }

    public int [] shape(){
        return new int[] {data.length,columns.length};
    }    
    
    // The columns are aligned, separated by three spaces, and include the index.
    //     Nombre   Edad    Profesion
    // 0    Lucía     28    Ingeniero
    // 1   Carlos     35     Profesor
    // 2      Ana     42       Doctor
    // 3    Jorge     30   Arquitecto
    // 4    Elena     25    Diseñador
    public String head(int rows) {
        StringBuilder sb = new StringBuilder();
        String sep = "   ";
        for(int i=0;i<columns.length;i++){
            sb.append(sep);
            sb.append(" ");
            sb.append(columns[i]);
        }
        for(int i=0;i<columns.length;i++){
            sb.append("\n");
            sb.append(String.valueOf(i));
            for(int j=0;j<data.length;j++){
                sb.append(sep);
                sb.append(data[i][j].substring(0,columns[i].length()));
            }
        }
        return sb.toString();
    }
    
    public boolean equals(DataFrame df){
        return false;
    }
    
    public boolean equals(Object o){
        return equals((DataFrame)o);
    }
}
