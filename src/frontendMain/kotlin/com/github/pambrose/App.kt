package com.github.pambrose

import io.kvision.Application
import io.kvision.form.text.Text
import io.kvision.html.Button
import io.kvision.html.ButtonStyle.PRIMARY
import io.kvision.html.P
import io.kvision.html.Span
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
import kotlinx.coroutines.GlobalScope
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
        val root = root("kvapp") {
        }

        AppScope.launch {
            val panel = VPanel()
            root.add(
                VPanel {
                    vPanel {
                        add(panel)
                    }
                    hPanel {
                        button("Choice 1", style = PRIMARY) {
                            onClick {
                                AppScope.launch {
                                    panel.apply {
                                        removeAll()
                                        add(P(Model.ping("Hello world from client!"), true))
                                    }
                                }
                            }
                        }
                        button("Choice 2", style = PRIMARY) {
                            onClick {
                                GlobalScope.launch {
                                    val t = Text(label = "Decision Explanation") {
                                        placeholder = "I made this decision because..."
                                    }
                                    val dialog = Dialog<String>("Dialog with result") {
                                        add(Span("Press a button"))
                                        add(t)
                                        addButton(Button("Button").onClick {
                                            setResult(t.value)
                                        })
                                    }
                                    val result = dialog.getResult()
                                    console.log(result)
                                }
                            }
                        }
                    }
                })
        }
    }
}

fun main() {
    startApplication(::App, module.hot)
}
