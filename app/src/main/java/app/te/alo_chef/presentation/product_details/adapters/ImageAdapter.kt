package app.te.alo_chef.presentation.product_details.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import app.te.alo_chef.R
import app.te.alo_chef.databinding.ItemSliderImageBinding

class ImageAdapter(private val imageList: ArrayList<String>, private val viewPager2: ViewPager2) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_slider_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setModel(imageList[position])
        if (position == imageList.size - 1) {
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    private val runnable = Runnable {
        imageList.addAll(imageList)
        notifyDataSetChanged()
    }
    override fun onViewAttachedToWindow(holder: ImageAdapter.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.bind()
    }

    override fun onViewDetachedFromWindow(holder: ImageAdapter.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.unBind()
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        lateinit var itemLayoutBinding: ItemSliderImageBinding

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
            itemLayoutBinding.image = item
        }
    }
}