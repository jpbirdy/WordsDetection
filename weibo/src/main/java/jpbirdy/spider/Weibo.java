/**
 * Created by jpbirdy on 14-12-12.
 */

package jpbirdy.spider;

import weibo4j.examples.oauth2.Log;
import weibo4j.model.Status;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author jpbirdy
 * @project Segmentation
 * @class Weibo
 * @date 14-12-12 17:08
 * @desc 抓取微博数据
 */
public class Weibo implements Runnable
{
    public static final String token = "3338226493";
    public static final String count = "200";
    public static final String url = "https://api.weibo.com/2/statuses/public_timeline.json";
    //5s请求1次新数据
    public int perTime = 5000;
    public int totalTime = 15000;
    public DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public DateFormat dffile = new SimpleDateFormat("yyyyMMddhhmm");

    @Override
    public void run()
    {
        File file = new File("weibo_spider");
        if(!file.exists())
        {
            log("文件夹" + file.getName() + "不存在，正在创建！");
            while(!file.mkdir())
            {
                log("创建文件夹" + file.getName() + "失败，正在重试……");
            }
            log("文件夹创建成功！");
        }

        String filename = dffile.format(new Date());
        File targetFile = new File( file.getAbsolutePath() + "/" + filename + ".txt");
        log("写入文件为：" + targetFile.getAbsolutePath() + targetFile.getName());


        FileOutputStream fop = null;
        try
        {
            if (!targetFile.exists())
            {
                targetFile.createNewFile();
            }
            fop = new FileOutputStream(targetFile);

            while ((totalTime--) > 0)
            {
                log("开始抓取数据");
                List<Status> list = (GetPublicTimeline.getPublicTimeline());
                log("获取到微博" +  list.size() + "条");
                for(Status status : list)
                {
                    fop.write(status.getText().getBytes());
                    fop.write("\n".getBytes());
                }
                log("写入成功，等待剩余" + totalTime);
                fop.flush();
                Thread.sleep(perTime);
            }
            fop.close();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            log("线程终止");
            new Thread(new Weibo()).start();
        }
    }

    public void log(String logString)
    {
        Log.logInfo(logString);
//        System.out.println(df.format(new Date()) + " " + logString);
    }

    public static void main(String[] args) throws Exception
    {
        new Thread(new Weibo()).start();
    }

}
