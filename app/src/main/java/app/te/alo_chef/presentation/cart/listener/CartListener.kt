package app.te.alo_chef.presentation.cart.listener

interface CartListener {
    fun updateProductQuantity(productId: Int, quantity: Int)
    fun deleteItem(roomId: Int)
    fun openCheckout()
}