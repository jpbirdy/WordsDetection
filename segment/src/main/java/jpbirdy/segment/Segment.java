/**
 * Created by jpbirdy on 14-11-22.
 */

package jpbirdy.segment;

import java.util.List;

/**
 * @author jpbirdy
 * @project Segmentation
 * @class Segment
 * @date 14-11-22 20:47
 * @desc
 */
public class Segment
{
    private int start;
    private int end;
    private SegToken token;

    public int getStart()
    {
        return start;
    }

    public void setStart(int start)
    {
        this.start = start;
    }

    public int getEnd()
    {
        return end;
    }

    public void setEnd(int end)
    {
        this.end = end;
    }

    public SegToken getToken()
    {
        return token;
    }

    public void setToken(SegToken token)
    {
        this.token = token;
    }

    @Override
    public String toString()
    {
//        return combine(token.getText()) + "/" + token.getPos().getEntity().getEngSx();
        return combine(token.getText()) + "/" + token.getPos().getEntity().getChnSx();
    }

    public String combine(List<String> words)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for(String word : words)
            stringBuilder.append(word);
        return stringBuilder.toString();
    }

    public static void main(String[] args) throws Exception
    {
        System.out.println("Hello World!");
    }
}
