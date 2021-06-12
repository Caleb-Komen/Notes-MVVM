package com.note.notes.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton
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
        setupFab()
    }

    private fun setupFab() {
        view?.findViewById<FloatingActionButton>(R.id.fab_new_note)?.apply {
            setOnClickListener {
                Toast.makeText(requireContext(), "New note", Toast.LENGTH_SHORT).show()
            }
        }
    }
}