/*
 * Copyright 2019-2022 Mamoe Technologies and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/mamoe/mirai/blob/dev/LICENSE
 */

package net.mamoe.mirai.internal.network.protocol.packet.chat.receive

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.utils.io.core.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.mamoe.mirai.Bot
import net.mamoe.mirai.internal.QQAndroidBot
import net.mamoe.mirai.internal.asQQAndroidBot
import net.mamoe.mirai.internal.network.Packet
import net.mamoe.mirai.internal.network.components.AccountSecretsImpl
import net.mamoe.mirai.internal.network.components.AccountSecretsManager
import net.mamoe.mirai.internal.network.components.HttpClientProvider
import net.mamoe.mirai.internal.network.protocol.packet.IncomingPacketFactory
import net.mamoe.mirai.internal.network.protocol.packet.OutgoingPacket
import net.mamoe.mirai.internal.network.protocol.packet.buildResponseUniPacket
import net.mamoe.mirai.internal.network.protocol.packet.login.wtlogin.WtLogin10
import net.mamoe.mirai.internal.network.sdkVersion
import net.mamoe.mirai.utils.JsonStruct
import net.mamoe.mirai.utils.loadAs
import net.mamoe.mirai.utils.loadSafelyAs
import net.mamoe.mirai.utils.toUHexString

internal object OnlinePushSidExpired :
    IncomingPacketFactory<Packet?>("OnlinePush.SidTicketExpired", "OnlinePush.SidTicketExpired") {
    override suspend fun QQAndroidBot.handle(packet: Packet?, sequenceId: Int): OutgoingPacket {
        return buildResponseUniPacket(client, sequenceId = sequenceId).also {
            val str = getT544String(bot)
            bot.network.sendAndExpect(WtLogin10(client, mainSigMap = 1052896, remark = "10:refresh-token", t544String = str))
            bot.components[AccountSecretsManager].saveSecrets(bot.account, AccountSecretsImpl(client))
        }
    }

    override suspend fun ByteReadPacket.decode(bot: QQAndroidBot, sequenceId: Int): Packet? {
        return null
    }
}

@Serializable
internal data class Tlv544Json(
    @SerialName("code")  val code: Int=0,
    @SerialName("data")  val data: String="",
) : JsonStruct

public suspend fun getT544String(bot: Bot, cmd: String="810_9", mode:String="v2"):String{
    val client = bot.asQQAndroidBot().client
    val result: HttpResponse =client.bot.components[HttpClientProvider].getHttpClient().get("http://api.xinrao.moe/v1/energy?version=${client.sdkVersion}&uin=${bot.id}&guid=${client.device.guid.toUHexString().replace(" ","")}&data=$cmd&mode=$mode")
    val load = try {
        val str =  result.bodyAsText()
        str .loadAs(Tlv544Json.serializer())
    } catch (e: Exception) {
        print("t544获取失败")
        Tlv544Json(-1,"")
    }
    return load.data
}

