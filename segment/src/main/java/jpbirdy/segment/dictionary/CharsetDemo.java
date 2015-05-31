/**
 * Created by jpbirdy on 14-11-27.
 */

package jpbirdy.segment.dictionary;

import java.net.URLDecoder;

/**
 * @author jpbirdy
 * @project Segmentation
 * @class CharsetDemo
 * @date 14-11-27 11:55
 * @desc
 */
public class CharsetDemo {
    public static void main(String[] args) throws Exception {
        String a = "\\xe7\\xac\\xac01\\xe5\\x8d\\xb7";
        a = a.replace("\\x", "%");
        System.out.println(a);
        System.out.println(URLDecoder.decode(a, "UTF-8"));
    }
}
