import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class BabyPandasTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class BabyPandasTest
{
    private BabyPandas bp;
    private DataFrame df;
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {   
        String [] columns = {"Nombre", "Edad", "Profesión"};
        String [][] data = {{"Carlos", "35", "Profesor"}, 
        {"Ana", "42", "Doctor"}, 
        {"Jorge", "30", "Arquitecto"},
        {"Elena", "25", "Diseñador"}};
        bp = new BabyPandas();
        df = new DataFrame(data,columns);
        bp.define("p");
        bp.assign("p", df);
        bp.define("r");
    }
    
    @Test
    public void shouldAssignSelectedRowsToDestinationVariable() {
        String[] params = {"1", "3"};

        bp.assignUnary("r", "p", 'r', params);

        assertEquals(2, bp.shape("r")[0]);
        assertEquals(3, bp.shape("r")[1]);
    }
    
    @Test
    public void shouldAssignSelectedColumnsToDestinationVariable() {
        String[] params = {"Nombre", "Edad"};

        bp.assignUnary("r", "p", 'c', params);

        assertEquals(4, bp.shape("r")[0]);
        assertEquals(2, bp.shape("r")[1]);
    }
    
    @Test
    public void shouldAssignFilteredRowsToDestinationVariable() {
        String[] params = {null, "42", null};
    
        bp.assignUnary("r", "p", '?', params);
    
        assertEquals(1, bp.shape("r")[0]);
        assertEquals(3, bp.shape("r")[1]);
    }
    
    @Test
    public void shouldDefineVariable(){
        bp.define("uno");
        DataFrame dfTest = new DataFrame();
        assertArrayEquals(bp.shape("uno"),dfTest.shape());
    }
    
    
    @Test
    public void shouldAssignDataFrame(){
        bp.define("uno");
        bp.assign("uno",df);
        assertArrayEquals(df.shape(),bp.shape("uno"));
    }
    
    @Test
    public void shouldNotShowHead(){
        assertNull(bp.head("uno",5)); 
    }
    
    @Test
    public void shouldConcatByRowsKeepingColumns() {
        String[] cols = {"Nombre", "Edad", "Profesión"};
        String[][] extraData = {{"Pedro", "28", "Abogado"}};
        DataFrame extra = new DataFrame(extraData, cols);
        bp.define("q");
        bp.assign("q", extra);
        bp.define("s");

        bp.assignBinary("s", "p", 'r', "q");

        assertEquals(5, bp.shape("s")[0]);
        assertEquals(3, bp.shape("s")[1]);
    }

    @Test
    public void shouldConcatByColumnsKeepingRows() {
        String[] cols = {"Ciudad"};
        String[][] extraData = {{"Bogota"}, {"Cali"}, {"Medellin"}, {"Barranquilla"}};
        DataFrame extra = new DataFrame(extraData, cols);
        bp.define("q");
        bp.assign("q", extra);
        bp.define("s");

        bp.assignBinary("s", "p", 'c', "q");

        assertEquals(4, bp.shape("s")[0]);
        assertEquals(4, bp.shape("s")[1]);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }
}