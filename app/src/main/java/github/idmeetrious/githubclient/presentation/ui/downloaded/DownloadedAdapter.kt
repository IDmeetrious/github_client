package github.idmeetrious.githubclient.presentation.ui.downloaded

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import github.idmeetrious.githubclient.R
import github.idmeetrious.githubclient.domain.entities.GitRepo

class DownloadedAdapter : RecyclerView.Adapter<RepoViewHolder>() {
    private var list: List<GitRepo> = emptyList()

    fun updateList(list: List<GitRepo>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_downloaded_repo_item, parent, false)
        return RepoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val item = list[position]
        holder.loginTv.text = item.owner.login
        holder.repoTv.text = item.name
    }

    override fun getItemCount(): Int = list.size
}