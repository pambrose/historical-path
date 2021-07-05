package com.github.pambrose

import io.kvision.Application
import io.kvision.form.text.Text
import io.kvision.html.Button
import io.kvision.html.ButtonStyle.PRIMARY
import io.kvision.html.P
import io.kvision.html.button
import io.kvision.i18n.DefaultI18nManager
import io.kvision.i18n.I18n
import io.kvision.modal.Dialog
import io.kvision.module
import io.kvision.panel.VPanel
import io.kvision.panel.hPanel
import io.kvision.panel.root
import io.kvision.panel.vPanel
import io.kvision.startApplication
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch

val AppScope = CoroutineScope(window.asCoroutineDispatcher())

class App : Application() {

  override fun start(state: Map<String, Any>) {
    I18n.manager =
      DefaultI18nManager(
        mapOf(
          "en" to io.kvision.require("i18n/messages-en.json"),
          "pl" to io.kvision.require("i18n/messages-pl.json")
        )
      )

    val root = root("kvapp") {}

    suspend fun assignPanel(title: String, panel: VPanel) {
      val content = Model.content(title)
      val choices = Model.choices(title)
      panel.apply {
        removeAll()
        vPanel {
          add(P(content, true))
        }

        hPanel {
          choices.forEach { p ->
            button(p.choice, style = PRIMARY) {
              onClick {
                val submit = Button("Submit", disabled = true)
                val dialog =
                  Dialog<String>("Reasoning") {
                    val input = Text(label = "Reason for your decision:") {
                      placeholder = "I made this decision because..."
                      setEventListener<Text> {
                        keyup = { e ->
                          println("Key pressed ${value.isNullOrEmpty()} $value")
                          submit.disabled = value.isNullOrEmpty()
                        }
                      }
                    }
                    add(input)
                    addButton(submit.also { it.onClick { setResult(input.value) } })
                    addButton(Button("Cancel").also { it.onClick { setResult("") } })
                  }

                AppScope.launch {
                  dialog.getResult().also {
                    if (!it.isNullOrEmpty())
                      assignPanel(p.title, panel)
                  }
                }
              }
            }
          }
        }
      }
    }

    AppScope.launch {
      val panel = VPanel()
      root.add(
        VPanel {
          add(panel)

//          hPanel {
//            button("Choice 1", style = PRIMARY) {
//              onClick {
//                AppScope.launch {
//                  panel.apply {
//                    removeAll()
//                    add(P(Model.content("Hello world from client!"), true))
//                  }
//                }
//              }
//            }
//
//            button("Choice 2", style = PRIMARY) {
//              onClick {
//                AppScope.launch {
//                  val t = Text(label = "Decision Explanation") {
//                    placeholder = "I made this decision because..."
//                  }
//                  val dialog = Dialog<String>("Dialog with result") {
//                    add(Span("Press a button"))
//                    add(t)
//                    addButton(Button("Button").onClick {
//                      setResult(t.value)
//                    })
//                  }
//                  val result = dialog.getResult()
//                  console.log(result)
//                }
//              }
//            }
//          }
        })
      assignPanel("/", panel)
    }
  }
}

fun main() {
  startApplication(::App, module.hot)
}
