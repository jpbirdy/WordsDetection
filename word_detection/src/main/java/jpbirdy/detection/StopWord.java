/**
 * Created by jpbirdy on 15-5-19.
 */

package jpbirdy.detection;

import com.sun.jdi.Bootstrap;
import jpbirdy.segment.Segmenter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jialou.jp
 * @project Segmentation
 * @class StopWord
 * @date 15-5-19 11:13
 * @desc
 */
public class StopWord
{
    private static List<String> stopWords;
    public static Segmenter seg = null;
    static
    {
        try
        {
            loadStopWords();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void loadStopWords() throws IOException
    {
        if(stopWords != null) return ;
        stopWords = new ArrayList<String>();
        stopWords.add(" ");
        File stopFile = new File(Bootstrap.class.getResource("/main/resources/stops.txt").getPath());

        System.out.println(stopFile.getAbsolutePath());
        if(!stopFile.exists())
        {
            System.err.println("字典文件不存在！");
            return;
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(stopFile)));

        String line;

        while((line = br.readLine()) != null)
        {
            stopWords.add(line);
        }
        System.out.println("停词加载成功！");
//        System.out.println(stopWords);
    }


    public static boolean hasStopWord(String word)
    {
        int len = word.length();
        int alphaNum = 0;
        int chineseNum = 0;

        for(int i=0 ; i<len ; i++)
        {
            char ch = word.charAt(i);
            if (ch >= 'a' && ch<='z') alphaNum++;
            else if(ch >='A' && ch<='Z') alphaNum++;
            else if(ch>='0' && ch <='9') alphaNum++;
            if(isChinese(ch)) chineseNum++;
        }
//        if(alphaNum > len/2) return true;
        if(chineseNum < 1) return true;
        for(String stop:stopWords)
        {
            if(word.contains(stop))
            {
                return true;
            }
        }

        if(seg.segment(word).size() <= 1)
            return true;
        return false;
    }

    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS ||
                ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS ||
                ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A ||
                ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B ||
                ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION ||
                ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS ||
                ub == Character.UnicodeBlock.GENERAL_PUNCTUATION;
    }

    public static boolean containsEmoji(String source)
    {
        int len = source.length();
        for (int i = 0; i < len; i++)
        {
            char codePoint = source.charAt(i);
            if (isEmojiCharacter(codePoint))
            {
                //do nothing，判断到了这里表明，确认有表情字符
                return true;
            }
        }
        return false;
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    public static void main(String[] args) throws Exception
    {
        loadStopWords();

    }

}
