package weibo4j.examples.favorites;

import weibo4j.Favorite;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.Favorites;
import weibo4j.model.WeiboException;

import java.util.List;

public class GetFavoritesByTags {

    public static void main(String[] args) {
        String access_token = args[0];
        Favorite fm = new Favorite(access_token);
        String tid = args[1];
        try {
            List<Favorites> favors = fm.getFavoritesByTags(tid);
            for (Favorites s : favors) {
                Log.logInfo(s.toString());
            }
        }
        catch (WeiboException e) {
            e.printStackTrace();
        }
    }

}
