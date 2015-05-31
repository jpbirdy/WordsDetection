/**
 * Created by jpbirdy on 15-5-19.
 */

package jpbirdy.detection;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jialou.jp
 * @project Segmentation
 * @class MapEntity
 * @date 15-5-19 10:49
 * @desc
 */
public class MapEntity {
    private String word;
    private List<String> left, right;
    private int repeatNum;


    public MapEntity() {
        left = new ArrayList<String>();
        right = new ArrayList<String>();
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<String> getLeft() {
        return left;
    }

    public void setLeft(List<String> left) {
        this.left = left;
    }

    public List<String> getRight() {
        return right;
    }

    public void setRight(List<String> right) {
        this.right = right;
    }

    public void addOne() {
        this.repeatNum++;
    }

    public int getRepeatNum() {
        return repeatNum;
    }

    @Override
    public String toString() {
        return "MapEntity{" +
                "word='" + word + '\'' +
                ", left=" + left +
                ", right=" + right +
                ", repeatNum=" + repeatNum +
                '}';
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Hello World!");
    }
}
