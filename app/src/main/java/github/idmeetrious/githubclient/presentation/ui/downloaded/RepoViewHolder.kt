package github.idmeetrious.githubclient.presentation.ui.downloaded

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import github.idmeetrious.githubclient.R

class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var loginTv: TextView
    val repoTv: TextView

    init {
        view.let {
            loginTv = it.findViewById(R.id.downloaded_repo_item_login_tv)
            repoTv = it.findViewById(R.id.downloaded_repo_item_repo_tv)
        }
    }
}