package com.qingmei2

import javax.imageio.ImageIO
import javax.xml.crypto.Data

import static java.lang.System.currentTimeMillis

def actionInterval = 100                                //两次下翻操作的时间间隔，单位ms,因为IO操作也会造成延时，因此暂时设置为100
def threshold = 0.95                                    //图片分析相似度的阈值，当相似度大于阈值时，视为图片相同
def clearScreenShotCacheWhenFinishTask = true           //Optional，是否自动清除截图缓存
def ending = false                                      //是否已结束
def duration = 0                                        //本次操作已执行时间
String rootPath = System.getProperty("user.dir") + "/screenshots/"
String lastScreenShot = null
String newScreenShot = null

println "已选中的Android设备："
println "————————————————————————————————————"
println "adb devices".execute().text
println "————————————————————————————————————"
println "开始执行自动阅读"

/**
 *  19 -->  "KEYCODE_DPAD_UP"
 *  20 -->  "KEYCODE_DPAD_DOWN"
 *  21 -->  "KEYCODE_DPAD_LEFT"
 *  22 -->  "KEYCODE_DPAD_RIGHT"
 *  23 -->  "KEYCODE_DPAD_CENTER"
 */
while (!ending) {
    if (!new File(rootPath).exists()) {
        new File(rootPath).mkdirs()
    }

    def dateBefore = new Date()

    newScreenShot = task_screenShot(rootPath)
    task_downPage(actionInterval)

    if (lastScreenShot != null) {
        def similar = task_compareSimilar(lastScreenShot, newScreenShot)
        if (similar <= threshold) {
            println "图片相似度 = $similar,小于等于临界值$threshold,继续执行向下翻页操作."
        } else {
            println "图片相似度 = $similar,大于临界值$threshold,结束阅读."
            task_finishReading()                                      //结束阅读
            task_clearDir(clearScreenShotCacheWhenFinishTask)         //清除缓存截图
            ending = true
        }
    }
    lastScreenShot = newScreenShot
    
    def dateAfter = new Date()
    def ioCostTime = dateAfter.getTime() - dateBefore.getTime()

    duration += (actionInterval + ioCostTime)
    println "本次阅读已持续时间：$duration ms"
}

// 考虑采用三方图片识别技术，通过对屏幕的识别，判断是否达到文章底部，并点击「完成阅读」操作
println "The essay's reading was finished."

/**
 * 为当前的屏幕截图，并保存在默认路径
 */
def task_screenShot(String rootPath) {

    def millis = currentTimeMillis()

    def screenShotPath = rootPath + millis  //要截图的路径

    println "screenShotPath = $screenShotPath"

    println "adb shell screencap -p /sdcard/${millis}".execute().text
    println "adb pull /sdcard/${millis} $screenShotPath".execute().text

    return screenShotPath
}

/**
 * 为当前app执行向下翻页操作
 */
def task_downPage(Integer interval = 500) {
    Thread.sleep(interval)
    println "adb shell input keyevent 20".execute().text
}

/**
 * 通过比较获取图片的相似度
 */
def task_compareSimilar(String pic1, String pic2) {
    def print1 = new FingerPrint(ImageIO.read(new File(pic1)))
    def print2 = new FingerPrint(ImageIO.read(new File(pic2)))

    return print1.compare(print2)
}

/**
 * 结束阅读，自动点击屏幕下方按钮「完成阅读」或者「读后感」
 */
def task_finishReading() {
    println "——————————————————————————————————————————————"
    println "执行结束阅读操作..."
    println "adb shell input tap 540 1730".execute().text         //模拟点击按钮完成阅读，这里以1920*1080的屏幕分辨率为准
    println "执行结束阅读操作完毕."
    println "——————————————————————————————————————————————"
}

/**
 * 清除文件目录下截图文件
 */
def task_clearDir(boolean clear = true, String rootPath = System.getProperty("user.dir") + "/screenshots/") {
    if (clear) {
        println '清除图片文件夹中...'
        new File(rootPath).deleteDir()
        println '清除完毕'
    } else {
        println '本次任务不清除screenshots文件夹下缓存图片文件,若要修改该配置,请将脚本文件中clearScreenShotCacheWhenFinishTask设置为true'
    }
}