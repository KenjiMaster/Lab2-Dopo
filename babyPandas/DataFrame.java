import java.util.*;

public class DataFrame {
    
    private String[][] data;
    private String[] columns;
    
    /** Crea un DataFrame vacío sin filas ni columnas. */
    public DataFrame() {
        data = new String[][] {};
        columns = new String[] {};
    }
    
    /**
     * Crea un DataFrame con los datos y columnas dados; ignora filas cuyo largo no coincida con el número de columnas.
     * @param data    matriz de datos
     * @param columns nombres de las columnas
     */
    public DataFrame(String[][] data, String[] columns) {
        List<String[]> repaso = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            if (data[i].length == columns.length) {
                repaso.add(data[i]);
            }
        }
        String[][] newData = new String[repaso.size()][columns.length];
        for (int i = 0; i < repaso.size(); i++) newData[i] = repaso.get(i);
        this.data = newData;
        this.columns = columns;
    }
    
    /**
     * Retorna un nuevo DataFrame con las filas en los índices dados; índices inválidos son ignorados.
     * @param rows    índices de las filas a seleccionar
     * @param columns ignorado, se conservan todas las columnas
     */
    public DataFrame loc(int[] rows, String columns) {
        List<String[]> selected = new ArrayList<>();
        for (int idx : rows) {
            if (idx >= 0 && idx < data.length) {
                selected.add(data[idx].clone());
            }
        }
        return new DataFrame(selected.toArray(new String[0][]), this.columns.clone());
    }
    
    /**
     * Retorna un nuevo DataFrame con solo las columnas solicitadas en el orden dado; columnas inexistentes son ignoradas.
     * @param values nombres de las columnas a seleccionar
     */
    public DataFrame select(String[] values) {
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
     * Retorna un nuevo DataFrame con las filas donde cada columna no-nula en parameters sea igual al valor de la celda.
     * @param parameters valor esperado por columna; null significa que no importa
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
    
    /**
     * Concatena este DataFrame con los del arreglo; axis 0 une por filas (mismas columnas), axis 1 une por columnas (mismo número de filas).
     * @param dfs  DataFrames a concatenar
     * @param axis 0 para filas, 1 para columnas
     */
    public DataFrame concat(DataFrame[] dfs, byte axis) {
        if (axis == 0) {
            List<String[]> newData = new ArrayList<>(Arrays.asList(data));
            for (DataFrame df : dfs) {
                if (Arrays.equals(df.columns, this.columns)) {
                    newData.addAll(Arrays.asList(df.data));
                }
            }
            return new DataFrame(newData.toArray(new String[0][]), this.columns.clone());
        } else {
            List<String> newCols = new ArrayList<>(Arrays.asList(this.columns));
            String[][] newData = new String[data.length][];
            for (int i = 0; i < data.length; i++) {
                newData[i] = Arrays.copyOf(data[i], data[i].length);
            }
            for (DataFrame df : dfs) {
                if (df.data.length == this.data.length) {
                    for (String col : df.columns) newCols.add(col);
                    for (int i = 0; i < newData.length; i++) {
                        int oldLen = newData[i].length;
                        newData[i] = Arrays.copyOf(newData[i], oldLen + df.data[i].length);
                        System.arraycopy(df.data[i], 0, newData[i], oldLen, df.data[i].length);
                    }
                }
            }
            return new DataFrame(newData, newCols.toArray(new String[0]));
        }
    }
    
    /** Retorna un arreglo {filas, columnas} con las dimensiones del DataFrame. */
    public int[] shape() {
        return new int[] {data.length, columns.length};
    }
    
    /**
     * Retorna un String con las primeras 'rows' filas del DataFrame alineadas con separadores.
     * @param rows número de filas a mostrar
     */
    public String head(int rows) {
        StringBuilder sb = new StringBuilder();
        String sep = "   ";
        for (int i = 0; i < columns.length; i++) {
            sb.append(sep);
            sb.append(" ");
            sb.append(columns[i]);
        }
        for (int i = 0; i < columns.length; i++) {
            sb.append("\n");
            sb.append(String.valueOf(i));
            for (int j = 0; j < rows; j++) {
                sb.append(sep);
                sb.append(data[i][j].substring(0, columns[i].length()));
            }
        }
        return sb.toString();
    }
    
    /**
     * Retorna true si ambos DataFrames tienen las mismas columnas y datos.
     * @param df DataFrame a comparar
     */
    public boolean equals(DataFrame df) {
        if (!Arrays.equals(this.columns, df.columns)) return false;
        if (this.data.length != df.data.length) return false;
        for (int i = 0; i < data.length; i++) {
            if (!Arrays.equals(this.data[i], df.data[i])) return false;
        }
        return true;
    }
    
    /** @param o objeto a comparar */
    public boolean equals(Object o) {
        if (!(o instanceof DataFrame)) return false;
        return equals((DataFrame) o);
    }
}
