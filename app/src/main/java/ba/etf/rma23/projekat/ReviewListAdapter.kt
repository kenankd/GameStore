package ba.etf.rma23.projekat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.spirala.R

class ReviewListAdapter( private var reviews:List<UserImpression>):RecyclerView.Adapter<ReviewListAdapter.ReviewHolder>(){
    inner class ReviewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        var username : TextView = itemView.findViewById(R.id.username_textview)
        var comment : TextView = itemView.findViewById(R.id.review_textview)
        var rating : RatingBar = itemView.findViewById(R.id.rating_bar)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.review, parent, false)
        return ReviewHolder(view)
    }
    override fun getItemCount(): Int =reviews.size

    override fun onBindViewHolder(holder: ReviewHolder, position: Int) {
        if(reviews[position] is UserRating){
            var c = reviews[position] as UserRating
            holder.username.text=c.username
            holder.rating.rating= c.rating.toFloat()
            holder.comment.isVisible=false
        }
        if(reviews[position] is UserReview){
            var c = reviews[position] as UserReview
            holder.username.text=c.username
            holder.comment.text=c.review
            holder.rating.isVisible=false
        }
    }
     fun setReviews(reviews : List<UserImpression>){
        this.reviews=reviews
        notifyDataSetChanged()
    }
}