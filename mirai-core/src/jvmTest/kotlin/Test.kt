/*
 * Copyright 2019-2023 Mamoe Technologies and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/mamoe/mirai/blob/dev/LICENSE
 */

package net.mamoe.mirai.internal

import net.mamoe.mirai.BotFactory
import net.mamoe.mirai.auth.BotAuthorization
import net.mamoe.mirai.utils.BotConfiguration
import java.io.File

internal suspend fun main(){
        val workDir = "C:\\mirai\\3092644245\\ANDROID_PAD"
        val file = File(workDir)
        if (file.exists() && file.isDirectory ){
            file.mkdirs()
        }

        val bot = BotFactory.newBot(3092644245,"YanJiuYUEswb913"){
            protocol = BotConfiguration.MiraiProtocol.ANDROID_PAD
            workingDir = file
            fileBasedDeviceInfo("device.json")
//            redirectBotLogToFile()
//            redirectBotLogToDirectory()
            redirectNetworkLogToFile()
//            redirectNetworkLogToDirectory()
        }
        bot.login()
//        bot.dailyAttendance()
//        bot.dailyEveningAttendance()
//    bot.close()
    bot.dailyVipMallAttendance()
//    val friend = bot.getFriend(1749335128L)
//    val temp =friend?.likeQQZone(3,1)
//    friend?.thumbsUp(10)
//    println(temp)
}