package ir.millennium.sampleProject.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ir.millennium.sampleProject.data.dataSource.local.sharedPreferences.SharedPreferencesManager
import ir.millennium.sampleProject.data.model.local.aboutMe.UserProfileSocialNetworkEntity
import ir.millennium.sampleProject.databinding.RowSocialNetworkBinding

class UserProfileSocialNetworkAdapter(
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val itemClickCallBack: (UserProfileSocialNetworkEntity) -> Unit
) :
    ListAdapter<UserProfileSocialNetworkEntity, UserProfileSocialNetworkAdapter.UserProfileSocialNetworkViewHolder>(
        object : DiffUtil.ItemCallback<UserProfileSocialNetworkEntity>() {
            override fun areItemsTheSame(
                oldItem: UserProfileSocialNetworkEntity,
                newItem: UserProfileSocialNetworkEntity
            ): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(
                oldItem: UserProfileSocialNetworkEntity,
                newItem: UserProfileSocialNetworkEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserProfileSocialNetworkViewHolder {
        val view = RowSocialNetworkBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserProfileSocialNetworkViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: UserProfileSocialNetworkViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class UserProfileSocialNetworkViewHolder(
        private val binding: RowSocialNetworkBinding,
        val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: UserProfileSocialNetworkEntity) {
            binding.lblTitle.text = context.resources.getString(item.title)

            binding.cvMain.setOnClickListener {
                itemClickCallBack.invoke(item)
            }

            binding.ibArrow.setOnClickListener {
                itemClickCallBack.invoke(item)
            }
        }
    }
}
