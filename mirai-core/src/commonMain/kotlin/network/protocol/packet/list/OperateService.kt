/*
 * Copyright 2019-2022 Mamoe Technologies and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/mamoe/mirai/blob/dev/LICENSE
 */

package net.mamoe.mirai.internal.network.protocol.packet.list

import io.ktor.utils.io.core.*
import net.mamoe.mirai.internal.QQAndroidBot
import net.mamoe.mirai.internal.network.Packet
import net.mamoe.mirai.internal.network.QQAndroidClient
import net.mamoe.mirai.internal.network.protocol.data.jce.*
import net.mamoe.mirai.internal.network.protocol.data.jce.RequestPacket
import net.mamoe.mirai.internal.network.protocol.data.jce.VisitorSvcJce
import net.mamoe.mirai.internal.network.protocol.packet.OutgoingPacketFactory
import net.mamoe.mirai.internal.network.protocol.packet.buildOutgoingUniPacket
import net.mamoe.mirai.internal.utils.io.serialization.jceRequestSBuffer
import net.mamoe.mirai.internal.utils.io.serialization.readJceStruct
import net.mamoe.mirai.internal.utils.io.serialization.writeJceStruct
import net.mamoe.mirai.utils.hexToBytes
import net.mamoe.mirai.utils.read

internal class OperateService {
    object VisitorSvc : OutgoingPacketFactory<VisitorSvc.ReqFavorite>("VisitorSvc.ReqFavorite") {

        data class ReqFavorite(val code: Int, val message: String) : Packet

        override suspend fun ByteReadPacket.decode(bot: QQAndroidBot): ReqFavorite {
            val resp =readJceStruct(RequestPacket.serializer())
                .sBuffer.read { readJceStruct(RequestDataVersion2.serializer()) }
                .map["RespFavorite"]?.get("QQService.RespFavorite")
                ?.read { readJceStruct(VisitorSvcRespTemp.serializer()) }
                ?.body?.body
            val resultCode = resp?.result
            var message = resp?.data

            if(resultCode == 0.toByte()){
                return ReqFavorite(200,"点赞成功")
            }else{
                if(message.isNullOrBlank()){
                    message ="点赞失败"
                }
                return ReqFavorite(404,message)
            }

        }

        operator fun invoke(
            client: QQAndroidClient,
            number: Int,
            targetId: Long,//被点赞人
            senderId: Long//点赞人
        ) = buildOutgoingUniPacket(client) {sequenceId->
          writeJceStruct(
                RequestPacket.serializer(),
                RequestPacket(
                    servantName = "VisitorSvc",
                    funcName = "ReqFavorite",
                    requestId = client.nextRequestPacketRequestId(),
                    sBuffer = jceRequestSBuffer(
                        "ReqFavorite",
                        VisitorSvcJce.serializer(),
                        VisitorSvcJce(
                            VisitorSvcValue(uin = senderId, command1 = 1, command2 = sequenceId+1, command3 = 1,command4=0, command5 = "0C 18 00 01 06 01 31 16 01 31".hexToBytes()),
                            senderId = targetId,
                            command2 = 0,
                            command3 = 1,
                            count = number
                        )
                    )
                )
            )
        }


    }
}