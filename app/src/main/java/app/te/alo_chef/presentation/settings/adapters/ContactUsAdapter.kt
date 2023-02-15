package app.te.alo_chef.presentation.settings.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.te.alo_chef.R
import app.te.alo_chef.databinding.ItemContactBinding
import app.te.alo_chef.presentation.contactus.ContactUsEventListeners
import app.te.alo_chef.presentation.contactus.ContactUsUiState

class ContactUsAdapter(val contactUsEventListeners: ContactUsEventListeners) :
  RecyclerView.Adapter<ContactUsAdapter.ViewHolder>() {
  private val differCallback = object : DiffUtil.ItemCallback<ContactUsUiState>() {
    override fun areItemsTheSame(
      oldItem: ContactUsUiState,
      newItem: ContactUsUiState
    ): Boolean {
      return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
      oldItem: ContactUsUiState,
      newItem: ContactUsUiState
    ): Boolean {
      return oldItem.toString() == newItem.toString()
    }
  }
  val differ = AsyncListDiffer(this, differCallback)
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
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
    lateinit var itemLayoutBinding: ItemContactBinding

    init {
      bind()
    }

    fun bind() {
      itemLayoutBinding = DataBindingUtil.bind(itemView)!!
    }

    fun unBind() {
      itemLayoutBinding.unbind()
    }

    fun setModel(item: ContactUsUiState) {
      itemLayoutBinding.uiState = item
      itemLayoutBinding.eventListener = contactUsEventListeners
    }
  }

}