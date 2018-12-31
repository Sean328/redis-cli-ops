package reflection;

/**
 * @author lixin
 * @date 2018-12-24 16:39
 **/
public class InstanceClass {

    private void sout(String str, Object object){
        if (object != null){
            System.out.println(str + object.getClass().getName());
        }else {
            System.out.println(str);
        }

        System.out.println("sout 执行完成");
    }

    private String getSout(String str,Object object){
        return "李新"+str;
    }

}
