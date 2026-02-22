import java.util.HashMap;

/** BabyPandas.java
 * 
 * @author ESCUELA 2026-01
 */
    
public class BabyPandas{
    
    private HashMap<String,DataFrame> variables;
    
    public BabyPandas(){
        variables = new HashMap<>();
    }

    //Definea new variable
    public void define(String name){
        if(!variables.containsKey(name)){
            variables.put(name,new DataFrame());
        }
    }
     
    //Assign a DataFrame to an existing variable
    //a := DataFrame
    public void assign(String variable, DataFrame df){
        if(variables.containsKey(variable)){
            variables.put(variable,df);
        }
    }    
    
    //Return DataFrame's shape
    public int[] shape(String variable){
        if(variables.containsKey(variable)){
            return variables.get(variable).shape();
        }
        return new int[] {0,0};
    }
    
    
    //Assigns the value of a unary operation to a variable
    // a = b op parameters
    //The operator characters are: 'r' select rows, 'c' select columns, '?' select condition
    //The parameters for 'r' are [index1, index2, ...]
    //The parameters for 'c' are [column1, column2, ...]
    //The parameters for '?' are [valueColumn1, valueColumn2, ...]

    public void assignUnary(String a, String b, char op, String [] parameters){
        if (!variables.containsKey(a) || !variables.containsKey(b)) return;
        DataFrame source = variables.get(b);
        DataFrame result = null;

        if (op == 'r') {
            // Convert string indices to int[]
            int[] rows = new int[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                rows[i] = Integer.parseInt(parameters[i]);
            }
            result = source.loc(rows, null); // second param ignored inside loc

        } else if (op == 'c') {
            result = source.select(parameters);

        } else if (op == '?') {
            result = source.filter(parameters);
        }

        if (result != null) {
            variables.put(a, result);
        }
    }
      
    
    //Assigns the value of a binary operation to a variable
    // a = b op c
    //The operator characters are:  'r' concate by rows, 'c' concate by columns
    public void assignBinary(String a, String b, char op, String c){
    }
  
    
    //Return some rows of the DataFrame
    public String head(String variable, int rows){
        if(variables.containsKey(variable)){
            DataFrame df = variables.get(variable);
            int minRows = Math.min(df.shape()[0],rows);
            return df.head(minRows);
        }
        return null;
    }
    
    
    //If the last operation was successfully completed
    public boolean ok(){
        return false;
    }

}
    



