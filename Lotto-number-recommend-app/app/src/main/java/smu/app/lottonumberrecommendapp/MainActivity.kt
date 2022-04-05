package smu.app.lottonumberrecommendapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    private val  clearButton: Button by lazy {
        findViewById<Button>(R.id.clearButton)
    }
    private val  addButton: Button by lazy {
        findViewById<Button>(R.id.addButton)
    }   
    private val  runButton: Button by lazy {
        findViewById<Button>(R.id.runButton)
    }

    private val numberPicker: NumberPicker by lazy{
        findViewById(R.id.numberPicker)
    }

    private val numberTextViewList:List<TextView> by lazy{
        listOf<TextView>(
            findViewById<TextView>(R.id.textView1),
            findViewById<TextView>(R.id.textView2),
            findViewById<TextView>(R.id.textView3),
            findViewById<TextView>(R.id.textView4),
            findViewById<TextView>(R.id.textView5),
            findViewById<TextView>(R.id.textView6)
        )
    }
    private var didRun = false
    private var pickNumberSet = hashSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker.minValue =1
        numberPicker.maxValue =45

        initRunButtom()
        initAddButtom()
        initClearButton()

    }

    private fun initRunButtom(){
        runButton.setOnClickListener {
            if(didRun){
                Toast.makeText(this,"초기화 후에 시도해주세요.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val list = getRandomNumber()

            numberTextViewList.forEachIndexed { index, textView ->
                textView.isVisible = true
                textView.text = list[index].toString()

                when(list[index]){
                    in 1.. 10 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_blue)
                    in 11.. 20 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_gray)
                    in 21.. 30 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_green)
                    in 31.. 40 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_red)
                    else-> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_yellow)
                }
            }

            didRun = true
        }
    }

    private fun initAddButtom(){
        addButton.setOnClickListener {
            if(didRun){
                Toast.makeText(this,"초기화 후에 시도해주세요.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(pickNumberSet.size>=5 && !didRun){
                Toast.makeText(this,"번호는 5개까지만 선택할 수 있습니다.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(pickNumberSet.contains(numberPicker.value)){
                Toast.makeText(this,"이미 선택한 번호 입니다.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val textView = numberTextViewList[pickNumberSet.size]
            textView.isVisible = true
            textView.text = numberPicker.value.toString()

            when(numberPicker.value){
                in 1.. 10 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_blue)
                in 11.. 20 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_gray)
                in 21.. 30 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_green)
                in 31.. 40 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_red)
                else -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_yellow)
            }

            pickNumberSet.add(numberPicker.value)
        }
    }

    private fun initClearButton(){
        clearButton.setOnClickListener {
            pickNumberSet.clear()
            didRun = false

            for (textView in numberTextViewList){
                textView.isVisible = false
            }

            // 이렇게도 가능가능
            numberTextViewList.forEach {
                it.isVisible = false
            }
        }
    }

    private  fun getRandomNumber(): List<Int>{
        val numberList = mutableListOf<Int>()
            .apply {
                for (i in 1..45){
                    if(pickNumberSet.contains(i)){
                        continue
                    }
                    this.add(i)
                }
            }
        numberList.shuffle()

        val newList = pickNumberSet.toList() + numberList.subList(0, 6 - pickNumberSet.size)

        return  newList.sorted()
    }
}