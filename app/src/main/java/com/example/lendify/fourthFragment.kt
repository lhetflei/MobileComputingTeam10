package com.example.lendify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.lendify.adapter.UserAdapter
import com.example.lendify.databinding.FragmentFourthBinding
import com.example.lendify.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class fourthFragment : Fragment() {

    private var _binding: FragmentFourthBinding? = null
    private val binding get() = _binding!!
    private var userList = ArrayList<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFourthBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUserList()
    }

    fun getUserList() {
        val firebase: FirebaseUser = FirebaseAuth.getInstance().currentUser!!

        //Refresh
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance("https://lendify-6cd5f-default-rtdb.europe-west1.firebasedatabase.app/").getReference("user")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (dataSnapShot: DataSnapshot in snapshot.children) {
                    val user = dataSnapShot.getValue(User::class.java)

                    //if (user!!.userID != firebase.uid) {

                    if (user!!.userID != firebase.uid)  {
                        userList.add(user)
                    }
                }
                val user_a = UserAdapter(context!!, userList)
                binding.userRecyclerView.adapter = user_a
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,"Error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

