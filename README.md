WordsDetection
======
#基于新浪微博数据的新词发现算法

* 支持[新浪微博数据爬取](http://open.weibo.com/tools/console)
* 支持[中文分词](segment/README.md)
* 支持新词发现

# UPDATE

## update 1.1 
* 修改在JDK7以上版本Bootstrap类被取消的问题，统一改为使用ClassLoader
* 修改在默认编码环境为非UTF-8导致的乱码问题

# 安装&编译
    git clone https://github.com/jpbirdy/WordsDetection.git
    cd WordsDetection
    mvn install -DskipTests -Dfile.encoding=UTF-8
需要配置maven和jdk，java版本1.6以上

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
执行结束后，会在该目录下生成weibo_newwords文件夹，存放本次识别的10W组新词   

对于样例数据，执行的结果如下：
    
    微博 51 n 
    微信 10 n 
    雨转 30 n 
    e租宝 5 n 
    男神 11 n 
    锅里 7 n 
    垃圾分类 5 n 
    鸡条 4 n 
    新帕萨特 4 n 
    吐槽 5 n 
    佛牌 10 n 
    韩版 4 n 
    阵雨转 16 n 
    摩羯 6 n 
    子座 141 n 
    里放 6 n 
    超赞 6 n 
    天猫 4 n 
    葳ィ言 10 n 
    羊座 14 n 
    锅中 5 n 
    羯座 5 n 
    级转 41 n 
    奢姿 3 n 
    洁面 5 n 
    加微信 4 n 
    唇部肌肤 3 n 
    亻言 5 n 
    陈皮 3 n 
    专享礼 21 n 
    金坑梯田 3 n 
    俞灏明 3 n 
    杨幂 3 n 
    鸡脯 3 n 
    吃吃 3 n 
    亚投行 3 n 
    微信号 3 n 