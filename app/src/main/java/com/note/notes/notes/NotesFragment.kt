package com.note.notes.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.note.notes.EventObserver
import com.note.notes.R
import com.note.notes.Util.getViewModelFactory
import com.note.notes.databinding.FragmentNotesBinding

class NotesFragment : Fragment() {
    private lateinit var binding: FragmentNotesBinding

    private val notesViewModel by viewModels<NotesViewModel> {
        getViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesBinding.inflate(inflater, container, false).apply {
            viewmodel = notesViewModel
        }
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupFab()
        setupNavigation()
    }

    private fun setupNavigation(){
        notesViewModel.openNoteEvent.observe(viewLifecycleOwner, EventObserver{
            openNoteDetails(it)
        })
    }

    private fun openNoteDetails(noteId: Long) {
        val action = NotesFragmentDirections.actionNotesFragmentToNotesDetailsFragment(noteId)
        findNavController().navigate(action)
    }

    private fun setupFab() {
        view?.findViewById<FloatingActionButton>(R.id.fab_new_note)?.apply {
            setOnClickListener {
                val action = NotesFragmentDirections
                    .actionNotesFragmentToAddEditNotesFragment(-1L)
                it.findNavController().navigate(action)
            }
        }
    }

    private fun setupAdapter(){
        val notesAdapter = NotesAdapter(notesViewModel)
        binding.notesList.adapter = notesAdapter
    }
}