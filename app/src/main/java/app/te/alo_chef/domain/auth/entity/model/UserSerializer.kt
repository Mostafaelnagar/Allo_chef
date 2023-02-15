package app.te.alo_chef.domain.auth.entity.model

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.structure.base_mvvm.User
import java.io.InputStream
import java.io.OutputStream

object UserSerializer: Serializer<User> {
  override val defaultValue: User = User.getDefaultInstance()
  override suspend fun readFrom(input: InputStream): User {
    try {
      return User.parseFrom(input)
    } catch (exception: InvalidProtocolBufferException) {
      throw CorruptionException("Cannot read proto.", exception)
    }
  }

  override suspend fun writeTo(t: User, output: OutputStream) = t.writeTo(output)
}
