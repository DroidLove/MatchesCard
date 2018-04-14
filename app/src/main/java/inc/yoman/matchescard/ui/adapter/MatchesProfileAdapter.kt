package inc.yoman.matchescard.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import inc.yoman.matchescard.R
import inc.yoman.matchescard.model.MatchesProfileModel

class MatchesProfileAdapter(data: List<MatchesProfileModel>) : RecyclerView.Adapter<MatchesProfileAdapter.MatchesProfileViewHolder>() {

    private var data: List<MatchesProfileModel> = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesProfileViewHolder {
        return MatchesProfileViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_matches_profile, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MatchesProfileViewHolder, position: Int) = holder.bind(data[position])

    fun swapData(data: List<MatchesProfileModel>) {
        this.data = data
        notifyDataSetChanged()
    }

    class MatchesProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: MatchesProfileModel) = with(itemView) {
            // TODO: Bind the data with View
            setOnClickListener {
                // TODO: Handle on click
            }
        }
    }
}