package com.example.tictactoe

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {

    private lateinit var top: ImageView
    private lateinit var topStart: ImageView
    private lateinit var topEnd: ImageView

    private lateinit var center: ImageView
    private lateinit var centerStart: ImageView
    private lateinit var centerEnd: ImageView

    private lateinit var bottom: ImageView
    private lateinit var bottomStart: ImageView
    private lateinit var bottomEnd: ImageView

    private lateinit var list: List<ImageView>

    private lateinit var restart: Button

    private var count: Int = 1
    private var gameEnded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        restart = findViewById(R.id.restart)
        restart.setOnClickListener { restartGame() }

        top = findViewById(R.id.top)
        topStart = findViewById(R.id.topStart)
        topEnd = findViewById(R.id.topEnd)

        center = findViewById(R.id.center)
        centerStart = findViewById(R.id.centerStart)
        centerEnd = findViewById(R.id.centerEnd)

        bottom = findViewById(R.id.bottom)
        bottomStart = findViewById(R.id.bottomStart)
        bottomEnd = findViewById(R.id.bottomEnd)

        list = listOf(
            top, topStart, topEnd, center, centerStart,
            centerEnd, bottom, bottomStart, bottomEnd
        )

        for (box in list) {
            configureBox(box)
        }

    }

    private fun restartGame(){
        count = 1
        gameEnded = false

        for (box in list){
            box.setImageDrawable(null)
            box.tag = null
        }
    }

    private fun configureBox(box: ImageView){
        box.setOnClickListener{
            if(box.tag == null && !gameEnded) {
                if(isOdd(count)) {
                    box.setImageResource(R.drawable.baseline_close_24)
                    box.tag = 1
                }
                else {
                    box.setImageResource(R.drawable.baseline_remove_circle_24)
                    box.tag = 2
                }
//                Toast.makeText(this, "count: $count", Toast.LENGTH_SHORT).show()
                count++
            }

            if(playerWin(1) && !gameEnded) {
                Toast.makeText(this, "Player 1 Win", Toast.LENGTH_SHORT).show()
                gameEnded = true
            }
            else if(playerWin(2) && !gameEnded) {
                Toast.makeText(this, "Player 2 Win", Toast.LENGTH_SHORT).show()
                gameEnded = true
            } else if(count == 10 && !gameEnded){
                Toast.makeText(this, "Draw", Toast.LENGTH_SHORT).show()
                gameEnded = true
            }
        }
    }

    private fun isOdd(value: Int): Boolean = (value and 1) == 1

    private fun playerWin(value: Int): Boolean {
        if(top.tag == value && center.tag == value && bottom.tag == value) return true
        if(topStart.tag == value && centerStart.tag == value && bottomStart.tag == value) return true
        if(topEnd.tag == value && centerEnd.tag == value && bottomEnd.tag == value) return true

        if(topStart.tag == value && top.tag == value && topEnd.tag == value) return true
        if(centerStart.tag == value && center.tag == value && centerEnd.tag == value) return true
        if(bottomStart.tag == value && bottom.tag == value && bottomEnd.tag == value) return true

        if(topStart.tag == value && center.tag == value && bottomEnd.tag == value) return true
        if(topEnd.tag == value && center.tag == value && bottomStart.tag == value) return true

        return false
    }
}