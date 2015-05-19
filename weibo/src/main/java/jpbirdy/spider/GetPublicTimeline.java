package jpbirdy.spider;

import weibo4j.Timeline;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.WeiboException;

import java.util.List;


public class GetPublicTimeline
{
    public static List<Status> getPublicTimeline()
    {
        String access_token = "2.00g3hZ_BnMrudDa18d8283e9PMFY5B";
        Timeline tm = new Timeline(access_token);
        try {
            StatusWapper status = tm.getPublicTimeline(200,0);
            return status.getStatuses();
        } catch (WeiboException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 可以从http://open.weibo.com/tools/console获取token
     * @param args
     */
	public static void main(String[] args) {
        String timeline = null;
        String access_token = "2.00g3hZ_BnMrudDa18d8283e9PMFY5B";
        Timeline tm = new Timeline(access_token);
        try {
            StatusWapper status = tm.getPublicTimeline();
            Log.logInfo(status.toString());
        } catch (WeiboException e) {
            e.printStackTrace();
        }
	}

}
