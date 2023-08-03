package com.jerry.clean_arch_mvvm.assetpage.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jerry.clean_arch_mvvm.assetpage.databinding.AssetItemBinding
import com.jerry.clean_arch_mvvm.assetpage.domain.entities.ui.AssetUiItem


class AssetsAdapter constructor(
    private val onItemClick: (String) -> Unit = {},
) : RecyclerView.Adapter<AssetItemViewHolder>() {

    private var assetItems: List<AssetUiItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AssetItemViewHolder(
                binding = AssetItemBinding.inflate(
                        inflater,
                        parent,
                        false
                ),
                onLinkClicked = onItemClick
        )
    }

    override fun onBindViewHolder(holder: AssetItemViewHolder, position: Int) {
        holder.bind(assetItems[position])
    }

    override fun getItemCount() = assetItems.size

    fun setList(items: List<AssetUiItem>?) {
        if (items != null) {
            this.assetItems = items
            notifyDataSetChanged()
        }
    }

}

class AssetItemViewHolder(
    private val binding: AssetItemBinding,
    private val onLinkClicked: (String) -> Unit = {}
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(asset: AssetUiItem) {
        binding.textViewAssetCode.text = asset.symbol
        binding.textViewAssetName.text = asset.name
        binding.textViewAssetPrice.text = asset.price

        binding.root.setOnClickListener {
            asset.id?.let {
                onLinkClicked(it)
            }
        }
    }
}
