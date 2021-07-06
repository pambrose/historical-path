package com.github.pambrose

import io.kvision.Application
import io.kvision.core.Container
import io.kvision.form.text.Text
import io.kvision.html.Button
import io.kvision.html.ButtonStyle.OUTLINESECONDARY
import io.kvision.html.ButtonStyle.PRIMARY
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

  suspend fun assignPanel(title: String, panel: VPanel) {
    val content = Model.content(title)
    val choiceOrientation = Model.choiceOrientation(title)
    val choices = Model.choices(title)
    panel.apply {
      removeAll()
      div {
        margin = 10.px

        vPanel {
          add(P(content, true))
        }

        if (choiceOrientation == ChoiceOrientation.VERTICAL)
          vPanel(spacing = 4) { addContent(choices, panel) }
        else
          hPanel(spacing = 4) { addContent(choices, panel) }
      }
    }
  }

  fun Container.addContent(choices: List<ChoiceTitle>, panel: VPanel) {
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
            dialog.getResult().also {
              if (!it.isNullOrBlank())
                assignPanel(p.title, panel)
            }
          }
        }
      }
    }
  }

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
}

fun main() {
  startApplication(::App, module.hot)
}
