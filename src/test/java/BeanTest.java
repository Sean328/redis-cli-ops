import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sean
 * @date 2018/12/10/22:49
 **/
public class BeanTest {

    public static void main(String[] args) {
        BigDecimal bd = BigDecimal.ZERO;
        List<BigDecimal> bds = new ArrayList<>();
        bds.add(bd);
        for(int i=0;i<10;i++){
            bd = new BigDecimal(i);
            bds.add(bd);
        }

        System.out.println(bds);
    }

}
