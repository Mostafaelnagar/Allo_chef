package app.te.alo_chef.data.general.dto.config


import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "GeneralConfig")
@Keep
data class GeneralConfig(
    @PrimaryKey(autoGenerate = true)
    val roomId: Int? = null,
    @SerializedName("payment_ways")
    val paymentWays: List<PaymentWay> = listOf(),
    @SerializedName("setting")
    val setting: Setting = Setting()
)