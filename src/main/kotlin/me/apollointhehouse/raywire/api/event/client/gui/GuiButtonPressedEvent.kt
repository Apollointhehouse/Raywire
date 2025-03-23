package me.apollointhehouse.raywire.api.event.client.gui

import me.apollointhehouse.raywire.api.Event
import net.minecraft.client.gui.ButtonElement
import net.minecraft.client.gui.Screen

class GuiButtonPressedEvent(
    var gui: Screen,
    var button: ButtonElement
) : Event
