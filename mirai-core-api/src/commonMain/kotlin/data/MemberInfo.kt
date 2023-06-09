/*
 * Copyright 2019-2022 Mamoe Technologies and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/mamoe/mirai/blob/dev/LICENSE
 */

package net.mamoe.mirai.data

import net.mamoe.mirai.LowLevelApi
import net.mamoe.mirai.contact.MemberPermission

@LowLevelApi
public interface MemberInfo : UserInfo {
    public val nameCard: String

    public val permission: MemberPermission

    public val specialTitle: String

    public val muteTimestamp: Int

    public val anonymousId: String? get() = null

    /**
     * 入群时间 秒
     */
    public val joinTimestamp: Int

    /**
     * 上次发言时间 秒
     */
    public val lastSpeakTimestamp: Int

    /**
     * 是否为官方机器人
     */
    public val isOfficialBot: Boolean

    /**
     * 活跃等级
     * @see point
     * @since 2.13
     */
    public val rank: Int

    /**
     * 活跃积分
     * @see rank
     * @since 2.13
     */
    public val point: Int

    /**
     * 群荣誉标志
     * @since 2.13
     */
    public val honors: Set<GroupHonorType>

    /**
     * 活跃度
     * @since 2.13
     */
    public val temperature: Int
}