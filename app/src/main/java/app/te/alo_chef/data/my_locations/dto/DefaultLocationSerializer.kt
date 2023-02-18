package app.te.alo_chef.data.my_locations.dto

import androidx.annotation.Keep
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.structure.base_mvvm.DefaultLocation
import java.io.InputStream
import java.io.OutputStream

@Keep
object DefaultLocationSerializer: Serializer<DefaultLocation> {
    override val defaultValue: DefaultLocation = DefaultLocation.getDefaultInstance()
    override suspend fun readFrom(input: InputStream): DefaultLocation {
        try {
            return DefaultLocation.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: DefaultLocation, output: OutputStream) = t.writeTo(output)
}
