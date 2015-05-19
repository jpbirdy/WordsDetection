WordsDetection
======
#基于新浪微博数据的新词发现算法

* 支持[新浪微博数据爬取](http://open.weibo.com/tools/console)
* 支持[中文分词](segment/README.md)
* 支持新词发现

# 安装&编译
    git clone https://github.com/jpbirdy/WordsDetection.git
    cd WordsDetection
    mvn install -DskipTests -Dfile.encoding=UTF-8

成功安装后会出现如下的提示

    [INFO] Reactor Summary:
    [INFO] 
    [INFO] new_words_detection ................................ SUCCESS [  0.230 s]
    [INFO] weibo.api .......................................... SUCCESS [  1.173 s]
    [INFO] segment ............................................ SUCCESS [  2.017 s]
    [INFO] word_detection ..................................... SUCCESS [  1.573 s]
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time: 5.090 s
    [INFO] Finished at: 2015-05-19T19:37:17+08:00
    [INFO] Final Memory: 8M/81M
    [INFO] ------------------------------------------------------------------------
    
此时所有项目已经编译完成，在各项目的/target文件夹下会生成编译文件

#运行
项目导入到idea或eclipse中后

* 中文分词 样例程序在segment/test/java/jpbirdy/segment/test/SegmentTest类中
* 微博抓取 直接运行weibo/src/main/java/jpbirdy/spider/Weibo 类，抓取的内容会保存至weibo_spider文件夹（本项目保存了一份抓取的数据样本）
* 新词发现 直接运行word_detection/src/main/java/jpbirdy/detection/NewWordDet 类，运行结束后会产生10W组新词

在控制台运行，可以采用如下方法：
项目编译成功后，将
word_detection/target/word_detection-1.0-SNAPSHOT-jar-with-dependencies.jar 和 
weibo_spider文件夹（文件夹和jar包同级）拷贝至任意目录下，并在该目录下运行

    java -Xms4096m -Xmx4096m -Dfile.encoding=UTF-8 -cp word_detection-1.0-SNAPSHOT-jar-with-dependencies.jar jpbirdy.detection.NewWordDec
执行结束后，会在该目录下生成weibo_newword文件夹，存放本次识别的10W组新词   
