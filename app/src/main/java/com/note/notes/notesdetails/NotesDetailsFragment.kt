package com.note.notes.notesdetails

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.note.notes.EventObserver
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
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesDetailsViewModel.getNote(args.noteId)
        setupSnackbar()
        setupNavigation()
    }

    private fun setupNavigation() {
        notesDetailsViewModel.editNoteEvent.observe(viewLifecycleOwner, EventObserver{ noteId ->
            val action = NotesDetailsFragmentDirections.actionNotesDetailsFragmentToAddEditNotesFragment(noteId)
            findNavController().navigate(action)
        })
    }

    private fun setupSnackbar() {
        view?.setupSnackbar(this, notesDetailsViewModel.snackbarMessage)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_details_frag_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.edit){
            editNote()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun editNote() {
        notesDetailsViewModel.editNote(args.noteId)
    }

}