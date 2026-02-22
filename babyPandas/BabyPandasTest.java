

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