package com.dabyz.save2disck

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import java.util.*

data class Product(var c1: String = "", var c2: String = "") : Serializable

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState); setContentView(R.layout.activity_main)
    bSave.setOnClickListener {
      save()

    }
    bLoad.setOnClickListener {
      load()
    }
  }

  fun save2Disk(s: String) = openFileOutput("edu.txt", MODE_PRIVATE).write(s.toByteArray())

  fun load2Disk(): String {
    var br = BufferedReader(InputStreamReader(openFileInput("edu.txt")))
    var sb = StringBuilder()
    while (true) {
      var line: String? = br.readLine() ?: break
      sb.append(line)
    }
    return sb.toString()
  }


  private fun fromString(s: String): Any? {
    val data: ByteArray = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
      Base64.getDecoder().decode(s)
    } else {
      TODO("VERSION.SDK_INT < O")
    }
    val ois = ObjectInputStream(ByteArrayInputStream(data))
    ois.close()
    return ois.readObject()
  }

  private fun obj2String(o: Serializable): String {
    val baos = ByteArrayOutputStream()
    val oos = ObjectOutputStream(baos)
    oos.writeObject(o)
    oos.close()
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
      Base64.getEncoder().encodeToString(baos.toByteArray())
    } else {
      TODO("VERSION.SDK_INT < O")
    }
  }

  private fun save() {
    var p = Product(et1.text.toString(), et2.text.toString())
    save2Disk(obj2String(p))
    et1.text.clear()
    et2.text.clear()
  }

  private fun load() {
    var o = fromString(load2Disk()) as Product
    et1.setText(o.c1)
    et2.setText(o.c2)
  }


}