package com.github.pambrose

import com.github.pambrose.ChoiceOrientation.VERTICAL
import io.kvision.Application
import io.kvision.core.Container
import io.kvision.form.text.Text
import io.kvision.html.Button
import io.kvision.html.ButtonStyle.*
import io.kvision.html.P
import io.kvision.html.button
import io.kvision.html.div
import io.kvision.i18n.DefaultI18nManager
import io.kvision.i18n.I18n
import io.kvision.modal.Dialog
import io.kvision.module
import io.kvision.panel.VPanel
import io.kvision.panel.hPanel
import io.kvision.panel.root
import io.kvision.panel.vPanel
import io.kvision.startApplication
import io.kvision.utils.px
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

    AppScope.launch {
      val panel = VPanel()
      root.add(
        VPanel {
          add(panel)
        })
      assignPanel("/", panel)
    }
  }

  suspend fun assignPanel(title: String, panel: Container) {
    val content = Model.content(title)
    val choices = Model.choices(title)
    val choiceOrientation = Model.choiceOrientation(title)
    val parentTitles = Model.parentTitles(title)
    panel.apply {
      removeAll()
      div {
        margin = 10.px

        vPanel {
          add(P(content, true))
        }

        if (choiceOrientation == VERTICAL)
          vPanel(spacing = 4) { addButtons(choices, panel) }
        else
          hPanel(spacing = 4) { addButtons(choices, panel) }

        if (parentTitles.isNotEmpty()) {
          div {
            marginTop = 10.px

            vPanel {
              button("Go Back In Time...", style = SUCCESS) {
                onClick {
                  val dialog =
                    Dialog<String>("Go Back To...") {
                      vPanel(spacing = 4) {
                        parentTitles.forEach { title ->
                          button(title, style = PRIMARY) {
                            onClick {
                              setResult(title)
                            }
                          }
                        }
                      }
                    }
                  AppScope.launch {
                    dialog.getResult()?.also { title ->
                      if (title.isNotBlank())
                        assignPanel(title, panel)
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  fun Container.addButtons(choices: List<ChoiceTitle>, panel: Container) {
    choices.forEach { p ->
      button(p.choice, style = PRIMARY) {
        onClick {
          val submit = Button("OK", disabled = true)
          val dialog =
            Dialog<String>("Reasoning") {
              val input =
                Text(label = "Reason for your decision:") {
                  placeholder = "I made this decision because..."
                  setEventListener<Text> {
                    keyup = { e ->
                      submit.disabled = value.isNullOrBlank()
                    }
                  }
                }
              add(input)
              addButton(Button("Cancel", style = OUTLINESECONDARY).also { it.onClick { setResult("") } })
              addButton(submit.also { it.onClick { setResult(input.value) } })
            }

          AppScope.launch {
            dialog.getResult()?.also { response ->
              if (response.isNotBlank())
                assignPanel(p.title, panel)
            }
          }
        }
      }
    }
  }
}

fun main() {
  startApplication(::App, module.hot)
}
