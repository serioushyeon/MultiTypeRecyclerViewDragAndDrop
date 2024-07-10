package com.serioushyeon.multityperecyclerviewdraganddrop

import android.content.ClipData
import android.content.ClipDescription
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.serioushyeon.multityperecyclerviewdraganddrop.databinding.ItemSentenceItemBinding

class RecyclerViewAdapter(private var items: MutableList<MultiTypeItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnTouchListener , View.OnLongClickListener {


    companion object {
        private const val TYPE_TEXT = 0
        private const val TYPE_BREATH_BUTTON = 1
        private const val TYPE_PPT_BUTTON = 2
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_TEXT -> TextViewHolder(layoutInflater.inflate(R.layout.item_sentence_item, parent, false))
            TYPE_BREATH_BUTTON -> BreathButtonViewHolder(layoutInflater.inflate(R.layout.item_breath_button, parent, false))
            TYPE_PPT_BUTTON -> PPTButtonViewHolder(layoutInflater.inflate(R.layout.item_ppt_button, parent, false))
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TextViewHolder -> holder.bind((items[position] as MultiTypeItem.TextItem).text, this, position)
            is BreathButtonViewHolder -> holder.bind(this, position)
            is PPTButtonViewHolder -> holder.bind(this, position)
        }
    }
    class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(text: String, adapter: RecyclerViewAdapter, position: Int) {
            itemView.findViewById<TextView>(R.id.tv_rv_sentence).text = text
            itemView.findViewById<ConstraintLayout>(R.id.cl_sentence).tag = position
            itemView.findViewById<ConstraintLayout>(R.id.cl_sentence).setOnDragListener(DragListener())
        }
    }

    class BreathButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(adapter: RecyclerViewAdapter, position: Int) {
            itemView.findViewById<ConstraintLayout>(R.id.cl_breath_btn).tag = position
            itemView.findViewById<ConstraintLayout>(R.id.btn_rv_breath).tag = position
            itemView.findViewById<ConstraintLayout>(R.id.btn_rv_breath).setOnLongClickListener(adapter)
            itemView.findViewById<ConstraintLayout>(R.id.cl_breath_btn).setOnDragListener(DragListener())
            itemView.findViewById<ImageView>(R.id.iv_breath_delete).setOnClickListener{
                adapter.removeItemAt(position)
            }
        }
    }

    class PPTButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(adapter: RecyclerViewAdapter, position: Int) {
            itemView.findViewById<ConstraintLayout>(R.id.cl_ppt_btn).tag = position
            itemView.findViewById<ConstraintLayout>(R.id.btn_rv_ppt).tag = position
            itemView.findViewById<ConstraintLayout>(R.id.btn_rv_ppt).setOnLongClickListener(adapter)
            itemView.findViewById<ConstraintLayout>(R.id.cl_ppt_btn).setOnDragListener(DragListener())
            itemView.findViewById<ImageView>(R.id.iv_ppt_delete).setOnClickListener{
                adapter.removeItemAt(position)
            }
        }
    }
    override fun getItemCount(): Int = items.size

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val data = ClipData.newPlainText("", "")
            val shadowBuilder = View.DragShadowBuilder(v)
            v.startDragAndDrop(data, shadowBuilder, v, 0)
            return true
        }
        return false
    }
    override fun onLongClick(v: View?): Boolean {
        Log.d("", "Long")
        val data = ClipData.newPlainText("", "")
        val shadowBuilder = View.DragShadowBuilder(v)
        v!!.startDragAndDrop(data, shadowBuilder, v.parent, 0)
        return true
    }

    fun getList(): MutableList<MultiTypeItem> = items

    fun updateList(list: MutableList<MultiTypeItem>) {
        this.items = list
    }

    // 아이템 제거 메소드
    fun removeItemAt(position: Int) {
        if (position >= 0 && position < items.size) {
            items.removeAt(position)
            notifyItemRemoved(position)
            notifyDataSetChanged()
        }
    }
    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is MultiTypeItem.TextItem -> TYPE_TEXT
            is MultiTypeItem.BreathButtonItem -> TYPE_BREATH_BUTTON
            is MultiTypeItem.PPTButtonItem -> TYPE_PPT_BUTTON
        }
    }
}