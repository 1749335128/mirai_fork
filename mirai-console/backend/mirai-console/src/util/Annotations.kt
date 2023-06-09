/*
 * Copyright 2019-2021 Mamoe Technologies and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/mamoe/mirai/blob/dev/LICENSE
 */

@file:JvmMultifileClass
@file:JvmName("ConsoleUtils")

package net.mamoe.mirai.console.util

import kotlin.annotation.AnnotationTarget.*

/**
 * 表明这个 API 是为了让 Java 使用者调用更方便. Kotlin 使用者不应该使用这些 API.
 */
@RequiresOptIn(level = RequiresOptIn.Level.ERROR)
@Target(PROPERTY, FUNCTION, CLASS)
internal annotation class JavaFriendlyApi

/**
 * 标记为一个仅供 mirai-console 内部使用的 API.
 *
 * 这些 API 可能会在任意时刻更改, 且不会发布任何预警.
 * 非常不建议在发行版本中使用这些 API.
 */
@Retention(AnnotationRetention.BINARY)
@RequiresOptIn(level = RequiresOptIn.Level.ERROR)
@Target(CLASS, TYPEALIAS, FUNCTION, PROPERTY, FIELD, CONSTRUCTOR, CLASS, FUNCTION, PROPERTY)
@MustBeDocumented
public annotation class ConsoleInternalApi(
    val message: String = "",
)

/**
 * 标记一个实验性的 API.
 *
 * 这些 API 不具有稳定性, 且可能会在任意时刻更改.
 * 不建议在发行版本中使用这些 API.
 */
@Retention(AnnotationRetention.BINARY)
@RequiresOptIn(level = RequiresOptIn.Level.WARNING)
@Target(CLASS, TYPEALIAS, FUNCTION, PROPERTY, FIELD, CONSTRUCTOR)
@MustBeDocumented
public annotation class ConsoleExperimentalApi(
    val message: String = "",
)