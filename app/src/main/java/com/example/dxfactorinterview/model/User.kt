package com.example.dxfactorinterview.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(

    @ColumnInfo(name = "firstName")
    var firstName : String,

    @ColumnInfo(name = "lastName")
    var lastName : String,

    @ColumnInfo(name = "email")
    var email : String,

    @ColumnInfo(name = "password")
    var password : String,

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var data: ByteArray?,

    @PrimaryKey(autoGenerate = true)
    val id : Int = 0
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (email != other.email) return false
        if (password != other.password) return false
        if (data != null) {
            if (other.data == null) return false
            if (!data.contentEquals(other.data)) return false
        } else if (other.data != null) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + (data?.contentHashCode() ?: 0)
        result = 31 * result + id
        return result
    }
}