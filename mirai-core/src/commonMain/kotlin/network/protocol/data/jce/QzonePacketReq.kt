/*
 * Copyright 2019-2022 Mamoe Technologies and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/mamoe/mirai/blob/dev/LICENSE
 */

package net.mamoe.mirai.internal.network.protocol.data.jce

import io.ktor.http.*
import io.ktor.util.date.*
import io.ktor.utils.io.core.*
import kotlinx.serialization.Serializable
import net.mamoe.mirai.internal.utils.io.JceStruct
import net.mamoe.mirai.internal.utils.io.serialization.tars.TarsId
import net.mamoe.mirai.utils.EMPTY_BYTE_ARRAY
import kotlin.jvm.JvmField
@Serializable
internal class QzonePacketReq(
    @TarsId(0) @JvmField val packetList: Int? = 3,
    @TarsId(1) @JvmField val cPacketType: Int? = 1000027,
    @TarsId(2) @JvmField val uinQQ: Long,
    @TarsId(3) @JvmField val qqVersion: String = "V1_AND_SQ_8.8.90_2828_YYB_D",
    @TarsId(4) @JvmField val commandName: String = "",
    @TarsId(5) @JvmField val urlDevice: String = "",
    @TarsId(6) @JvmField val sBuffer6: QzoneP1 ?= null,
    @TarsId(7) @JvmField val sBuffer7: QzoneP2 ?= null,
    @TarsId(8) @JvmField val pbDate: ByteArray= EMPTY_BYTE_ARRAY,
    @TarsId(9) @JvmField val command9 :ByteArray= EMPTY_BYTE_ARRAY,
    @TarsId(10) @JvmField val command10: Int = 0,
    @TarsId(11) @JvmField val command11: Int = 0,
    @TarsId(12) @JvmField val command12: QzoneP6 ?= null,
    ) : JceStruct

@Serializable
internal class QzonePacketResp(
    @TarsId(0) @JvmField val packetList: Int=0,
    @TarsId(1) @JvmField val uinQQ: Long=0,
    @TarsId(2) @JvmField val command2: Int=0,
    @TarsId(3) @JvmField val command3: Int=0,
    @TarsId(4) @JvmField val commandName: String="",
    @TarsId(5) @JvmField val pbDate: ByteArray= EMPTY_BYTE_ARRAY,
    @TarsId(6) @JvmField val command6 :ByteArray=EMPTY_BYTE_ARRAY,
    @TarsId(7) @JvmField val command7: String="",
    @TarsId(8) @JvmField val command8: String="",
    @TarsId(9) @JvmField val command9: String="",
) : JceStruct

public fun deviceInfo(
    imei :String,
    mac :String,
    model :String,
    sdkVersion:String,
    brand:String,
    md5AndroidId:String,
    product:String):String{
    return "i=$imei&imsi=&mac=$mac&m=$model&o=7.1.2&a=$sdkVersion&sd=0&c64=0&sc=1&p=1080*1920&f=$brand&mm=2022&cf=2407&cc=8&aid=$imei&qimei=1ce33f722b6770abf4988024100019d1670b&qimei36=1ce33f722b6770abf4988024100019d1670b&sharpP=1&n=wifi&support_xsj_live=true&client_mod=default&longitude=&latitude=&client_mod=default&qadid=&md5_android_id=$md5AndroidId&md5_mac=&client_ipv4=&aid_ticket=&taid_ticket=&muid=&muid_type=0&device_ext="+"{\"attri_info\":{\"ua\":\"Dalvik\\/2.1.0+(Linux;+U;+Android+7.1.2;+$model+Build\\/$product)\",\"ua_i\":{\"c_i\":\"92.0.4515.131\",\"s_i\":{\"b_i\":\"$product\",\"b_m\":\"$model\",\"b_mf\":\"$brand\",\"b_r_o_c\":\"7.1.2\",\"b_v_c\":\"REL\",\"b_v_i\":\"700220913\",\"b_v_r\":\"7.1.2\",\"jvm_v\":\"2.1.0\",\"sw_s\":\"1\"}}},\"font_size\":1,\"harmony_sys_info\":{\"harmony_pure_mode\":-1,\"is_harmony_os\":false},\"hevc_compatibility_info\":[{\"max_fps\":30,\"max_luma_samples\":\"0\",\"video_player_type\":1}],\"mqq_config_status\":1,\"qi36\":\"1ce33f722b6770abf4988024100019d1670b\",\"qqb_external_exp_info\":{\"traffic_type\":26},\"targeting_ability\":{\"support_quick_app_link\":false,\"web_wx_mgame\":true},\"wechat_installed_info\":{\"api_ver\":\"0\"}}".encodeURLParameter()+"&qimei=1ce33f722b6770abf4988024100019d1670b&longitude=&latitude=&coordtype=0&timezone=+8,id:Asia/Shanghai&is_teenager_mod=0&is_care_mod=0"
}


@Serializable
internal class QzoneP1(
    @TarsId(0) @JvmField val command1: Int = 64,
    @TarsId(1) @JvmField val command2: ByteArray= "".toByteArray(),
    @TarsId(2) @JvmField val command3: Map<Byte,ByteArray> = mapOf(1.toByte()  to byteArrayOf(0)),
):JceStruct

