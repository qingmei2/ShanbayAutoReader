# ShanbayAutoReader
扇贝英语阅读APP，自动阅读脚本。

「扇贝阅读」APP的打卡签到需要每日完成阅读两篇文章，但是直接点击文章底部的「完成阅读」会被APP检测到作弊行为，从而无法签到打卡：

![error1.gif](https://upload-images.jianshu.io/upload_images/7293029-d086361b9dba9d79.gif?imageMogr2/auto-orient/strip)

项目的根本目的就是为了方便在没时间阅读的情况下，利用脚本自动模拟用户的行为，达到一点点往下翻页阅读的效果：

![result.gif](https://upload-images.jianshu.io/upload_images/7293029-bd8b1858bc4ad70e.gif?imageMogr2/auto-orient/strip)

因为gif图的原因，看起来很快，实际上ADB是控制屏幕缓慢地匀速下拉.

终端控制台打印的Log日志：

![开始阅读](https://upload-images.jianshu.io/upload_images/7293029-062cb376628ca514.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![结束阅读](https://upload-images.jianshu.io/upload_images/7293029-59825faa68c1afc0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
