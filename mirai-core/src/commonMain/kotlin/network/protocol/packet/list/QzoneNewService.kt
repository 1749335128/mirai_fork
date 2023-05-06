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
import net.mamoe.mirai.internal.network.protocol.packet.OutgoingPacketFactory
import net.mamoe.mirai.internal.network.protocol.packet.buildOutgoingUniPacket
import net.mamoe.mirai.internal.utils.io.serialization.readJceStruct
import net.mamoe.mirai.internal.utils.io.serialization.toByteArray
import net.mamoe.mirai.internal.utils.io.serialization.writeJceStruct
import net.mamoe.mirai.utils.*

internal class QzoneNewService {
    data class QzoneResp(val code: Int, val message: String) : Packet
    internal object GetMainPage :
        OutgoingPacketFactory<QzoneResp>("SQQzoneSvc.getMainPage") {

        override suspend fun ByteReadPacket.decode(bot: QQAndroidBot): QzoneResp {
            val res = this.readJceStruct(QzonePacketResp.serializer())
//            val p6 =  res.command6.read { readJceStruct(QzoneP3.serializer()) }.command1
//            val p6busiCompCtl   = p6["busiCompCtl"]?.get("QMF_PROTOCAL.QmfBusiControl")
//            val p6server_info   = p6["server_info"]?.get("QMF_PROTOCAL.QmfServerInfo")
//            val p6busiCompCtl_value = p6busiCompCtl?.read { readJceStruct(QzoneTemp.serializer()) }?.body
//            val p6server_info_value = p6server_info?.read { readJceStruct(QzoneTempA2.serializer()) }?.body
            val value: Int = try {
                val pb = res.pbDate.inflate().read { readJceStruct(QzoneP3.serializer()) }.command1["getMainPage"]
                    ?.get("NS_MOBILE_MAIN_PAGE.mobile_main_page_rsp")
                pb?.get(3)?.toInt()!!
            } catch (e: Exception) {
                5
            }
            //基本没有用上面数据
            return if (res.command2 >= 0 && res.command3 >= 0) {
                if (value > 2) {
                    QzoneResp(501, "访问被拒")
                } else {
                    QzoneResp(200, "访问成功")
                }
            } else {
                QzoneResp(404, "协议出错了!")
            }

        }

        operator fun invoke(
            client: QQAndroidClient,
            uid: Long,//对方QQ
        ) = buildOutgoingUniPacket(client) {sequenceId->
            writeJceStruct(
                QzonePacketReq.serializer(),
                QzonePacketReq(
                    packetList = sequenceId,
                    uinQQ = client.uin,
                    commandName = "QzoneNewService.getMainPage",
                    urlDevice = deviceInfo(
                        client.device.imei, client.device.macAddress.decodeToString(),
                        client.device.model.decodeToString(), client.device.version.sdk.toString(),
                        client.device.brand.decodeToString(),
                        client.device.androidId.decodeToString().md5().toUHexString().lowercase().replace(" ", ""),
                        client.device.product.decodeToString()
                    ),
                    sBuffer6 = QzoneP1(),
                    sBuffer7 = QzoneP2(),
                    pbDate = QzoneP3(
                        command1 = mapOf(
                            "getMainPage" to mapOf(
                                "NS_MOBILE_MAIN_PAGE.mobile_main_page_req" to QzoneP4(
                                    QzoneP3U1(uid = uid, uin = client.uin)
                                ).toByteArray(QzoneP4.serializer())
                            ), "hostuin" to mapOf("int64" to QzoneHost(client.uin).toByteArray(QzoneHost.serializer()))
                        ),
                    ).toByteArray(QzoneP3.serializer()).deflate(),
                    command9 = QzoneP3(
                        mapOf(
                            "busiCompCtl" to mapOf(
                                "QMF_PROTOCAL.QmfBusiControl" to QzoneP5(QzoneP5U1()).toByteArray(
                                    QzoneP5.serializer()
                                )
                            )
                        )
                    ).toByteArray(QzoneP3.serializer()),
                    command12 = QzoneP6()
                )
            )
        }

    }

