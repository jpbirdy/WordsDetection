/**
 * Created by jpbirdy on 15-5-19.
 */

package jpbirdy.detection;


import jpbirdy.segment.Segmenter;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author jialou.jp
 * @project Segmentation
 * @class NewWordDec
 * @date 15-5-19 10:44
 * @desc
 */
public class NewWordDec {

    private String file;
    private Map<String, MapEntity> newWords;

    public NewWordDec() {
        newWords = new HashMap<String, MapEntity>();
    }

    public void loadFile(String file) throws IOException {
        System.out.println("正在处理文件：" + file);
        File docFile = new File(file);
        if (!docFile.exists()) {
            System.err.println("目标文件不存在！");
            return;
        }

        int totalLine = count(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(docFile),"UTF-8"));

        String line;
        long lineNum = 0;

        while ((line = br.readLine()) != null) {
            findNewWords(line);
            lineNum++;
            if (lineNum % 1000 == 0) {
                System.out.println((lineNum * 10000 / totalLine / 100.0) + "% success!");
            }
        }
        //        System.out.println(newWords);
        System.out.println("文件处理结束：" + file);
    }

    public static int count(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        byte[] c = new byte[1024];
        int count = 0;
        int readChars = 0;
        while ((readChars = is.read(c)) != -1) {
            for (int i = 0; i < readChars; ++i) {
                if (c[i] == '\n')
                    ++count;
            }
        }
        is.close();
        return count;
    }


    public static class MapEntityComparator implements Comparator<MapEntity> {

        @Override
        public int compare(MapEntity o1, MapEntity o2) {
            int a = o1.getRepeatNum() / 100 + o1.getLeft().size() * o1.getRight().size();
            int b = o2.getRepeatNum() / 100 + o2.getLeft().size() * o2.getRight().size();
            return b - a;
        }
    }

    public void printNewWords() throws InterruptedException {
        final int MIN_REPEAT = 1;
        final int TOP = 500;
        List<MapEntity> list = new ArrayList<MapEntity>();
        for (Map.Entry<String, MapEntity> entry : newWords.entrySet()) {
            if (entry.getValue().getRepeatNum() < MIN_REPEAT)
                continue;
            list.add(entry.getValue());
            //            System.out.println(entry.getKey()+"--->"+entry.getValue());
        }
        Collections.sort(list, new MapEntityComparator());
        for (int i = 0; i < TOP; i++) {
            MapEntity entity = list.get(i);
            System.out.println("word: " + entity.getWord() +
                    " , left=" + entity.getLeft().size() +
                    " , right=" + entity.getRight().size() + " ,repeatNum: " + entity.getRepeatNum());
        }
        toFile(list);
    }

    public void toFile(List<MapEntity> list) throws InterruptedException {
        final int MAX_NEW_WORD = 100000;

        DateFormat dffile = new SimpleDateFormat("yyyyMMddHHmm");

        File file = new File("weibo_newwords");
        if (!file.exists()) {
            System.out.println("文件夹" + file.getName() + "不存在，正在创建！");
            while (!file.mkdir()) {
                System.out.println("创建文件夹" + file.getName() + "失败，正在重试……");
                Thread.sleep(1000);
            }
            System.out.println("文件夹创建成功！");
        }

        String filename = dffile.format(new Date());
        File targetFile = new File(file.getAbsolutePath() + "/" + filename + ".txt");
        System.out.println("写入文件为：" + targetFile.getAbsolutePath() + targetFile.getName());


        FileOutputStream fos = null;
        try {
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }
            fos = new FileOutputStream(targetFile);
            for (int i = 0; i < Math.min(list.size(), MAX_NEW_WORD); i++) {
                MapEntity entity = list.get(i);
                fos.write((entity.getWord() + " " + entity.getRepeatNum() + " n \n").getBytes("UTF-8"));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadDir(String dir) throws IOException {
        File dirFile = new File(dir);
        if (!dirFile.isDirectory()) {
            System.out.println("文件夹错误！");
        }
        File[] files = dirFile.listFiles();
        for (File file : files) {
            if (file.isDirectory())
                loadDir(file.getPath());
            else
                loadFile(file.getPath());
        }
    }

    public void findNewWords(String line) {
        int len = line.length();

        for (int i = 0; i < len; i++) {
            char left, right;
            left = right = 0;

            if (i > 0)
                left = line.charAt(i - 1);
            for (int j = 2; j <= 4; j++) {
                StringBuilder sb = new StringBuilder();
                sb.setLength(0);
                for (int k = 0; k < j && (i + k) < len; k++) {
                    sb.append(line.charAt(i + k));
                }
                if (sb.length() < 2)
                    continue;

                if (i + j < len)
                    right = line.charAt(i + j);

                String newWord = sb.toString();
                if (StopWord.hasStopWord(newWord))
                    continue;
                if (newWords.containsKey(newWord)) {
                    MapEntity entity = newWords.get(newWord);
                    entity.setWord(newWord);
                    entity.addOne();
                }
                else {
                    MapEntity entity = new MapEntity();
                    entity.setWord(newWord);
                    entity.addOne();
                    newWords.put(newWord, entity);
                }

                if (left > 0) {
                    if (!newWords.get(newWord).getLeft().contains(left + ""))
                        newWords.get(newWord).getLeft().add(left + "");
                }
                if (right > 0) {
                    if (!newWords.get(newWord).getRight().contains(right + ""))
                        newWords.get(newWord).getRight().add(right + "");
                }
            }
        }
    }


    public static void main(String[] args) throws Exception {
        StopWord.seg = new Segmenter();
        NewWordDec dec = new NewWordDec();
        dec.loadDir("weibo_spider");
        dec.printNewWords();
    }
}
