package app.te.alo_chef.domain.account.use_case

import app.te.alo_chef.domain.account.entity.request.SendFirebaseTokenRequest
import app.te.alo_chef.domain.account.repository.AccountRepository
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SendFirebaseTokenUseCase @Inject constructor(private val accountRepository: AccountRepository) {

  operator fun invoke(
    firebase_token: String,
    platform: String,
    device_id: String
  ): Flow<Resource<BaseResponse<*>>> = flow {
    emit(
      accountRepository.sendFirebaseToken(
        SendFirebaseTokenRequest(
          firebase_token,
          platform,
          device_id
        )
      )
    )
  }.flowOn(Dispatchers.IO)
}