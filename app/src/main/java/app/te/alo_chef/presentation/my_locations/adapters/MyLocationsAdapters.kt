package app.te.alo_chef.presentation.my_locations.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.te.alo_chef.R
import app.te.alo_chef.data.my_locations.dto.LocationsData
import app.te.alo_chef.databinding.ItemMyLocationBinding
import app.te.alo_chef.presentation.my_locations.listeners.LocationsListener

class MyLocationsAdapters(val locationsListener: LocationsListener) :
    RecyclerView.Adapter<MyLocationsAdapters.ViewHolder>() {
    private val differCallback = object : DiffUtil.ItemCallback<LocationsData>() {
        override fun areItemsTheSame(
            oldItem: LocationsData,
            newItem: LocationsData
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: LocationsData,
            newItem: LocationsData
        ): Boolean {
            return oldItem.toString() == newItem.toString()
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.itemLayoutBinding.radio.setOnClickListener {
            locationsListener.saveAsDefault(data)
            resubmitLocations(data)
        }
        holder.setModel(data)
    }

    private fun resubmitLocations(selectedItem: LocationsData) {
        val newItems: MutableList<LocationsData> = mutableListOf()
        differ.currentList.map {
            if (selectedItem.id == it.id) {
                newItems.add(it.copy(main = "1", selected = true))
            } else
                newItems.add(it.copy(main = "0", selected = false))
        }

        differ.submitList(newItems)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_my_location, parent, false)
        return ViewHolder(view)
    }

    override fun onViewAttachedToWindow(holder: MyLocationsAdapters.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.bind()
    }

    override fun onViewDetachedFromWindow(holder: MyLocationsAdapters.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.unBind()
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        lateinit var itemLayoutBinding: ItemMyLocationBinding

        init {
            bind()
        }

        fun bind() {
            itemLayoutBinding = DataBindingUtil.bind(itemView)!!
        }

        fun unBind() {
            itemLayoutBinding.unbind()
        }

        fun setModel(item: LocationsData) {
            itemLayoutBinding.event = locationsListener
            itemLayoutBinding.item = item
        }
    }


}