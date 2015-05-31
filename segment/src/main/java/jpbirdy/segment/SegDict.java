/**
 * Created by jpbirdy on 14-11-22.
 */

package jpbirdy.segment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jpbirdy
 * @project Segmentation
 * @class SegDict
 * @date 14-11-22 20:39
 * @desc
 */
public class SegDict {
    private SegNode root;
    private int maxLength;
    private int num;
    private List<SegToken> tokens;
    private long totalFrequency;

    public SegDict() {
        root = new SegNode();
        maxLength = 0;
        num = 0;
        tokens = new ArrayList<SegToken>();
        totalFrequency = 0;
    }

    public SegNode getRoot() {
        return root;
    }

    public void setRoot(SegNode root) {
        this.root = root;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<SegToken> getTokens() {
        return tokens;
    }

    public void setTokens(List<SegToken> tokens) {
        this.tokens = tokens;
    }

    public long getTotalFrequency() {
        return totalFrequency;
    }

    public void setTotalFrequency(long totalFrequency) {
        this.totalFrequency = totalFrequency;
    }

    ////////end getter and setter

    public static class SearchNode {
        private int index;
        private boolean success;

        public SearchNode(int i, boolean s) {
            index = i;
            success = s;
        }

        public int getIndex() {
            return index;
        }

        public boolean isSuccess() {
            return success;
        }
    }

    // 二分法查找字元在子节点中的位置
    // 如果查找成功，第一个返回参数为找到的位置，第二个返回参数为true
    // 如果查找失败，第一个返回参数为应当插入的位置，第二个返回参数false
    public SearchNode binarySearch(List<SegNode> nodes, String word) {
        int start = 0;
        int end = nodes.size() - 1;
        //nodes为空，直接插入
        if (end < 0)
            return new SearchNode(0, false);
        //首尾优化
        int compareWithFirstWord = word.compareTo(nodes.get(start).getWord());
        if (compareWithFirstWord < 0) {
            return new SearchNode(start, false);
        }
        else if (compareWithFirstWord == 0) {
            return new SearchNode(start, true);
        }

        int compareWithLastWord = word.compareTo(nodes.get(end).getWord());
        if (compareWithLastWord == 0) {
            return new SearchNode(end, true);
        }
        else if (compareWithLastWord > 0) {
            return new SearchNode(end + 1, false);
        }


        //二分
        int current = (start + end) >> 1;
        while ((end - start) > 1) {
            int compareWithCurrentWord = word.compareTo(nodes.get(current).getWord());
            if (compareWithCurrentWord == 0)
                return new SearchNode(current, true);
            else if (compareWithCurrentWord < 0)
                end = current;
            else
                start = current;
            current = (start + end) >> 1;
        }

        return new SearchNode(end, false);
    }


    // 将字元加入节点数组中，并返回插入的节点指针
    // 如果字元已经存在则返回存在的节点指针
    public SegNode upsert(List<SegNode> nodes, String word) {
        SearchNode searchNode = binarySearch(nodes, word);
        //查找成功
        if (searchNode.isSuccess()) {
            return nodes.get(searchNode.getIndex());
        }

        if (nodes == null)
            nodes = new ArrayList<SegNode>();
        nodes.add(searchNode.getIndex(), new SegNode(word));

        return nodes.get(searchNode.getIndex());
    }

    public SegDict addToken(SegToken token) {
        SegNode current = root;
        for (String word : token.getText()) {
            current = upsert(current.getChildren(), word);
        }

        if (current.getToken() == null) {
            current.setToken(token);
            if (token.getText().size() > maxLength) {
                maxLength = token.getText().size();
            }
            num++;
            tokens.add(token);
            totalFrequency += token.getFrequency();
        }

        return this;
    }

    // 在词典中查找和字元组words可以前缀匹配的所有分词
    // 返回值为找到的分词数
    public int lookupTokens(List<String> words, List<SegToken> tokens) {
        if (words.size() == 0)
            return 0;
        SegNode current = root;
        int numTokens = 0;
        for (String word : words) {
            // 如果已经抵达叶子节点则不再继续寻找
            if (current.getChildren().size() == 0)
                break;
            SearchNode searchNode = binarySearch(current.getChildren(), word);

            // 否则在该节点子节点中进行下个字元的匹配
            if (!searchNode.isSuccess()) {
                break;
            }
            current = current.getChildren().get(searchNode.getIndex());
            if (current.getToken() != null) {
                if (tokens.size() - 1 < numTokens)
                    tokens.add(null);
                tokens.set(numTokens, current.getToken());
                numTokens++;
            }
        }
        return numTokens;
    }


    public static void main(String[] args) throws Exception {
        //        List<Integer> list = new ArrayList<Integer>();
        //        list.add(1);
        //        list.add(2);
        //        list.add(3);
        //        list.add(4);
        //        list.add(5);
        //        list.add(6);
        //        list.add(1,99);
        //        System.out.println(list);
    }
}
