package app.te.alo_chef.domain.utils

enum class FailureStatus {
  EMPTY,
  API_FAIL,
  NO_INTERNET,
  OTHER,
  NOT_ACTIVE,
  TOKEN_EXPIRED
}