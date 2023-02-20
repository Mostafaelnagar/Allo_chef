package app.te.alo_chef.presentation.cart.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.te.alo_chef.R
import app.te.alo_chef.databinding.ItemCartBinding
import app.te.alo_chef.presentation.cart.ui_state.ItemCartUiState

class CartAdapter :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    private val differCallback = object : DiffUtil.ItemCallback<ItemCartUiState>() {
        override fun areItemsTheSame(
            oldItem: ItemCartUiState,
            newItem: ItemCartUiState
        ): Boolean {
            return oldItem.mealCart.product_id == newItem.mealCart.product_id
        }

        override fun areContentsTheSame(
            oldItem: ItemCartUiState,
            newItem: ItemCartUiState
        ): Boolean {
            return oldItem.mealCart.toString() == newItem.mealCart.toString()
        }
    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.setModel(data)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.bind()
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.unBind()
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        lateinit var itemLayoutBinding: ItemCartBinding

        init {
            bind()
        }

        fun bind() {
            itemLayoutBinding = DataBindingUtil.bind(itemView)!!
        }

        fun unBind() {
            itemLayoutBinding.unbind()
        }

        fun setModel(item: ItemCartUiState) {
            itemLayoutBinding.uiState = item
        }
    }

}