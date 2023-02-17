package app.te.alo_chef.presentation.wallet.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.te.alo_chef.R
import app.te.alo_chef.databinding.ItemTransactionBinding
import app.te.alo_chef.presentation.wallet.ui_state.ItemTransactionUiState

class WalletTransactionsAdapter :
    RecyclerView.Adapter<WalletTransactionsAdapter.ViewHolder>() {
    private val differCallback = object : DiffUtil.ItemCallback<ItemTransactionUiState>() {
        override fun areItemsTheSame(
            oldItem: ItemTransactionUiState,
            newItem: ItemTransactionUiState
        ): Boolean {
            return oldItem.item.id == newItem.item.id
        }

        override fun areContentsTheSame(
            oldItem: ItemTransactionUiState,
            newItem: ItemTransactionUiState
        ): Boolean {
            return oldItem.toString() == newItem.toString()
        }
    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
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
        lateinit var itemLayoutBinding: ItemTransactionBinding

        init {
            bind()
        }

        fun bind() {
            itemLayoutBinding = DataBindingUtil.bind(itemView)!!
        }

        fun unBind() {
            itemLayoutBinding.unbind()
        }

        fun setModel(item: ItemTransactionUiState) {
            itemLayoutBinding.itemUiState = item
        }
    }

}