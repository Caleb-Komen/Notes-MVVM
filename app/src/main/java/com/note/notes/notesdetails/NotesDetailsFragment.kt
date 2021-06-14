package com.note.notes.notesdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.note.notes.R
import com.note.notes.Util.getViewModelFactory
import com.note.notes.Util.setupSnackbar
import com.note.notes.databinding.FragmentNotesDetailsBinding

class NotesDetailsFragment : Fragment() {
    private val args: NotesDetailsFragmentArgs by navArgs()

    private val notesDetailsViewModel: NotesDetailsViewModel by viewModels {
        getViewModelFactory()
    }

    private lateinit var binding: FragmentNotesDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_notes_details, container, false)
        binding = FragmentNotesDetailsBinding.bind(root).apply {
            viewmodel = notesDetailsViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesDetailsViewModel.getNote(args.noteId)
        setupSnackbar()
    }

    private fun setupSnackbar() {
        view?.setupSnackbar(this, notesDetailsViewModel.snackbarMessage)
    }

}