/*
 * Copyright 2019-2022 Mamoe Technologies and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/mamoe/mirai/blob/dev/LICENSE
 */

package net.mamoe.mirai.internal.network.protocol.data.jce

import kotlinx.serialization.Serializable
import net.mamoe.mirai.internal.network.Packet
import net.mamoe.mirai.internal.utils.io.JceStruct
import net.mamoe.mirai.internal.utils.io.serialization.tars.TarsId
import kotlin.jvm.JvmField
@Serializable
internal class VisitorSvcJce(
    @TarsId(0) @JvmField
    val data: VisitorSvcValue?=null,
    @TarsId(1) @JvmField
    val senderId: Long,
    @TarsId(2) @JvmField
    val command2: Int = 0,
    @TarsId(3) @JvmField
    val command3: Int = 1,
    @TarsId(4) @JvmField
    val count: Int = 1,
):JceStruct

@Serializable
internal class VisitorSvcValue(
    @TarsId(0) @JvmField
    val uin: Long,
    @TarsId(1) @JvmField
    val command1: Int,
    @TarsId(2) @JvmField
    val command2: Int,
    @TarsId(3) @JvmField
    val command3: Int,
    @TarsId(4) @JvmField
    val command4: Int,
    @TarsId(5) @JvmField
    val command5: ByteArray? = null
):JceStruct,Packet


@Serializable
internal class VisitorSvcRespValue(
    @TarsId(0) @JvmField
    val body:VisitorSvcRespResult,
    @TarsId(1) @JvmField
    val uid:Long,
    @TarsId(2) @JvmField
    val command2:Byte,
    @TarsId(3) @JvmField
    val resultList:ByteArray?=null,
    @TarsId(4) @JvmField
    val command4:Int,
    @TarsId(21) @JvmField
    val command21:Int,
    ):JceStruct,Packet
@Serializable
internal class VisitorSvcRespTemp(
    @TarsId(0) @JvmField
    val body:VisitorSvcRespValue
):JceStruct,Packet
@Serializable
internal class VisitorSvcRespResult(
    @TarsId(0) @JvmField
    val command1:Int,
    @TarsId(1) @JvmField
    val command2: Int,
    @TarsId(2) @JvmField
    val command3:Long,
    @TarsId(3) @JvmField
    val result:Byte,
    @TarsId(4) @JvmField
    val data:String
):JceStruct,Packet