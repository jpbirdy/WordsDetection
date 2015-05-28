/**
 * Created by jpbirdy on 14-11-23.
 */

package jpbirdy.segment;


import jpbirdy.segment.util.Jumper;
import jpbirdy.segment.util.PartOfSpeech;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author jpbirdy
 * @project Segmentation
 * @class Segmenter
 * @date 14-11-23 10:56
 * @desc 分词器
 */
public class Segmenter
{
    public static final int minTokenFrequency = 2;
    private static SegDict dict;

    static
    {
        try
        {
            loadDictionary("main/resources/dict.txt");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    /**

     * 当一个分词既出现在用户词典也出现在通用词典中，则优先使用用户词典。
     * 词典的格式为（每个分词一行）：
     * 分词文本 频率 词性
     * @param file 加载的字典文件，多个用逗号分隔
     * @return 字典结构
     * @see jpbirdy.segment.SegDict
     *
     */
    public static SegDict loadDictionary(String file) throws IOException
    {
        dict = new SegDict();
        String[] files = file.split(",");
        for(int i=0 ; i<files.length ; i++)
        {
            System.out.println("正在载入字典 " + files[i]);
//            File dictFile = new File(files[i]);
            InputStream is  = Segmenter.class.getClassLoader().getResourceAsStream(files[i]);
            if(is == null)
            {
                System.err.println("字典文件不存在！");
                continue;
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line;
            String text;
            int frequency;
            PartOfSpeech pos;

            while((line = br.readLine()) != null)
            {
//                System.out.println("read line:" + line);
                String[] lineSplit = line.split(" ");
                if(lineSplit.length <2) continue;
                if(lineSplit.length == 2)
                {
                    text = lineSplit[0];
                    System.out.println(lineSplit[0] + lineSplit[1]);
                    frequency = Integer.parseInt(lineSplit[1]);
                    pos = new PartOfSpeech();
                }
                else
                {
                    text = lineSplit[0];
                    frequency = Integer.parseInt(lineSplit[1]);
                    pos = new PartOfSpeech(lineSplit[2]);
                }

//                System.out.println(text + ' ' + frequency + pos);

                if(frequency < minTokenFrequency)
                {
                    //词频过低忽略
                    continue;
                }

                //添加分词
                List<String> words = splitTextToWords(text);
                SegToken token = new SegToken(words,frequency , 0 , pos);
                dict.addToken(token);
            }


        }
        // 计算每个分词的路径值
        double logTotalFrequency = Math.log(dict.getTotalFrequency());
        for(SegToken segToken : dict.getTokens())
        {
            segToken.setDistance(logTotalFrequency - Math.log(segToken.getFrequency()));
        }


        for(SegToken segToken : dict.getTokens())
        {
            List<Segment> segments = segmentWords(segToken.getText(), true);
            // 计算需要添加的子分词数目

            int numTokensToAdd = 0;
            for (Segment segment : segments)
            {
                //长度为1的分词自动过滤
                if(segment.getToken().getText().size()>1)
                {
                    numTokensToAdd++;
                }
            }

            List<Segment> listTemp = new ArrayList<Segment>(Collections.nCopies(numTokensToAdd, new Segment()));
            segToken.setSegments(listTemp);

            //添加子分词
            int iSegmentsToAdd = 0;
            for (Segment segment : segments)
            {
                if(segment.getToken().getText().size()>1)
                {
                    segToken.getSegments().set(iSegmentsToAdd, segments.get(iSegmentsToAdd));
                }
            }

        }

        //词典添加完毕
        System.out.println("词典添加完毕");

        return dict;
    }

    public List<String> splitTextToWords2(String word)
    {
        List<String> text = new ArrayList<String>();
        for(int i=0 ; i<word.length() ; i++)
        {
            text.add(word.charAt(i)+"");
        }
        return text;
    }


    public static List<String> splitTextToWords(String word)
    {
        List<String> text = new ArrayList<String>();
        int pattern = 0;
        StringBuilder sb = new StringBuilder();
        for(int i=0 ; i<word.length() ; i++)
        {
            char wordChar = word.charAt(i);
            switch (pattern)
            {
                //无状态
                case 0:
                    if((wordChar>='A' && wordChar<='Z') || (wordChar>='a' && wordChar<='z'))
                    {
                        sb.append(wordChar);
                        pattern = 1;
                    }
                    else if((wordChar>='0' && wordChar<='9'))
                    {
                        sb.append(wordChar);
                        pattern = 2;
                    }
                    else
                    {
                        sb.append(wordChar);
                        text.add(sb.toString());
                        sb.setLength(0);
                        pattern = 0;
                    }
                    break;
                //前一个是字母
                case 1:
                    if((wordChar>='A' && wordChar<='Z') || (wordChar>='a' && wordChar<='z'))
                    {
                        sb.append(wordChar);
                    }
                    else
                    {
                        text.add(sb.toString());
                        sb.setLength(0);
                        pattern = 0;
                        i--;
                    }
                    break;
                case 2:
                    if(Character.isDigit(wordChar))
                    {
                        sb.append(wordChar);
                    }
                    else
                    {
                        if(wordChar == '%'){sb.append(wordChar);i++;}
                        text.add(sb.toString());
                        sb.setLength(0);
                        pattern = 0;
                        i--;
                    }
                    break;
                default:
                    pattern = 0;
                    sb.setLength(0);
                    break;
            }
        }
        if(sb.length() >0)
            text.add(sb.toString());

        return text;
    }


    // 更新跳转信息:
    // 	1. 当该位置从未被访问过时(jumper.minDistance为零的情况)，或者
    //	2. 当该位置的当前最短路径大于新的最短路径时
    // 将当前位置的最短路径值更新为baseDistance加上新分词的概率
    public static Jumper updateJumper(Jumper jumper, double baseDistance, SegToken token)
    {
        double newDistance = baseDistance + token.getDistance();
        if(jumper.getMinDistance() == 0 || jumper.getMinDistance() > newDistance)
        {
            jumper.setMinDistance(newDistance);
            jumper.setToken(token);
        }
        return jumper;
    }



    public static List<Segment> segmentWords(List<String> text, boolean searchMode)
    {
        //搜索模式下该分词已无继续划分可能的情况
        if(searchMode && text.size() ==1)
        {
            return new ArrayList<Segment>();
        }

        List<Jumper> jumpers = new ArrayList<Jumper>(text.size());
        for(int i=0 ; i<text.size() ; i++) jumpers.add(new Jumper());
        List<SegToken> tokens  = new ArrayList<SegToken>(dict.getMaxLength());
        for(int i=0 ; i<dict.getMaxLength() ; i++) tokens.add(new SegToken());


        for(int current = 0 ; current< text.size() ; current++)
        {
            double baseDistance = 0;
            if(current == 0)
                baseDistance = 0;
            else
                baseDistance = jumpers.get(current - 1).getMinDistance();


            // 寻找所有以当前字元开头的分词
            int numTokens = dict.lookupTokens(makeSub(text, current, current + dict.getMaxLength()),tokens);

            // 对所有可能的分词，更新分词结束字元处的跳转信息
            for(int iToken = 0 ; iToken < numTokens ; iToken++)
            {
                int location = current + tokens.get(iToken).getText().size() - 1;
                if(!searchMode || current!=0 || location!=text.size() -1)
                {
                    jumpers.set(location, updateJumper(jumpers.get(location), baseDistance, tokens.get(iToken)));
                }
            }

            // 当前字元没有对应分词时补加一个伪分词
            if(numTokens == 0 ||  tokens.get(0).getText().size()>1)
            {
                jumpers.set(current, updateJumper(jumpers.get(current), baseDistance, new SegToken(splitTextToWords(text.get(current)), 1, 32, new PartOfSpeech("x"))));
            }
        }

        // 从后向前扫描第一遍得到需要添加的分词数目
        int numSeg = 0;
        for(int index  = text.size() - 1;index>=0 ;)
        {
            int location = index - jumpers.get(index).getToken().getText().size() + 1;
            numSeg++;
            index = location - 1;
        }

        // 从后向前扫描第二遍添加分词到最终结果
        List<Segment> outputSegment = new ArrayList<Segment>(numSeg);
        for(int i=0 ; i<numSeg ; i++) outputSegment.add(new Segment());

        for(int index = text.size() - 1 ; index>=0 ;)
        {
            int location = index - jumpers.get(index).getToken().getText().size() + 1;
            numSeg--;
            outputSegment.get(numSeg).setToken(jumpers.get(index).getToken());
            index = location - 1;
        }

        int bytePosition = 0;
        for (Segment anOutputSegment : outputSegment)
        {
            anOutputSegment.setStart(bytePosition);
            bytePosition += textSliceByteLength(anOutputSegment.getToken().getText());
            anOutputSegment.setEnd(bytePosition);
        }
        return outputSegment;
    }

    // 返回多个字元的字节总长度
    public static int textSliceByteLength(List<String> words)
    {
        int length = 0;
        for(String anWord : words)
        {
            length +=anWord.length();
        }
        return length;
    }

    public static List<String> makeSub(List<String> text, int begin, int end)
    {
        List<String> ret = new ArrayList<String>();
        for(int i=begin ; i<text.size() && i<=end ; i++)
            ret.add(text.get(i));
        return  ret;
    }

    public List<Segment> internalSegment(String string,boolean searchMode)
    {
        if(string.length() == 0)
        {
            return new ArrayList<Segment>();
        }
        List<String> text = splitTextToWords(string);
        return segmentWords(text,searchMode);
    }

    public List<Segment> segment(String string)
    {
        return internalSegment(string,false);
    }

}
