package com.qingmei2

println "已选中的Android设备："

println "————————————————————————————————————"

println "adb devices".execute().text

println "————————————————————————————————————"

println "开始执行自动阅读"

//因为系统原因，很多情况下该命令实际的效果为对界面元素的长按，因此抛弃该命令
//println 'input swipe 540 1300 540 500 100 '

boolean stillScrolling = true
int actionInterval = 500
int duration = 0

/**
 *  19 -->  "KEYCODE_DPAD_UP"
 *  20 -->  "KEYCODE_DPAD_DOWN"
 *  21 -->  "KEYCODE_DPAD_LEFT"
 *  22 -->  "KEYCODE_DPAD_RIGHT"
 *  23 -->  "KEYCODE_DPAD_CENTER"
 */
while (stillScrolling) {
    Thread.sleep(actionInterval)
    println "adb shell input keyevent 20".execute().text
    duration += actionInterval
    if (duration >= 60 * 1000)
        stillScrolling = false
    println 'Action page down...'
}

// 考虑采用三方图片识别技术，通过对屏幕的识别，判断是否达到文章底部，并点击「完成阅读」操作
println "The essay's reading was finished."

