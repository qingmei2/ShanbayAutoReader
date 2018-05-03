# ShanbayAutoReader
扇贝英语阅读APP，自动阅读脚本。

## 简介

「扇贝阅读」APP的打卡签到需要每日完成阅读两篇文章，但是直接点击文章底部的「完成阅读」会被APP检测到作弊行为，从而无法签到打卡：

<div align="left"><img width="300" height="540" src="https://upload-images.jianshu.io/upload_images/7293029-d086361b9dba9d79.gif?imageMogr2/auto-orient/strip"/></div>

项目的根本目的就是为了方便在没时间阅读的情况下，利用脚本自动模拟用户的行为，达到一点点往下翻页阅读的效果：

<div align="left"><img width="300" height="540" src="https://upload-images.jianshu.io/upload_images/7293029-bd8b1858bc4ad70e.gif?imageMogr2/auto-orient/strip"/></div>

因为gif图的原因，看起来很快，实际上ADB是控制屏幕缓慢地匀速下拉.

终端控制台打印的Output：

![output1](https://upload-images.jianshu.io/upload_images/7293029-062cb376628ca514.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![output2](https://upload-images.jianshu.io/upload_images/7293029-59825faa68c1afc0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 如何使用

### 1、配置Groovy和Java的开发环境

通过在终端输入 java -version 命令和 groovy -v 命令，得到正确的输出为准:

![check_java_screenshot](https://github.com/qingmei2/ShanbayAutoReader/blob/master/production/images/check_java_screenshot.png)

![check_groovy_screenshot](https://github.com/qingmei2/ShanbayAutoReader/blob/master/production/images/check_groovy_screenshot.png)

### 2、打开APP，打开首页的一篇文章

### 3、终端输入命令运行脚本：

> groovy -cp xxx/ShanbayAutoReader/production/ShanbayAutoReader.jar xxx/ShanbayAutoReader/src/groovy/com/qingmei2/Main.groovy

命令中的"xxx/ShanbayAutoReader/production/ShanbayAutoReader.jar"为jar包的位置，xxx为你项目所在的位置；

"xxx/ShanbayAutoReader/src/groovy/com/qingmei2/Main.groovy"为你要运行的脚本文件，xxx为你项目所在的位置。

以个人项目为例，完整命令为：

> groovy -cp file:/Users/qingmei/IdeaProjects/ShanbayAutoReader/production/ShanbayAutoReader.jar file:/Users/qingmei/IdeaProjects/ShanbayAutoReader/src/groovy/com/qingmei2/Main.groovy

## 原理

基本思路是这样：
* 1、通过adb命令模拟用户向下翻页的操作；
* 2、每次模拟完翻页操作后，将当前屏幕截图保存；
* 3、然后将上次翻页完成后的截图和本次截图进行图像识别分析，得到2张屏幕截图的相似度；
* 4、当2张屏幕截图的相似度匹配不高时，视为两张图片不同，即应该继续向下翻页，并重复1~3的行为；
* 5、当2张屏幕截图的相似度匹配很高时，视为该两次操作达到了文章最底部（无法继续下翻，所以截图基本一样），点击「完成阅读」按钮，并清除截图缓存文件夹，结束本次脚本任务。

更详细请参考我的这篇文章以及源码：[Android 用Groovy实现扇贝阅读APP的自动阅读功能](https://www.jianshu.com/p/062d8666e7f4)

## 感谢

[java:均值哈希实现图像内容相似度比较](https://blog.csdn.net/10km/article/details/70949272)

脚本代码中，「图像内容相似度比较」的算法是很重要的一部分，对此我参考了@10km前辈的这篇文章，并将代码基本原封不动放入了项目中，深表感谢！