package app.te.alo_chef.presentation.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.te.alo_chef.R
import app.te.alo_chef.databinding.ItemHomeDaysBinding
import app.te.alo_chef.presentation.home.eventListener.HomeEventListener
import app.te.alo_chef.presentation.home.ui_state.HomeDaysUiItemState

class DaysAdapter(val homeEventListener: HomeEventListener) :
    RecyclerView.Adapter<DaysAdapter.ViewHolder>() {
    var lastPosition: Int = 0
    private val differCallback = object : DiffUtil.ItemCallback<HomeDaysUiItemState>() {
        override fun areItemsTheSame(
            oldItem: HomeDaysUiItemState,
            newItem: HomeDaysUiItemState
        ): Boolean {
            return oldItem.homeDaysData.id == newItem.homeDaysData.id

        }

        override fun areContentsTheSame(
            oldItem: HomeDaysUiItemState,
            newItem: HomeDaysUiItemState
        ): Boolean {
            return oldItem.toString() == newItem.toString()
        }
    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_home_days, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.itemLayoutBinding.tvHomeItem.setOnClickListener {
            notifyItemChanged(lastPosition)
            lastPosition = position
            homeEventListener.changeDay(data.homeDaysData.date)
            notifyItemChanged(lastPosition)
        }
        if (lastPosition == position) {
            holder.itemLayoutBinding.v16.visibility = View.VISIBLE
        } else {
            holder.itemLayoutBinding.v16.visibility = View.GONE
        }
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
        lateinit var itemLayoutBinding: ItemHomeDaysBinding

        init {
            bind()
        }

        fun bind() {
            itemLayoutBinding = DataBindingUtil.bind(itemView)!!
        }

        fun unBind() {
            itemLayoutBinding.unbind()
        }

        fun setModel(item: HomeDaysUiItemState) {
            itemLayoutBinding.uiState = item
        }
    }

}