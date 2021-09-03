package com.github.pambrose

import com.github.pambrose.ChoiceOrientation.VERTICAL
import io.kvision.Application
import io.kvision.core.*
import io.kvision.core.BorderStyle.SOLID
import io.kvision.core.Col.GRAY
import io.kvision.core.Col.WHITE
import io.kvision.form.text.Text
import io.kvision.html.*
import io.kvision.html.ButtonStyle.*
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
      panel.refreshPanel("/")
    }
  }

  suspend fun Container.refreshPanel(name: String) {
    val title = Model.title(name)
    val content = Model.content(name)
    val choices = Model.choices(name)
    val choiceOrientation = Model.choiceOrientation(name)
    val parentTitles = Model.parentTitles(name)
    val currentScore = Model.currentScore()

    removeAll()

    div {
      margin = 10.px

      div {
        border = Border(2.px, SOLID, Color.name(Col.WHITE))
        paddingTop = 5.px
        paddingBottom = 5.px
        textAlign = TextAlign.LEFT
        +"Total moves: $currentScore"
      }

      h1 {
        background = Background(Color.rgb(53, 121, 246))
        color = Color.name(WHITE)
        textAlign = TextAlign.CENTER
        +title
      }

      div {
        border = Border(2.px, SOLID, Color.name(GRAY))
        padding = 25.px
        add(P(content, true))
      }

      div {
        marginTop = 10.px
        val spacing = 4
        val init: Container.() -> Unit = { addButtons(title, choices, this@refreshPanel) }
        if (choiceOrientation == VERTICAL)
          vPanel(spacing = spacing, init = init)
        else
          hPanel(spacing = spacing, init = init)
      }

      if (parentTitles.isNotEmpty()) {
        div {
          marginTop = 10.px

          vPanel {
            button("Go Back In Time", style = SUCCESS) {
              onClick {
                val dialog =
                  Dialog<String>("Go back to...") {
                    vPanel(spacing = 4) {
                      parentTitles.forEach { title ->
                        button(title, style = PRIMARY) { onClick { setResult(title) } }
                      }
                    }
                  }

                AppScope.launch {
                  dialog.getResult()?.also { title ->
                    if (title.isNotBlank()) this@refreshPanel.refreshPanel(title)
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  fun Container.addButtons(title: String, choices: List<ChoiceTitle>, mainPanel: Container) {
    choices.forEach { ct ->
      button(ct.choice, style = PRIMARY) {
        onClick {
          val submit = Button("OK", disabled = true)
          val dialog =
            Dialog<String>("Reasoning") {
              val input =
                Text(label = "Reason for your decision:") {
                  placeholder = """I chose "${ct.choice}" because..."""
                  setEventListener<Text> {
                    keyup = { e ->
                      submit.disabled = value.isNullOrBlank()
                    }
                  }
                }
              add(input)
              addButton(Button("Cancel", style = OUTLINESECONDARY).also { it.onClick { setResult("") } })
              addButton(submit.also {
                it.onClick {
                  setResult(input.value)
                }
              })
            }

          AppScope.launch {
            dialog.getResult()?.also { response ->
              if (response.isNotBlank()) {
                Model.choose("user1", title, ct.title, ct.choice, response)
                mainPanel.refreshPanel(ct.title)
              }
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
