package inc.yoman.matchescard.ui.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import inc.yoman.matchescard.R
import inc.yoman.matchescard.model.MatchesProfileModel
import inc.yoman.matchescard.utils.AppUtils
import kotlinx.android.synthetic.main.item_matches_profile.view.*

class MatchesProfileAdapter(activity: Activity, data: List<MatchesProfileModel>) : RecyclerView.Adapter<MatchesProfileAdapter.MatchesProfileViewHolder>() {

    private var data: List<MatchesProfileModel> = data
    private var activity: Activity = activity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesProfileViewHolder {
        return MatchesProfileViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_matches_profile, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MatchesProfileViewHolder, position: Int) = holder.bind(activity, data[position])

//    fun swapData(data: List<MatchesProfileModel>) {
//        this.data = data
//        notifyDataSetChanged()
//    }

    class MatchesProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(activity: Activity, item: MatchesProfileModel) = with(itemView) {

            itemView.textView_username.text = (item.name.title).capitalize() + " " + (item.name.first).capitalize() + " " + (item.name.last).capitalize()
            itemView.textView_address.text = (item.location.city).capitalize() + ", " + (item.location.state).capitalize()

           AppUtils.loadImageInImageView(item.picture.large,imageView_profile, item.gender)

            setOnClickListener {

                it.imageView_call.setOnClickListener { AppUtils.callIntent(activity, item.phone) }
                it.imageView_email.setOnClickListener { AppUtils.sendEmail(activity, item.email) }
            }
        }
    }
}