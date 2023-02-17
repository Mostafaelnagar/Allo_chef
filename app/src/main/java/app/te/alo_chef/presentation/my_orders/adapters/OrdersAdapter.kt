package app.te.alo_chef.presentation.my_orders.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.te.alo_chef.R
import app.te.alo_chef.databinding.ItemMyOrderBinding
import app.te.alo_chef.presentation.my_orders.ui_state.OrderItemUiState

class OrdersAdapter :
    RecyclerView.Adapter<OrdersAdapter.ViewHolder>() {
    private val differCallback = object : DiffUtil.ItemCallback<OrderItemUiState>() {
        override fun areItemsTheSame(
            oldItem: OrderItemUiState,
            newItem: OrderItemUiState
        ): Boolean {
            return oldItem.myOrdersData.id == newItem.myOrdersData.id
        }

        override fun areContentsTheSame(
            oldItem: OrderItemUiState,
            newItem: OrderItemUiState
        ): Boolean {
            return oldItem.myOrdersData.toString() == newItem.myOrdersData.toString()
        }
    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_my_order, parent, false)
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
        lateinit var itemLayoutBinding: ItemMyOrderBinding

        init {
            bind()
        }

        fun bind() {
            itemLayoutBinding = DataBindingUtil.bind(itemView)!!
        }

        fun unBind() {
            itemLayoutBinding.unbind()
        }

        fun setModel(item: OrderItemUiState) {
            itemLayoutBinding.itemState = item
        }
    }

}