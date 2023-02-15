package app.te.alo_chef.appTutorial

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.te.alo_chef.appTutorial.TutorialAdapter.ImagesSliderViewHolder
import coil.ImageLoader
import coil.request.ImageRequest
import app.te.alo_chef.domain.intro.entity.AppTutorialModel
import app.te.alo_chef.R
import app.te.alo_chef.databinding.ItemTutorialBinding

internal class TutorialAdapter(
    private var titleColor: Int,
    private var contentColor: Int
) : ListAdapter<AppTutorialModel, ImagesSliderViewHolder>(DIFF_CALLBACK) {
    private lateinit var context: Context

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AppTutorialModel>() {
            override
            fun areItemsTheSame(oldItem: AppTutorialModel, newItem: AppTutorialModel): Boolean =
                oldItem.hashCode() == newItem.hashCode()

            override
            fun areContentsTheSame(oldItem: AppTutorialModel, newItem: AppTutorialModel): Boolean =
                oldItem == newItem
        }
    }

    override
    fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImagesSliderViewHolder {
        val root =
            LayoutInflater.from(parent.context).inflate(R.layout.item_tutorial, parent, false)
        context = parent.context
        return ImagesSliderViewHolder(ItemTutorialBinding.bind(root))
    }

    override
    fun onBindViewHolder(holder: ImagesSliderViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ImagesSliderViewHolder(private val itemBinding: ItemTutorialBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        private lateinit var currentItem: AppTutorialModel

        init {
            itemBinding.tvTitle.setTextColor(titleColor)
            itemBinding.tvContent.setTextColor(contentColor)
        }

        fun bind(item: AppTutorialModel) {
            currentItem = item

            itemBinding.tvTitle.text = item.title
            itemBinding.tvContent.text = item.body
            showImage(item.image)
        }

        private fun showImage(imageUrl: String) {
            if (imageUrl.isNotEmpty()) {
                val request = ImageRequest.Builder(context)
                    .data(imageUrl)
                    .crossfade(true)
                    .crossfade(400)
                    .placeholder(R.color.colorGray)
                    .error(R.drawable.bg_no_image)
                    .target(
                        onStart = { placeholder ->
                            itemBinding.ivImage.setImageDrawable(placeholder)
                        },
                        onSuccess = { result ->
                            itemBinding.ivImage.setImageDrawable(result)
                        }
                    )
                    .listener(
                        onStart = { request ->
                            itemBinding.ivImage.setImageDrawable(request.placeholder)
                        },
                        onError = { request, _ ->
                            itemBinding.ivImage.setImageDrawable(request.error)
                        },
                        onSuccess = { _, result ->
                            itemBinding.ivImage.setImageDrawable(result.drawable)
                        }
                    )
                    .build()

                ImageLoader(context).enqueue(request)
            } else {
                itemBinding.ivImage.setImageResource(R.drawable.bg_no_image)
            }
        }

    }

}