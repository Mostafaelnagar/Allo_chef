package app.te.alo_chef.presentation.checkout.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.te.alo_chef.R
import app.te.alo_chef.databinding.ItemPaymentBinding
import app.te.alo_chef.presentation.checkout.ui_state.ItemPayment

class PaymentTypesAdapter :
    RecyclerView.Adapter<PaymentTypesAdapter.ViewHolder>() {
    var lastSelected: Int = -1
    private val differCallback = object : DiffUtil.ItemCallback<ItemPayment>() {
        override fun areItemsTheSame(
            oldItem: ItemPayment,
            newItem: ItemPayment
        ): Boolean {
            return oldItem.type == newItem.type
        }

        override fun areContentsTheSame(
            oldItem: ItemPayment,
            newItem: ItemPayment
        ): Boolean {
            return oldItem.toString() == newItem.toString()
        }
    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_payment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.itemLayoutBinding.paymentItem.setOnClickListener {
            notifyItemChanged(lastSelected)
            lastSelected = position
            notifyItemChanged(lastSelected)
        }
        holder.itemLayoutBinding.tvDeliveryPayment.isChecked = lastSelected == position
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
        lateinit var itemLayoutBinding: ItemPaymentBinding

        init {
            bind()
        }

        fun bind() {
            itemLayoutBinding = DataBindingUtil.bind(itemView)!!
        }

        fun unBind() {
            itemLayoutBinding.unbind()
        }

        fun setModel(item: ItemPayment) {
            itemLayoutBinding.item = item
        }
    }

}