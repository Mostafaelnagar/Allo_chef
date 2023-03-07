package app.te.alo_chef.data.general.data_source.local

import androidx.room.TypeConverter
import app.te.alo_chef.data.general.dto.config.PaymentWay
import app.te.alo_chef.data.general.dto.config.Setting
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {
    @TypeConverter
    fun fromPaymentWayString(value: String): List<PaymentWay> {
        val listType: Type = object : TypeToken<List<PaymentWay>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromPaymentWayList(list: List<PaymentWay>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromSettingString(value: String): Setting {
        val listType: Type = object : TypeToken<Setting>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromSetting(list: Setting): String {
        val gson = Gson()
        return gson.toJson(list)
    }

}