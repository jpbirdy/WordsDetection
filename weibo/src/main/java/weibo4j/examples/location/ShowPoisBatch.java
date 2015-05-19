package weibo4j.examples.location;

import weibo4j.Location;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.Poisition;
import weibo4j.model.WeiboException;

import java.util.List;

public class ShowPoisBatch {

	public static void main(String[] args) {
		String access_token = args[0];
		String srcids = args[1];
		Location l = new Location(access_token);
		try {
			List<Poisition> list = l.showPoisBatch(srcids);
			for (Poisition p : list) {
				Log.logInfo(p.toString());
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
