import java.util.HashMap;

/**
 * Interprete simplificado de Pandas que administra variables de tipo DataFrame.
 * @author ESCUELA 2026-01
 */
public class BabyPandas {
    
    private HashMap<String, DataFrame> variables;
    
    /** Crea una instancia de BabyPandas sin variables definidas. */
    public BabyPandas() {
        variables = new HashMap<>();
    }

    /**
     * Define una nueva variable vacía si el nombre no existe.
     * @param name nombre de la variable
     */
    public void define(String name) {
        if (!variables.containsKey(name)) {
            variables.put(name, new DataFrame());
        }
    }
    
    /**
     * Asigna un DataFrame a una variable existente.
     * @param variable nombre de la variable destino
     * @param df       DataFrame a asignar
     */
    public void assign(String variable, DataFrame df) {
        if (variables.containsKey(variable)) {
            variables.put(variable, df);
        }
    }
    
    /**
     * Retorna las dimensiones {filas, columnas} del DataFrame de la variable dada.
     * @param variable nombre de la variable
     */
    public int[] shape(String variable) {
        if (variables.containsKey(variable)) {
            return variables.get(variable).shape();
        }
        return new int[] {0, 0};
    }
    
    /**
     * Asigna a 'a' el resultado de aplicar una operación unaria sobre 'b'; 'r' selecciona filas, 'c' columnas, '?' filtra por condición.
     * @param a          variable destino
     * @param b          variable fuente
     * @param op         operador: 'r', 'c' o '?'
     * @param parameters índices, nombres de columna o valores de condición según el operador
     */
    public void assignUnary(String a, String b, char op, String[] parameters) {
        if (!variables.containsKey(a) || !variables.containsKey(b)) return;
        DataFrame source = variables.get(b);
        DataFrame result = null;

        if (op == 'r') {
            int[] rows = new int[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                rows[i] = Integer.parseInt(parameters[i]);
            }
            result = source.loc(rows, null);
        } else if (op == 'c') {
            result = source.select(parameters);
        } else if (op == '?') {
            result = source.filter(parameters);
        }

        if (result != null) {
            variables.put(a, result);
        }
    }
    
    /**
     * Asigna a 'a' la concatenación de 'b' y 'c'; 'r' une por filas, 'c' une por columnas.
     * @param a  variable destino
     * @param b  primera variable fuente
     * @param op operador: 'r' o 'c'
     * @param c  segunda variable fuente
     */
    public void assignBinary(String a, String b, char op, String c) {
        if (!variables.containsKey(a) || !variables.containsKey(b) || !variables.containsKey(c)) return;
        DataFrame dfB = variables.get(b);
        DataFrame dfC = variables.get(c);
        byte axis = (op == 'r') ? (byte) 0 : (byte) 1;
        DataFrame result = dfB.concat(new DataFrame[]{dfC}, axis);
        if (result != null) {
            variables.put(a, result);
        }
    }
    
    /**
     * Retorna las primeras 'rows' filas del DataFrame de la variable dada, o null si no existe.
     * @param variable nombre de la variable
     * @param rows     número de filas a mostrar
     */
    public String head(String variable, int rows) {
        if (variables.containsKey(variable)) {
            DataFrame df = variables.get(variable);
            int minRows = Math.min(df.shape()[0], rows);
            return df.head(minRows);
        }
        return null;
    }
    
    /** Retorna true si la última operación se completó exitosamente. */
    public boolean ok() {
        return false;
    }
}
    



