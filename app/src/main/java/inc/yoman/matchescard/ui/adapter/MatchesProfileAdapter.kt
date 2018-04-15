package inc.yoman.matchescard.ui.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import inc.yoman.matchescard.R
import inc.yoman.matchescard.helper.MatchesProfileResponseModel
import inc.yoman.matchescard.utils.AppUtils
import kotlinx.android.synthetic.main.item_matches_profile.view.*



class MatchesProfileAdapter(activity: Activity, data: MutableList<MatchesProfileResponseModel>) : RecyclerView.Adapter<MatchesProfileAdapter.MatchesProfileViewHolder>() {

    private var data: MutableList<MatchesProfileResponseModel> = data
    private var activity: Activity = activity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesProfileViewHolder {
        return MatchesProfileViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_matches_profile, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MatchesProfileViewHolder, position: Int) = holder.bind(activity, position)

    inner class MatchesProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(activity: Activity, position: Int) = with(itemView) {

            var userDetails: String  = (data[position].name.title).capitalize() + ". " + (data[position].name.first).capitalize() + " " + (data[position].name.last).capitalize() + ", "+ AppUtils.calculateUserAge(data[position].dob)

            itemView.textView_user_details.text = userDetails
            itemView.textView_address.text = (data[position].location.city).capitalize() + ", " + (data[position].location.state).capitalize()

           AppUtils.loadImageInImageView(data[position].picture.large,imageView_profile, data[position].gender)

                imageView_call.setOnClickListener { AppUtils.callIntent(activity, data[position].phone) }
                imageView_email.setOnClickListener {  AppUtils.sendEmail(activity, data[position].email) }
                button_accept.setOnClickListener {
                    setAnimation(activity, itemView)
                    remove() }
                button_reject.setOnClickListener {
                    setAnimation(activity, itemView)
                    remove() }
        }

        private fun remove() {
            layoutPosition.also { currentPosition ->
                data.removeAt(currentPosition)
                notifyItemRemoved(currentPosition)
            }
        }
    }

    private fun setAnimation(context:Activity, viewToAnimate: View) {
            val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_out_right)
            viewToAnimate.startAnimation(animation)
    }
}