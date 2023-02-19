package app.te.alo_chef.data.cart

import androidx.room.*
import app.te.alo_chef.domain.cart.entity.MealCart
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend fun addProduct(productDetails: MealCart)

    @Query("select * from MealCart")
    fun getProducts(): Flow<List<MealCart>>

    @Query("select publishDate from MealCart GROUP BY publishDate")
    fun getDeliveryDates(): Flow<List<String>>

    @Query("select sum(priceAfter) from MealCart")
    fun getCartTotal(): Flow<String>

    @Query("select COUNT(*) from MealCart")
    fun getCartTotalCount(): Flow<Int>

    @Query("select sum(points) from MealCart")
    fun getCartPoints(): Flow<Int>

    @Query("DELETE FROM MealCart WHERE product_room_id=:cartId")
    suspend fun deleteItem(cartId: Int)

    @Query("DELETE FROM MealCart")
    suspend fun emptyProductCart()

    @Query("UPDATE MealCart SET expired= :status where product_id=:productId")
    suspend fun updateStatusProductFromCart(status: Int, productId: Int)

    @Update
    suspend fun updateProduct(productDetails: MealCart)

    @Query("UPDATE MealCart SET quantity=quantity+:quantity,priceAfter=(quantity+:quantity) * priceItem where product_id=:productId")
    fun updateProductQuantity(quantity: Int, productId: Int)

    @Query("SELECT EXISTS (SELECT * FROM MealCart WHERE product_id=:mealId)")
    fun getIfMealExists(mealId: Int): Int

}