package com.note.notes.ui.notes

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.note.notes.EventObserver
import com.note.notes.R
import com.note.notes.Util.getViewModelFactory
import com.note.notes.Util.setupSnackbar
import com.note.notes.databinding.FragmentNotesBinding

class NotesFragment : Fragment() {
    private lateinit var binding: FragmentNotesBinding

    private val args: NotesFragmentArgs by navArgs()

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
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesViewModel.showResultMessage(args.result)
        setupAdapter()
        setupFab()
        setupNavigation()
        setupSnackbar()
    }

    private fun setupSnackbar() {
        view?.setupSnackbar(this, notesViewModel.snackbarMessage)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.notes_frag_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.delete_all -> {
                showConfirmationDialog()
                return true
            }
            R.id.filter_notes -> {
                displayPopUpMenu()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun displayPopUpMenu() {
        val view = requireActivity().findViewById<View>(R.id.filter_notes)
        PopupMenu(requireContext(), view).run {
            menuInflater.inflate(R.menu.filter_notes_menu, menu)
            setOnMenuItemClickListener { item ->
                notesViewModel.setFiltering(
                    when(item.itemId){
                        R.id.all_notes -> NotesFilterType.ALL_NOTES
                        R.id.bookmarked -> NotesFilterType.BOOKMARKS
                        else -> NotesFilterType.ALL_NOTES
                    }
                )
                true
            }
            show()
        }
    }

    private fun showConfirmationDialog() {
        val dialog = AlertDialog.Builder(requireContext()).apply {
            setMessage(getString(R.string.dialog_message))
            setPositiveButton(getString(R.string.dialog_button_okay)) { _, _ ->
                deleteAll()
            }
            setNegativeButton(getString(R.string.dialog_button_cancel)) { dialogInterface, _ ->
                dialogInterface?.dismiss()
            }
        }
        dialog.show()
    }

    private fun deleteAll() {
        notesViewModel.deleteAllNotes()
    }

    private fun setupAdapter(){
        val notesAdapter = NotesAdapter(notesViewModel)
        binding.notesList.adapter = notesAdapter
        binding.notesList.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
    }
}