package com.note.notes.notes

import androidx.lifecycle.*
import com.note.notes.R
import com.note.notes.data.Note
import com.note.notes.data.NotesRepository
import com.note.notes.data.Result

class NotesViewModel(
    private val notesRepository: NotesRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _loadNotes = MutableLiveData<NotesFilterType>()

    private val _notes: LiveData<List<Note>> = _loadNotes.switchMap { filterType ->
        if (filterType !is NotesFilterType){
            // Display toast message
        }
        notesRepository.observeNotes().distinctUntilChanged().switchMap {
            filterNotes(it)
        }
    }

    private fun filterNotes(result: Result<List<Note>>) = liveData{
        val data = if (result is Result.Success){
            filterItems(result.data, getFilterType())
        } else{
            emptyList()
        }
        emit(data)
    }

    private fun filterItems(notes: List<Note>, filterType: NotesFilterType): List<Note>{
        val notesToDisplay = ArrayList<Note>()
        for (note in notes){
            when(filterType){
                NotesFilterType.ALL_NOTES -> {
                    notesToDisplay.add(note)
                }
                NotesFilterType.BOOKMARKS -> if (note.bookmarked){
                    notesToDisplay.add(note)
                }
            }
        }
        return notesToDisplay
    }
    val notes: LiveData<List<Note>> = _notes

    private val _filterLabelString = MutableLiveData<Int>()
    val filterLabelString: LiveData<Int> get() = _filterLabelString

    private val _noNotesLabelString = MutableLiveData<Int>()
    val noNotesLabelString: LiveData<Int> get() = _noNotesLabelString

    private val _noNotesIcon = MutableLiveData<Int>()
    val noNotesIcon: LiveData<Int> = _noNotesIcon

    init {
        setFiltering(getFilterType())
    }

    fun setFiltering(filterType: NotesFilterType){
        savedStateHandle[NOTE_FILTER_TYPE_KEY] = filterType
        when(filterType){
            NotesFilterType.ALL_NOTES -> {
                setFilter(R.string.label_all, R.string.no_notes, R.drawable.ic_baseline_note_24)
            }
            NotesFilterType.BOOKMARKS -> {
                setFilter(R.string.label_bookmarks, R.string.no_bookmarks, R.drawable.ic_baseline_bookmarks_24)
            }
        }
        _loadNotes.value = filterType
    }

    private fun setFilter(filterLabelString: Int, noNotesLabelString: Int, noNotesIcon: Int) {
        _filterLabelString.value = filterLabelString
        _noNotesLabelString.value = noNotesLabelString
        _noNotesIcon.value = noNotesIcon
    }

    private fun getFilterType() = savedStateHandle[NOTE_FILTER_TYPE_KEY] ?: NotesFilterType.ALL_NOTES

    companion object{
        private const val NOTE_FILTER_TYPE_KEY = "NOTE_FILTER_TYPE_KEY"
    }

}