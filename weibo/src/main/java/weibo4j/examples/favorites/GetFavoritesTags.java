package weibo4j.examples.favorites;

import weibo4j.Favorite;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.FavoritesTag;
import weibo4j.model.WeiboException;

import java.util.List;

public class GetFavoritesTags {

    public static void main(String[] args) {
        String access_token = args[0];
        Favorite fm = new Favorite(access_token);
        try {
            List<FavoritesTag> favors = fm.getFavoritesTags();
            for (FavoritesTag s : favors) {
                Log.logInfo(s.toString());
            }
        }
        catch (WeiboException e) {
            e.printStackTrace();
        }
    }

}