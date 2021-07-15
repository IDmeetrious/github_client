package github.idmeetrious.githubclient.presentation.ui.search

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import github.idmeetrious.githubclient.R

class RepoViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    var nameTv: TextView
    val logoIv: ImageView
    val openWebBtn: ImageButton
    val downloadBtn: ImageButton

    init {
        view.let {
            nameTv = it.findViewById(R.id.search_repo_item_login_tv)
            logoIv = it.findViewById(R.id.repo_item_logo_iv)
            openWebBtn = it.findViewById(R.id.repo_item_open_ib)
            downloadBtn = it.findViewById(R.id.repo_item_download_ib)
        }
    }
}