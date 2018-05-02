package com.qingmei2

import javax.imageio.ImageIO

import static java.lang.System.currentTimeMillis

println "已选中的Android设备："
println "————————————————————————————————————"
println "adb devices".execute().text
println "————————————————————————————————————"

def millis = currentTimeMillis()

def screenShotPath = System.getProperty("user.dir") + "/screenshots/${millis}"  //要截图的路径

println "adb shell screencap -p /sdcard/${millis}".execute().text
println "adb pull /sdcard/${millis} $screenShotPath".execute().text

def file1 = new File(System.getProperty("user.dir") + "/screenshots/1525244359987")
def file2 = new File(System.getProperty("user.dir") + "/screenshots/1525244367472")

def print1 = new FingerPrint(ImageIO.read(file1))
def print2 = new FingerPrint(ImageIO.read(file2))

println "sim = " + print1.compare(print2)
