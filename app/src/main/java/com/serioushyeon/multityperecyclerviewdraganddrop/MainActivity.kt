package com.serioushyeon.multityperecyclerviewdraganddrop

import android.content.ClipData
import android.content.ClipDescription
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.serioushyeon.multityperecyclerviewdraganddrop.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    private val items = mutableListOf<MultiTypeItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initToolBar()
        setupDragListeners()
        initMultiTypeRecyclerView()
    }
    private fun initMultiTypeRecyclerView() {
        binding.rvScript.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        items.add(MultiTypeItem.PPTButtonItem)
        items.add(MultiTypeItem.TextItem("안녕하세요 여러분, 오늘 제가 여러분께 소개할 주제는 'iOS 프로젝트 주제 추천'입니다."))
        items.add(MultiTypeItem.TextItem("이 주제를 통해 Swift 언어를 사용하여 단기간에 개발할 수 있는 앱 개발 프로젝트에 대해 알아보겠습니다."))
        items.add(MultiTypeItem.PPTButtonItem)
        items.add(MultiTypeItem.TextItem("우선, iOS 앱 개발에 있어서 Swift는 매우 중요한 언어입니다."))
        items.add(MultiTypeItem.TextItem("Swift는 Apple이 iOS, macOS, watchOS, tvOS를 위해 만든 프로그래밍 언어로, 간결하면서도 강력한 기능을 제공합니다."))
        items.add(MultiTypeItem.TextItem("Swift를 사용함으로써 개발자는 보다 빠르고 안정적인 앱을 개발할 수 있습니다."))
        items.add(MultiTypeItem.TextItem("다음으로, 단기간 개발을 위한 프로젝트 아이디어를 몇 가지 소개하겠습니다."))
        items.add(MultiTypeItem.TextItem("첫 번째 아이디어는 '일기장 앱'입니다. 사용자가 매일의 일기를 쓰고 사진을 첨부할 수 있는 간단한 앱입니다."))
        items.add(MultiTypeItem.TextItem("이 프로젝트는 데이터 저장과 UI 디자인의 기본을 다루기 때문에 초보 개발자에게 적합합니다."))
        items.add(MultiTypeItem.BreathButtonItem)
        val multiTypeRecyclerViewAdapter = RecyclerViewAdapter(items)
        binding.rvScript.adapter = multiTypeRecyclerViewAdapter
    }
    private fun setupDragListeners() {
        val buttons = listOf(binding.btnFlowControllerBreath, binding.btnFlowControllerPpt)
        buttons.forEach { button ->
            button.setOnLongClickListener { view ->
                val item = ClipData.Item(view.tag as? CharSequence)
                val dragData = ClipData(view.tag.toString(), arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN), item)
                val shadowBuilder = View.DragShadowBuilder(view)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    view.startDragAndDrop(dragData, shadowBuilder, view, 0)
                } else {
                    view.startDrag(dragData, shadowBuilder, view, 0)
                }
                true
            }
        }
    }
    private fun initToolBar() {
        binding.toolbar.run {
            buttonBack.visibility = View.VISIBLE
            buttonClose.visibility = View.INVISIBLE
            textViewTitle.visibility = View.VISIBLE
            textViewTitle.text = "발표 연습"
            textViewPage.visibility = View.VISIBLE
            textViewPage.text = "3/5"
        }
    }
}