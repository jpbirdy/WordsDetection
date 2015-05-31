/**
 * Created by jpbirdy on 14-11-22.
 */

package jpbirdy.segment;


import jpbirdy.segment.util.PartOfSpeech;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jpbirdy
 * @project Segmentation
 * @class SegToken
 * @date 14-11-22 20:42
 * @desc
 */
public class SegToken {
    // 分词的字串,这实际上是个字元数组
    private List<String> text;

    //分词在语料库中的词频
    private int frequency;

    // log2(总词频/该分词词频)，这相当于log2(1/p(分词))，用作动态规划中
    // 该分词的路径长度。求解prod(p(分词))的最大值相当于求解
    // sum(distance(分词))的最小值，这就是“最短路径”的来历。
    private double distance;

    //词性
    private PartOfSpeech pos;

    private List<Segment> segments;

    public SegToken() {
        segments = new ArrayList<Segment>();
    }

    public SegToken(List<String> text, int frequency, double distance, PartOfSpeech pos) {
        this.text = text;
        this.frequency = frequency;
        this.distance = distance;
        this.pos = pos;
        segments = new ArrayList<Segment>();
    }


    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public PartOfSpeech getPos() {
        return pos;
    }

    public void setPos(PartOfSpeech pos) {
        this.pos = pos;
    }


    public List<Segment> getSegments() {
        return segments;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Hello World!");
    }
}
