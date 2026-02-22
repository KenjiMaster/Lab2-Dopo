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
        // Arrange
        int[] rows = {0, 2};
        String[][] expectedData = {
            {"Ana",   "Bogota", "25"},
            {"Maria", "Bogota", "25"}
        };
        DataFrame expected = new DataFrame(expectedData, new String[]{"Nombre", "Ciudad", "Edad"});

        // Act
        DataFrame result = personas.loc(rows, null);

        // Assert
        assertEquals(expected, result);
    }
    
    @Test
    public void shouldReturnOnlyTheRequestedColumnsInOrder() {
        // Arrange
        String[] cols = {"Ciudad", "Nombre"};
        String[][] expectedData = {
            {"Bogota",   "Ana"},
            {"Cali",     "Luis"},
            {"Bogota",   "Maria"},
            {"Medellin", "Carlos"}
        };
        DataFrame expected = new DataFrame(expectedData, new String[]{"Ciudad", "Nombre"});

        // Act
        DataFrame result = personas.select(cols);

        // Assert
        assertEquals(expected, result);
    }
    
    @Test
    public void shouldReturnRowsWhereConditionIsMetAndKeepAllColumns() {
        // Arrange — null means "don't care" for that column position
        String[] params = {null, "Bogota", null};
        String[][] expectedData = {
            {"Ana",   "Bogota", "25"},
            {"Maria", "Bogota", "25"}
        };
        DataFrame expected = new DataFrame(expectedData, new String[]{"Nombre", "Ciudad", "Edad"});

        // Act
        DataFrame result = personas.filter(params);

        // Assert
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
