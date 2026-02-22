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
        List<String[]> selected = new ArrayList<>();
        for (int idx : rows) {
            if (idx >= 0 && idx < data.length) {
                selected.add(data[idx].clone());
            }
        }
        return new DataFrame(selected.toArray(new String[0][]), this.columns.clone());
    }    
    
    public DataFrame select(String [] values){
        // Resolve column indices in the requested order
        List<Integer> colIdxs = new ArrayList<>();
        List<String> newCols = new ArrayList<>();
        for (String v : values) {
            for (int j = 0; j < columns.length; j++) {
                if (columns[j].equals(v)) {
                    colIdxs.add(j);
                    newCols.add(v);
                    break;
                }
            }
        }
        String[] newColumns = newCols.toArray(new String[0]);
        String[][] newData = new String[data.length][colIdxs.size()];
        for (int i = 0; i < data.length; i++) {
            for (int k = 0; k < colIdxs.size(); k++) {
                newData[i][k] = data[i][colIdxs.get(k)];
            }
        }
        return new DataFrame(newData, newColumns);
    }  
    
        /**
     * Returns a new DataFrame with rows that satisfy all non-null conditions.
     * parameters has one entry per column (same order as columns array).
     * null means "don't care"; any other value means the cell must equal it.
     * All columns are always returned.
     * @param parameters per-column filter values (null = any value accepted)
     * @return new DataFrame with only the matching rows
     */
    public DataFrame filter(String[] parameters) {
        List<String[]> selected = new ArrayList<>();
        for (String[] row : data) {
            boolean match = true;
            for (int j = 0; j < parameters.length && j < columns.length; j++) {
                if (parameters[j] != null && !parameters[j].equals(row[j])) {
                    match = false;
                    break;
                }
            }
            if (match) selected.add(row.clone());
        }
        return new DataFrame(selected.toArray(new String[0][]), columns.clone());
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
            for(int j=0;j<rows;j++){
                sb.append(sep);
                sb.append(data[i][j].substring(0,columns[i].length()));
            }
        }
        return sb.toString();
    }
    
    public boolean equals(DataFrame df){
        if (!Arrays.equals(this.columns, df.columns)) return false;
        if (this.data.length != df.data.length) return false;
        for (int i = 0; i < data.length; i++) {
            if (!Arrays.equals(this.data[i], df.data[i])) return false;
        }
        return true;
    }
    
    public boolean equals(Object o){
        if (!(o instanceof DataFrame)) return false;
        return equals((DataFrame) o);
    }
}
