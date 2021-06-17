package com.note.notes.ui.addeditnotes

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.note.notes.EventObserver
import com.note.notes.R
import com.note.notes.Util.getViewModelFactory
import com.note.notes.Util.setupSnackbar
import com.note.notes.databinding.FragmentAddEditNotesBinding

class AddEditNotesFragment : Fragment() {
    private val args: AddEditNotesFragmentArgs by navArgs()

    private val addEditViewModel: AddEditNotesViewModel by viewModels {
        getViewModelFactory()
    }

    private lateinit var binding: FragmentAddEditNotesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_edit_notes, container, false)
        binding.viewmodel = addEditViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addEditViewModel.open(args.noteId)
        setupSnackbar()
        setupNavigation()
    }

    private fun setupNavigation() {
        addEditViewModel.noteSavedEvent.observe(viewLifecycleOwner, EventObserver{
            val action = AddEditNotesFragmentDirections.actionAddEditNotesFragmentToNotesFragment(it)
            findNavController().navigate(action)
        })
    }

    private fun setupSnackbar() {
        view?.setupSnackbar(this, addEditViewModel.snackbarMessage)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_edit_frag_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save){
            saveNote()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveNote(){
        addEditViewModel.save()
    }
}