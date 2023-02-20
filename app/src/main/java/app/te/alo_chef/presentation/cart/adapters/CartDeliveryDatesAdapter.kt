package app.te.alo_chef.presentation.cart.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.te.alo_chef.R
import app.te.alo_chef.databinding.ItemDeliveryBinding
import app.te.alo_chef.presentation.cart.listener.CartListener

class CartDeliveryDatesAdapter(private val cartListener: CartListener) :
    RecyclerView.Adapter<CartDeliveryDatesAdapter.ViewHolder>() {
    private val differCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_delivery, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == 0)
            cartListener.callSavedLocation(differ.currentList.size)
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
        lateinit var itemLayoutBinding: ItemDeliveryBinding

        init {
            bind()
        }

        fun bind() {
            itemLayoutBinding = DataBindingUtil.bind(itemView)!!
        }

        fun unBind() {
            itemLayoutBinding.unbind()
        }

        fun setModel(item: String) {
            itemLayoutBinding.deliveryDate = item
        }
    }

}