package weibo4j.examples.trends;

import weibo4j.Trend;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.Trends;
import weibo4j.model.WeiboException;

import java.util.List;

public class GetTrendsDaily {
    public static void main(String[] args) {
        String access_token = args[0];
        Trend tm = new Trend(access_token);
        try {
            List<Trends> trends = tm.getTrendsDaily();
            for (Trends ts : trends) {
                Log.logInfo(ts.toString());
            }
        }
        catch (WeiboException e) {
            e.printStackTrace();
        }
    }


}


