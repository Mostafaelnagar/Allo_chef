package app.te.alo_chef.presentation.checkout.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.te.alo_chef.R
import app.te.alo_chef.data.checkout.dto.DeliveryTimes
import app.te.alo_chef.databinding.ItemDeliveryTimeBinding
import app.te.alo_chef.presentation.base.extensions.setupStoke

class DeliveryTimesAdapter :
    RecyclerView.Adapter<DeliveryTimesAdapter.ViewHolder>() {
    var lastSelected: Int = -1
    private val differCallback = object : DiffUtil.ItemCallback<DeliveryTimes>() {
        override fun areItemsTheSame(
            oldItem: DeliveryTimes,
            newItem: DeliveryTimes
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: DeliveryTimes,
            newItem: DeliveryTimes
        ): Boolean {
            return oldItem.toString() == newItem.toString()
        }
    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_delivery_time, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.itemLayoutBinding.timeSlot.setOnClickListener {
            notifyItemChanged(lastSelected)
            lastSelected = position
            notifyItemChanged(lastSelected)
        }
        if (lastSelected == position)
            holder.itemLayoutBinding.timeSlot.setupStoke(R.color.colorPrimaryDark)
        else
            holder.itemLayoutBinding.timeSlot.setupStoke(R.color.white)
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
        lateinit var itemLayoutBinding: ItemDeliveryTimeBinding

        init {
            bind()
        }

        fun bind() {
            itemLayoutBinding = DataBindingUtil.bind(itemView)!!
        }

        fun unBind() {
            itemLayoutBinding.unbind()
        }

        fun setModel(item: DeliveryTimes) {
            itemLayoutBinding.item = item
        }
    }

}