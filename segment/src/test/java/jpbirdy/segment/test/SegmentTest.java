/**
 * Created by jpbirdy on 15-4-23.
 */

package jpbirdy.segment.test;


import jpbirdy.segment.Segmenter;
import org.junit.Test;

import java.io.IOException;

/**
 * @author jialou.jp
 * @project LSDSegment
 * @class SegmentTest
 * @date 15-4-23 9:50
 * @desc
 */
public class SegmentTest {
    @Test(expected = OutOfMemoryError.class)
    public void testSegmenter() throws IOException {
        Segmenter seg = new Segmenter();
        System.out.println(seg.segment(
                "使用Java程序从数据库中查询大量的数据时出现异常:java.lang.OutOfMemoryError: Java heap space。在JVM中如果98%的时间是用于GC且可用的 Heap size 不足2%的时候将抛出此异常信息。JVM堆的设置是指java程序运行过程中JVM可以调配使用的内存空间的设置.JVM在启动的时候会自动设置Heap size的值，其初始空间(即-Xms)是物理内存的1/64，最大空间(-Xmx)是物理内存的1/4。可以利用JVM提供的-Xmn -Xms -Xmx等选项可进行设置。"));
        System.out.println(seg.segment(
                "白富美有些现成的办法，看楼主要达到的目的具体是什么。例如如果只是有字符串里面是十六进制的数字，要解析成整型数据类型，那直接用Integer.parseInt()或者Long.parseLong()就好了，像这样"));
        System.out.println(seg.segment("中西医结合成首选"));
    }

}
