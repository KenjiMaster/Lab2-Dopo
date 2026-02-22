import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DataFrameTest{

    
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    private DataFrame personas;
    private BabyPandas bp;
    @Before
    public void setUp(){
        String[] cols = {"Nombre", "Ciudad", "Edad"};
        String[][] data = {
            {"Ana",    "Bogota",   "25"},
            {"Luis",   "Cali",     "30"},
            {"Maria",  "Bogota",   "25"},
            {"Carlos", "Medellin", "30"}
        };
        personas = new DataFrame(data, cols);

        bp = new BabyPandas();
        bp.define("p");
        bp.assign("p", personas);
        bp.define("r");
    }
    
    @Test
    public void shouldReturnSelectedRowsWithAllColumns() {
        int[] rows = {0, 2};
        String[][] expectedData = {
            {"Ana",   "Bogota", "25"},
            {"Maria", "Bogota", "25"}
        };
        DataFrame expected = new DataFrame(expectedData, new String[]{"Nombre", "Ciudad", "Edad"});

        DataFrame result = personas.loc(rows, null);

        assertEquals(expected, result);
    }
    
    @Test
    public void shouldReturnOnlyTheRequestedColumnsInOrder() {
        String[] cols = {"Ciudad", "Nombre"};
        String[][] expectedData = {
            {"Bogota",   "Ana"},
            {"Cali",     "Luis"},
            {"Bogota",   "Maria"},
            {"Medellin", "Carlos"}
        };
        DataFrame expected = new DataFrame(expectedData, new String[]{"Ciudad", "Nombre"});

        DataFrame result = personas.select(cols);

        assertEquals(expected, result);
    }
    
    @Test
    public void shouldReturnRowsWhereConditionIsMetAndKeepAllColumns() {
        String[] params = {null, "Bogota", null};
        String[][] expectedData = {
            {"Ana",   "Bogota", "25"},
            {"Maria", "Bogota", "25"}
        };
        DataFrame expected = new DataFrame(expectedData, new String[]{"Nombre", "Ciudad", "Edad"});

        DataFrame result = personas.filter(params);

        assertEquals(expected, result);
    }

    
     @Test
    public void shouldCreateSmallestDataFrame(){
        String [] columns = {"Nombre", "Edad", "Profesión"};
        String [][] data={};
        int [] shape={0,3};
        DataFrame df= new DataFrame(data,columns);
        assertArrayEquals(shape,df.shape());     
    }    
   
    @Test
    public void shouldCreateOtherDataFrame(){
        String [] columns = {"Nombre", "Edad", "Profesión"};
        String [][] data = {{"Carlos", "35", "Profesor"}, 
        {"Ana", "42", "Doctor"}, 
        {"Jorge", "30", "Arquitecto"},
        {"Elena", "25", "Diseñador"}};
        int [] shape={4,3};
        DataFrame df=new DataFrame(data,columns);
        assertArrayEquals(shape, df.shape());   
    }    
    
    @Test
    public void shouldNotCreateBadDataFrame(){
        String [] columns = {"Nombre", "Edad", "Profesión"};
        String [][] data = {{"Carlos", "35"}, 
        {"Ana", "42", "Doctor"}, 
        {"30", "Arquitecto"},
        {"Elena", "25", "Diseñador"}};
        int [] shape={2,3};
        DataFrame df=new DataFrame(data,columns);
        assertArrayEquals(shape, df.shape());   
    }
    
    
    @Test
    public void shouldConcatByRowsIncreasingRowCount() {
        String[][] extraData = {{"Pedro", "Cali", "28"}};
        DataFrame extra = new DataFrame(extraData, new String[]{"Nombre", "Ciudad", "Edad"});

        DataFrame result = personas.concat(new DataFrame[]{extra}, (byte) 0);

        assertEquals(5, result.shape()[0]);
        assertEquals(3, result.shape()[1]);
    }

    @Test
    public void shouldConcatByColumnsIncreasingColumnCount() {
        String[][] extraData = {{"Colombia"}, {"Colombia"}, {"Colombia"}, {"Colombia"}};
        DataFrame extra = new DataFrame(extraData, new String[]{"Pais"});

        DataFrame result = personas.concat(new DataFrame[]{extra}, (byte) 1);

        assertEquals(4, result.shape()[0]);
        assertEquals(4, result.shape()[1]);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
}
