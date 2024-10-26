package com.example.dymagram.views.pager_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dymagram.R
import com.example.dymagram.data.model.messages.Message
import com.example.dymagram.repositories.DirectMessagesRepository
import com.example.dymagram.repositories.GlobalDataRepository
import com.example.dymagram.viewmodel.DirectMessagesViewModel
import com.example.dymagram.viewmodel.HomeFeedViewModel
import com.example.dymagram.viewmodel.factories.DirectMessagesViewModelFactory
import com.example.dymagram.viewmodel.factories.HomeFeedViewModelFactory
import com.example.dymagram.views.recycler_view_adapters.dm_adapters.DirectMessagesRvAdapter

class DirectMessagesFragment : Fragment() {

    lateinit var messagesRv: RecyclerView

    private val directMessagesiewModel: DirectMessagesViewModel by viewModels {
        DirectMessagesViewModelFactory( DirectMessagesRepository(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_direct_messages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchData(view)
    }

    private fun setUpDmRv(messages: List<Message>, fragmentView: View)  {
        this.messagesRv = fragmentView.findViewById(R.id.direct_messages_rv)
        this.messagesRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        this.messagesRv.adapter = DirectMessagesRvAdapter(messages)
    }

    private fun fetchData(fragmentView: View) {
        this.directMessagesiewModel.messagesData.observe(viewLifecycleOwner) { messages ->
            Toast.makeText(context, "On a reçu de la donnée", Toast.LENGTH_LONG).show()

            setUpDmRv(messages, fragmentView)
        }
        this.directMessagesiewModel.fetchMessagesFromRepo()
    }

}