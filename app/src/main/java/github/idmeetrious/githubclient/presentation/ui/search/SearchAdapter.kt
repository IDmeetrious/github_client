package github.idmeetrious.githubclient.presentation.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import github.idmeetrious.githubclient.R
import github.idmeetrious.githubclient.domain.entities.GitRepo

class SearchAdapter(): RecyclerView.Adapter<RepoViewHolder>() {
    private var list: List<GitRepo> = emptyList()

    fun updateList(list: List<GitRepo>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_search_repo_item, parent, false)
        return RepoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val item = list[position]
        holder.nameTv.text = item.name
        Glide.with(holder.itemView)
            .load(item.owner.logo)
            .placeholder(R.drawable.ic_broken_image_24)
            .into(holder.logoIv)

        holder.downloadBtn.setOnClickListener {

        }

        holder.openWebBtn.setOnClickListener {

        }
    }

    override fun getItemCount(): Int = list.size
}