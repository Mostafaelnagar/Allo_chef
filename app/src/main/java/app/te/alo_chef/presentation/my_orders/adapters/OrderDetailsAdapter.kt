package app.te.alo_chef.presentation.my_orders.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.te.alo_chef.R
import app.te.alo_chef.databinding.ItemMyOrderDetailsBinding
import app.te.alo_chef.presentation.my_orders.ui_state.OrderDetailsItemUiState

class OrderDetailsAdapter :
    RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder>() {
    private val differCallback = object : DiffUtil.ItemCallback<OrderDetailsItemUiState>() {
        override fun areItemsTheSame(
            oldItem: OrderDetailsItemUiState,
            newItem: OrderDetailsItemUiState
        ): Boolean {
            return oldItem.orderMealsItem.id == newItem.orderMealsItem.id
        }

        override fun areContentsTheSame(
            oldItem: OrderDetailsItemUiState,
            newItem: OrderDetailsItemUiState
        ): Boolean {
            return oldItem.orderMealsItem.toString() == newItem.orderMealsItem.toString()
        }
    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_my_order_details, parent, false)
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
        lateinit var itemLayoutBinding: ItemMyOrderDetailsBinding

        init {
            bind()
        }

        fun bind() {
            itemLayoutBinding = DataBindingUtil.bind(itemView)!!
        }

        fun unBind() {
            itemLayoutBinding.unbind()
        }

        fun setModel(item: OrderDetailsItemUiState) {
            itemLayoutBinding.uiState = item
        }
    }

}