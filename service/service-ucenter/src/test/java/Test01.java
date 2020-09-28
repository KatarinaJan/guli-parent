import com.google.gson.Gson;
import org.junit.Test;

import java.util.Iterator;
import java.util.Map;

/**
 * @Project: guli-parent
 * @Describe: 描述
 * @Author: Jan
 * @Date: 2020-09-25 19:55
 */
public class Test01 {

    @Test
    public void test01(){
        String str = "{\"openid\":\"o3_SC53QU5B34dEjsL3Csj3GIg8U\",\"nickname\":\"丶Jan\",\"sex\":1,\"language\":\"zh_CN\",\"city\":\"Jian\",\"province\":\"Jiangxi\",\"country\":\"CN\",\"headimgurl\":\"https:\\/\\/thirdwx.qlogo.cn\\/mmopen\\/vi_32\\/Q0j4TwGTfTKqG2nwdWXc7AibpfgvYViaTVxzAvwn9ClOzeGbplTVXgxdgoSJiaPPZPxrdGEaS268IE86l8XicmCz9g\\/132\",\"privilege\":[],\"unionid\":\"oWgGz1KeiEKZ4kDtrA-XLkIn4hLA\"}";
        Gson gson = new Gson();
        Map map = gson.fromJson(str, Map.class);
        Double num = 1.0;
        System.out.println(Integer.parseInt(num.toString().substring(0,num.toString().indexOf("."))));
    }
}
