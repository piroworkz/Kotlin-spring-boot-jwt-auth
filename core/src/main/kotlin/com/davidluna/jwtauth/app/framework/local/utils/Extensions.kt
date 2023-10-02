package com.davidluna.jwtauth.app.framework.local.utils

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredMemberProperties

inline fun <reified T : Any> ResultRow.toDomain(table: Table): T {
    val obj = T::class.createInstance()
    T::class.declaredMemberProperties.forEach { prop ->
        if (prop is KMutableProperty1<*, *>) {
            val column: Column<*>? = prop.name.let {
                table.columns.find { column -> column.name == it }
            }
            prop.setter.call(obj, this[column!!])
        }
    }
    return obj
}

inline fun <reified T : Any> T.toDatabaseRow(table: Table): HashMap<String, Any?> {
    val map = HashMap<String, Any?>()

    T::class.declaredMemberProperties.forEach { prop ->
        val columnName = prop.name
        val column = table.columns.find { it.name == columnName }
        if (column != null) {
            val value = prop.get(this)
            map[column.name] = value
        }
    }

    return map
}

fun <T : Table> InsertStatement<Number>.fill(table: T, row: Map<String, Any?>) {
    row.forEach { (key, value) ->
        val column = table.columns.find { it.name == key }
        if (column != null) {
            @Suppress("UNCHECKED_CAST")
            this[column as Column<Any?>] = value
        }
    }
}

fun <T : Table> UpdateStatement.fill(table: T, row: Map<String, Any?>) {
    row.forEach { (key, value) ->
        val column = table.columns.find { it.name == key }
        if (column != null) {
            @Suppress("UNCHECKED_CAST")
            this[column as Column<Any?>] = value
        }
    }
}
