package ba.etf.rma23.projekat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ba.etf.rma23.projekat.data.repositories.GameReview
import ba.etf.rma23.projekat.data.repositories.GameReviewsRepository.sendReview
import com.example.spirala.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.*

class SendReviewFragment: Fragment() {
    private lateinit var sendReviewButton: Button
    private lateinit var spinner : Spinner
    private lateinit var comment : EditText
    private lateinit var rating : RatingBar
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val bottomNav: BottomNavigationView = requireActivity().findViewById(R.id.bottom_nav)
        bottomNav.menu.getItem(0).isEnabled=false
        val view = inflater.inflate(R.layout.send_review, container, false)
        spinner=view.findViewById(R.id.spinnerReviewType)
        comment=view.findViewById(R.id.editTextComment)
        rating=view.findViewById(R.id.ratingBar)
        sendReviewButton = view.findViewById(R.id.buttonSendReview)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, arrayOf("Comment", "Rating"))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(0)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if (position == 0) { // Comment
                    comment.visibility = View.VISIBLE
                    rating.visibility = View.GONE
                } else { // Rating
                    comment.visibility = View.GONE
                    rating.visibility = View.VISIBLE
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                //something is always selected
            }
        }
        sendReviewButton.setOnClickListener {
            val context = this.context
            if(spinner.selectedItemPosition==0){
                CoroutineScope(Job() + Dispatchers.Main).launch{
                    sendReview(context!!,GameReview(null,comment.text.toString(),arguments?.getInt("id")!!,false,"",""))
                }
            }
            else if(spinner.selectedItemPosition==1){
                CoroutineScope(Job() + Dispatchers.Main).launch{
                    sendReview(context!!,GameReview(rating.rating.toInt(),null,arguments?.getInt("id")!!,false,"",""))
               }
            }
            findNavController().navigateUp()
            bottomNav.menu.getItem(0).isEnabled=true
        }
        return view
    }
}