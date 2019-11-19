/**
 * IUserAgent.java 2015-4-29
 * 龙德恒方科技发展有限公司(c) 2015 - 2015 。
 */
package com.shark.retrofit.http.header

import android.content.Context

/**
 * **IUserAgent。**
 *
 * **详细说明：**
 *
 * 无。
 *
 * **修改列表：**
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 *
 * <tr><td>1</td><td>Renyuxiang</td><td>2015-4-29 下午2:58:19</td><td>建立类型</td></tr>
 * <tr><td>2</td><td>Renyuxiang</td><td>2019-11-19 下午2:20:00</td><td>迁移到Kotlin</td></tr>
 * </table> *
 *
 * @author Renyuxiang
 * @version 2.0
 * @since 1.0
 */
interface IUserAgent {
    val context: Context

    fun generateUA(): String
}
