package app.te.alo_chef.presentation.subscriptions.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.te.alo_chef.R
import app.te.alo_chef.databinding.ItemSubscriptionBinding
import app.te.alo_chef.presentation.subscriptions.ui_state.SubscriptionItemUiState
import com.github.islamkhsh.CardSliderAdapter

class SubscriptionsAdapters : CardSliderAdapter<SubscriptionsAdapters.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<SubscriptionItemUiState>() {
        override fun areItemsTheSame(
            oldItem: SubscriptionItemUiState,
            newItem: SubscriptionItemUiState
        ): Boolean {
            return oldItem.subscriptionData.id == newItem.subscriptionData.id
        }

        override fun areContentsTheSame(
            oldItem: SubscriptionItemUiState,
            newItem: SubscriptionItemUiState
        ): Boolean {
            return oldItem.subscriptionData.toString() == newItem.subscriptionData.toString()
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun bindVH(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.setModel(data)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_subscription, parent, false)
        return ViewHolder(view)
    }

    override fun onViewAttachedToWindow(holder: SubscriptionsAdapters.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.bind()
    }

    override fun onViewDetachedFromWindow(holder: SubscriptionsAdapters.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.unBind()
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        lateinit var itemLayoutBinding: ItemSubscriptionBinding

        init {
            bind()
        }

        fun bind() {
            itemLayoutBinding = DataBindingUtil.bind(itemView)!!
        }

        fun unBind() {
            itemLayoutBinding.unbind()
        }

        fun setModel(item: SubscriptionItemUiState) {
            itemLayoutBinding.uiState = item
        }
    }

}