    internal object GetFacade :
        OutgoingPacketFactory<QzoneResp>("SQQzoneSvc.getFacade") {
        override suspend fun ByteReadPacket.decode(bot: QQAndroidBot): QzoneResp {
            val res = this.readJceStruct(QzonePacketResp.serializer())
            val code = if (res.command2 >= 0 && res.command3 >= 0) 200 else 404
            return QzoneResp(code, if (code == 200) "访问成功" else "访问失败")
        }

        operator fun invoke(
            client: QQAndroidClient,
            uid: Long,//对方QQ
        ) = buildOutgoingUniPacket(client) {sequenceId->
            writeJceStruct(
                QzonePacketReq.serializer(),
                QzonePacketReq(
                    packetList = sequenceId,
                    uinQQ = client.uin,
                    commandName = "QzoneNewService.Custom.getFacade",
                    urlDevice = deviceInfo(
                        client.device.imei, client.device.macAddress.decodeToString(),
                        client.device.model.decodeToString(), client.device.version.sdk.toString(),
                        client.device.brand.decodeToString(),
                        client.device.androidId.decodeToString().md5().toUHexString().lowercase().replace(" ", ""),
                        client.device.product.decodeToString()
                    ),
                    sBuffer6 = QzoneP1(),
                    sBuffer7 = QzoneP2(),
                    pbDate = QzoneP3(
                        command1 = mapOf(
                            "hostuin" to mapOf("int64" to QzoneHost(client.uin).toByteArray(QzoneHost.serializer())),
                            "getFacade" to mapOf(
                                "NS_MOBILE_CUSTOM.mobile_facade_get_req" to QzoneEmpty(QzoneHost(uid)).toByteArray(
                                    QzoneEmpty.serializer()
                                )
                            )
                        ),
                    ).toByteArray(QzoneP3.serializer()).deflate(),
                    command9 = QzoneP3(
                        mapOf(
                            "busiCompCtl" to mapOf(
                                "QMF_PROTOCAL.QmfBusiControl" to QzoneP5(
                                    QzoneP5U1(
                                        value2 = 104
                                    )
                                ).toByteArray(QzoneP5.serializer())
                            )
                        )
                    ).toByteArray(QzoneP3.serializer()),
                    command12 = QzoneP6()
                )
            )
        }

    }

    internal object Like:
        OutgoingPacketFactory<QzoneResp>("SQQzoneSvc.like") {

            override suspend fun ByteReadPacket.decode(bot: QQAndroidBot): QzoneResp {
                val res = this.readJceStruct(QzonePacketResp.serializer())
                val code = if (res.command2 >= 0 && res.command3 >= 0) 200 else 404
                return QzoneResp(code, if (code == 200) "互动成功" else "互动失败")
            }

            operator fun invoke(
                client: QQAndroidClient,
                uid: Long,//对方QQ
                type:Int,
                number:Int
            ) = buildOutgoingUniPacket(client) {sequenceId->
                writeJceStruct(
                    QzonePacketReq.serializer(),
                    QzonePacketReq(
                        packetList = sequenceId,
                        uinQQ = client.uin,
                        commandName = "QzoneNewService.like",
                        urlDevice = deviceInfo(
                            client.device.imei, client.device.macAddress.decodeToString(),
                            client.device.model.decodeToString(), client.device.version.sdk.toString(),
                            client.device.brand.decodeToString(),
                            client.device.androidId.decodeToString().md5().toUHexString().lowercase().replace(" ", ""),
                            client.device.product.decodeToString()
                        ),
                        sBuffer6 = QzoneP1(),
                        sBuffer7 = QzoneP2(),
                        pbDate = QzoneP3(
                            command1 = mapOf(
                                "like" to mapOf(
                                    "NS_MOBILE_OPERATION.operation_like_req" to QzoneFullConstruct(getFunnyString(uin=client.uin,uid = uid,type = type,number = number)).toByteArray(QzoneFullConstruct.serializer())
                                ),
                                "hostuin" to mapOf("int64" to QzoneHost(client.uin).toByteArray(QzoneHost.serializer()))
                            ),
                        ).toByteArray(QzoneP3.serializer()).deflate(),
                        command9 = QzoneP3(
                            mapOf(
                                "busiCompCtl" to mapOf(
                                    "QMF_PROTOCAL.QmfBusiControl" to QzoneP5(
                                        QzoneP5U1(
                                            value2 = 262
                                        )
                                    ).toByteArray(QzoneP5.serializer())
                                )
                            )
                        ).toByteArray(QzoneP3.serializer()),
                        command12 = QzoneP6(value1 = 1)
                    )
                )
            }
    }

}