@Serializable
internal class QzoneP2(
    @TarsId(0) @JvmField val command1: Byte = 0,
    @TarsId(1) @JvmField val command2: Byte = 0,
    @TarsId(2) @JvmField val command3: Byte = 0,
    @TarsId(3) @JvmField val command4: ByteArray = byteArrayOf(0,0,0,0,0,0),
):JceStruct

@Serializable
internal class QzoneP3(
    @TarsId(0) @JvmField val command1: Map<String,Map<String,ByteArray>>
):JceStruct

@Serializable
internal class QzoneP3U1(
    @TarsId(0) @JvmField val uid:Long,
    @TarsId(1) @JvmField val uin:Long,
    @TarsId(2) @JvmField val command2:Int=0,
    @TarsId(3) @JvmField val message:String="",
    @TarsId(4) @JvmField val value:Map<Int,Long> = mapOf(22 to 0,28 to 0,40 to 1527782400,25 to 0)
    ):JceStruct

@Serializable
internal class QzoneP4(
    @TarsId(0) @JvmField val value1: QzoneP3U1 ?= null,
):JceStruct

@Serializable
internal class QzoneP5(
    @TarsId(0) @JvmField val value1: QzoneP5U1 ?= null,
):JceStruct

@Serializable
internal class QzoneP5U1(
    @TarsId(0) @JvmField val value1: Byte =1,
    @TarsId(1) @JvmField val value2: Int =131,
    @TarsId(2) @JvmField val value3: Byte =1,
):JceStruct

@Serializable
internal class QzoneP6(
    @TarsId(0) @JvmField val value1: Byte =0,
    @TarsId(1) @JvmField val value2: Byte =0,
    @TarsId(2) @JvmField val value3: Long = getTimeMillis(),
):JceStruct

@Serializable
internal class QzoneEmpty(
    @TarsId(0) @JvmField val value1: QzoneHost?=null,
):JceStruct

@Serializable
internal class QzoneFullConstruct(
    @TarsId(0) @JvmField val value1: QzoneFull?=null,
):JceStruct

@Serializable
internal class QzoneFull(
    @TarsId(0) @JvmField val uin: Long=0,
    @TarsId(1) @JvmField val funny1: String,
    @TarsId(2) @JvmField val funny2: String,
    @TarsId(3) @JvmField val value3: Int=0,
    @TarsId(4) @JvmField val value4: Int=0,
    @TarsId(5) @JvmField val value5:Map<Int,String> = mapOf(180 to "FunnyTread"),
    @TarsId(6) @JvmField val uid: Long=0,
    @TarsId(8)@JvmField val value8 :Map<String,String>?=null
):JceStruct

internal fun getFunnyString(uin:Long,uid:Long,type:Int,number:Int) :QzoneFull{
    require(type in 1..3)
    require(number > 0)

    var actionType= "1"
    var actionId = "1"
    when (type) {
        1 -> {
            actionType = "1"
            actionId ="1"
        }
        2 -> {
            actionType = "1"
            actionId="2"
        }
        3 -> {
            actionType ="0"
            actionId ="3"
        }
    }
    val funny = "funny_space_312_"+uin.toString()+"_"+uid.toString()+"_"+actionId
    return QzoneFull(uin=uin, funny1 = funny,funny2=funny,uid = uid, value8 = mapOf("funny_tread_count" to number.toString(),"action_type" to actionType,"action_id" to actionId))
}

@Serializable
internal class QzoneHost(
    @TarsId(0) @JvmField val value1: Long,
):JceStruct

@Serializable
internal class QzoneTemp(
    @TarsId(0) @JvmField val body:QzoneP7?=null
):JceStruct

@Serializable
internal class QzoneTempA2(
    @TarsId(0) @JvmField val body:QzoneTempD2?=null,
):JceStruct

@Serializable
internal class QzoneTempD2(
    @TarsId(0) @JvmField val body:QzoneP8?=null,
    @TarsId(1) @JvmField val value :Int=0
):JceStruct


@Serializable
internal class QzoneP7(
    @TarsId(0) @JvmField val value1: Int =0,
    @TarsId(1) @JvmField val value2: Int =0,
    @TarsId(2) @JvmField val value3: Int = 0,
    @TarsId(3) @JvmField val value4: Int = 0
):JceStruct

@Serializable
internal class QzoneP8(
    @TarsId(0) @JvmField val value1: Int =0,
    @TarsId(1) @JvmField val value2: Int =0,
    @TarsId(2) @JvmField val value3: Int = 0,
    @TarsId(3) @JvmField val  command4 :ByteArray=EMPTY_BYTE_ARRAY
):JceStruct

@Serializable
internal class QzonePB1_4(
    @TarsId(0) @JvmField val value: Int =0,
    @TarsId(1) @JvmField val value1: Int =0,
    @TarsId(2) @JvmField val value2: String = "",
    @TarsId(3) @JvmField val  value3 :String = "",
    @TarsId(4) @JvmField val  value4 :String = "",
    @TarsId(5) @JvmField val  value5 :String = ""
):JceStruct


