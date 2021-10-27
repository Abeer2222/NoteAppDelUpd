package com.example.noteappdelupd

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var db: DatabaseHandler
    private lateinit var edit: EditText
    private lateinit var btnsave: Button
    private lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = DatabaseHandler(this)
        edit = findViewById(R.id.messageEditText)
        btnsave = findViewById(R.id.saveButton)
        btnsave.setOnClickListener {
            AddNote()
        }
        rv = findViewById(R.id.rv)
        updateRecycler()
    }

    private fun updateRecycler() {
        rv.adapter = RVAdapter(this, getItemsList())
        rv.layoutManager = LinearLayoutManager(this)
    }

    private fun getItemsList(): ArrayList<NoteModel> {
        return db.viewNotes()
    }

    private fun AddNote() {
        db.addNote(NoteModel(0, edit.text.toString()))
        edit.text.clear()
        Toast.makeText(this, "Note Added!", Toast.LENGTH_LONG).show()
        updateRecycler()
    }

    private fun editNote(noteID: Int, noteText: String) {
        db.updateNote(NoteModel(noteID, noteText))
        Toast.makeText(this, "Note updated!", Toast.LENGTH_LONG).show()

        updateRecycler()
    }

    fun deleteNote(noteID: Int) {
        db.deleteNote(NoteModel(noteID, ""))
        updateRecycler()
        Toast.makeText(this, "Note deleted!", Toast.LENGTH_LONG).show()

    }

    fun raiseDialog(id: Int) {
        val dialogBuilder = AlertDialog.Builder(this)
        val updatedNote = EditText(this)
        updatedNote.hint = " edit your note"
        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("update", DialogInterface.OnClickListener { _, _ ->
                editNote(id, updatedNote.text.toString())
            })
            .setNegativeButton("cancel", DialogInterface.OnClickListener { dialog, _ ->
                dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Update Note")
        alert.setView(updatedNote)
        alert.show()
    }
}