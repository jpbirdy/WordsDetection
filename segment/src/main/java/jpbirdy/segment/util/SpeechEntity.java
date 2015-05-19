/**
 * Created by jpbirdy on 14-11-22.
 */

package jpbirdy.segment.util;

/**
 * @author jpbirdy
 * @project Segmentation
 * @class SpeechEntity
 * @date 14-11-22 18:55
 * @desc 词性实体
 */
public class SpeechEntity
{
    private String engSx;
    private String chnSx;
    private String details;

    public SpeechEntity(String eng, String chn , String detail)
    {
        engSx = eng;
        chnSx = chn;
        details = detail;
    }

    public SpeechEntity(String eng, String chn )
    {
        engSx = eng;
        chnSx = chn;
    }


    public String getEngSx()
    {
        return engSx;
    }

    public void setEngSx(String engSx)
    {
        this.engSx = engSx;
    }

    public String getChnSx()
    {
        return chnSx;
    }

    public void setChnSx(String chnSx)
    {
        this.chnSx = chnSx;
    }

    public String getDetails()
    {
        return details;
    }

    public void setDetails(String details)
    {
        this.details = details;
    }

    @Override
    public String toString()
    {
        return engSx + " " + chnSx + " " + details;
    }

    public static void main(String[] args) throws Exception
    {
        System.out.println("Hello World!");
    }
}
