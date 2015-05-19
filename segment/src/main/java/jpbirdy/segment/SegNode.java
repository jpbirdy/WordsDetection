/**
 * Created by jpbirdy on 14-11-22.
 */

package jpbirdy.segment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jpbirdy
 * @project Segmentation
 * @class SegNode
 * @date 14-11-22 20:40
 * @desc
 */
public class SegNode
{
    private String word;
    private SegToken token;
    private List<SegNode> children;


    public SegNode()
    {
        this.word = "";
        token = null;
        children = new ArrayList<SegNode>();
    }

    public SegNode(String word)
    {
        this.word = word;
        token = null;
        children = new ArrayList<SegNode>();
    }

    public String getWord()
    {
        return word;
    }

    public void setWord(String word)
    {
        this.word = word;
    }

    public SegToken getToken()
    {
        return token;
    }

    public void setToken(SegToken token)
    {
        this.token = token;
    }

    public List<SegNode> getChildren()
    {
        return children;
    }

    public void setChildren(List<SegNode> children)
    {
        this.children = children;
    }

    public static void main(String[] args) throws Exception
    {
        System.out.println("Hello World!");
    }
}
