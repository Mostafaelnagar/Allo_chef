package app.te.alo_chef.presentation.vip

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.te.alo_chef.R
import app.te.alo_chef.databinding.ItemProductBinding
import app.te.alo_chef.databinding.ItemVipMealBinding
import app.te.alo_chef.presentation.home.eventListener.HomeEventListener
import app.te.alo_chef.presentation.home.ui_state.MealsUiState

class VipMealsAdapter :
    RecyclerView.Adapter<VipMealsAdapter.ViewHolder>() {
    private val differCallback = object : DiffUtil.ItemCallback<MealsUiState>() {
        override fun areItemsTheSame(
            oldItem: MealsUiState,
            newItem: MealsUiState
        ): Boolean {
            return oldItem.homeMealsData.id == newItem.homeMealsData.id
        }

        override fun areContentsTheSame(
            oldItem: MealsUiState,
            newItem: MealsUiState
        ): Boolean {
            return oldItem.homeMealsData.toString() == newItem.homeMealsData.toString()
        }
    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vip_meal, parent, false)
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
        lateinit var itemLayoutBinding: ItemVipMealBinding

        init {
            bind()
        }

        fun bind() {
            itemLayoutBinding = DataBindingUtil.bind(itemView)!!
        }

        fun unBind() {
            itemLayoutBinding.unbind()
        }

        fun setModel(item: MealsUiState) {
            itemLayoutBinding.uiState = item
        }
    }

}