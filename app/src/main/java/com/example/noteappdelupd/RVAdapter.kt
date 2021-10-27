package com.example.noteappdelupd

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*

class RVAdapter(private val activity: MainActivity,
    private val items: ArrayList<NoteModel>)
    : RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {
    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]

        holder.itemView.apply {
            noteTextView.text = item.noteText
            if(position%2==0){holderLinearLayout.setBackgroundColor(Color.GRAY)}
            ibEditNote.setOnClickListener {
                activity.raiseDialog(item.id)
            }
            ibDeleteNote.setOnClickListener {
                activity.deleteNote(item.id)
            }
        }    }

    override fun getItemCount()= items.size


}