package app.te.alo_chef.data.remote

object Keys {
  init {
    System.loadLibrary("native-lib")
    /**
     * Google account =>ihab.allochef@gmail.com
     * Google password => allo_chef_2022
     */
  }

  external fun debugBaseUrl(): String
  external fun releaseBaseUrl(): String
  external fun appDataStore(): String
  external fun appDataStoreFirstTime(): String
  external fun userDataStoreFileName(): String
  external fun defaultLocationStoreFileName(): String
  external fun firebaseToken(): String
  external fun userToken(): String
  external fun firstTime(): String
  external fun isLoggedIn(): String
  external fun lang(): String
  external fun splash(): String
  external fun platForm(): String
  external fun serverClientId(): String

}