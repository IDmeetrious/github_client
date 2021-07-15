package github.idmeetrious.githubclient.presentation.ui.search

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import github.idmeetrious.githubclient.R
import github.idmeetrious.githubclient.domain.entities.GitRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

private const val TAG = "SearchAdapter"
class SearchAdapter(): RecyclerView.Adapter<RepoViewHolder>() {
    private var list: List<GitRepo> = emptyList()
    private var _saveRepo: MutableStateFlow<GitRepo?> = MutableStateFlow(null)
    val saveRepo get() = _saveRepo
    private var _openRepo: MutableStateFlow<GitRepo?> = MutableStateFlow(null)
    val openRepo get() = _openRepo

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
            Log.i(TAG, "--> onBindViewHolder: Clicked[${item.name}]")
            CoroutineScope(Dispatchers.IO).launch {
                _saveRepo.value = item
            }
        }

        holder.openWebBtn.setOnClickListener {
            Log.i(TAG, "--> onBindViewHolder: Clicked[${item.name}]")
            CoroutineScope(Dispatchers.IO).launch {
                _openRepo.value = item
            }
        }
    }

    override fun getItemCount(): Int = list.size
}