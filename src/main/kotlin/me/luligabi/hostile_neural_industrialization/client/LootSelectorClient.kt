package me.luligabi.hostile_neural_industrialization.client

import aztech.modern_industrialization.machines.gui.ClientComponentRenderer
import aztech.modern_industrialization.machines.gui.ClientComponentRenderer.CustomButtonRenderer
import aztech.modern_industrialization.machines.gui.GuiComponentClient
import aztech.modern_industrialization.machines.gui.MachineScreen
import me.luligabi.hostile_neural_industrialization.common.HNI
import net.minecraft.client.gui.Font
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack

class LootSelectorClient(buf: RegistryFriendlyByteBuf): GuiComponentClient {

    private companion object {

        val BG_TEXTURE = HNI.id("textures/gui/loot_selector/loot_selector_bg.png")

        val UNSELECTED_TEXTURE = HNI.id("textures/gui/loot_selector/loot_selector_unselected.png")
        val SELECTED_TEXTURE = HNI.id("textures/gui/loot_selector/loot_selector_selected.png")

        // TODO Accesswiden from StonecutterScreen
        val RECIPE_SELECTED_SPRITE = ResourceLocation.withDefaultNamespace("container/stonecutter/recipe_selected")
        val RECIPE_HIGHLIGHTED_SPRITE = ResourceLocation.withDefaultNamespace("container/stonecutter/recipe_highlighted")
        val RECIPE_SPRITE = ResourceLocation.withDefaultNamespace("container/stonecutter/recipe")

    }

    init {
        readCurrentData(buf)
    }

    private var selectedIndex = -1
    private var lootList: List<ItemStack> = emptyList()

    private var open = false

    override fun readCurrentData(buf: RegistryFriendlyByteBuf) {
        selectedIndex = buf.readInt()
        lootList = ItemStack.LIST_STREAM_CODEC.decode(buf)
    }

    override fun createRenderer(screen: MachineScreen) = Renderer(screen)

    inner class Renderer(private val screen: MachineScreen): ClientComponentRenderer {

        override fun addButtons(container: ClientComponentRenderer.ButtonContainer) {

            container.addButton(
                48, 36,
                20, 20,
                { syncId ->
                    println(":3 $syncId")
                    open = !open
                },
                { listOf<Component>() },
                ButtonRenderer()
            )
        }

        override fun renderBackground(gui: GuiGraphics, x: Int, y: Int) {
            if (!open) return

            gui.pose().pushPose()
            gui.pose().translate(.0, .0, 2000.0)
            gui.blit(BG_TEXTURE, screen.guiLeft + 4, screen.guiTop - 37, 0f, 0f, 112, 69, 112, 69)

            val l = screen.guiLeft + 12
            val i1 = screen.guiTop - 31
            val j1 = 12
            renderButtons(gui, x, y, l, i1)
            renderRecipes(gui, l, i1, j1)

            gui.pose().popPose()
        }

        override fun renderTooltip(screen: MachineScreen, font: Font, gui: GuiGraphics, leftPos: Int, topPos: Int, cursorX: Int, cursorY: Int) {
            if (!open) return

            val x = screen.guiLeft + 12
            val y = screen.guiTop - 31

            for (i in 0 until lootList.size) {
                val j1 = x + i % 4 * 16
                val k1 = y + i / 4 * 18 + 2
                if (cursorX >= j1 && cursorX < j1 + 16 && cursorY >= k1 && cursorY < k1 + 18) {
                    gui.renderTooltip(font, lootList[i], cursorX, cursorY)
                }
            }
        }

        private fun renderButtons(gui: GuiGraphics, mouseX: Int, mouseY: Int, x: Int, y: Int) {
            for (i in 0 until lootList.size) {
                val k = x + i % 9 * 16
                val l = i / 9
                val i1 = y + l * 18 + 2

                val texture = if (i == selectedIndex) {
                    RECIPE_SELECTED_SPRITE
                } else if (mouseX >= k && mouseY >= i1 && mouseX < k + 16 && mouseY < i1 + 18) {
                    RECIPE_HIGHLIGHTED_SPRITE
                } else {
                    RECIPE_SPRITE
                }

                gui.blitSprite(texture, k, i1 - 1, 16, 18)
            }
        }

        private fun renderRecipes(gui: GuiGraphics, x: Int, y: Int, startIndex: Int) {
            for (i in 0 until lootList.size) {
                val k = x + i % 4 * 16
                val l = i / 4
                val i1 = y + l * 18 + 2
                gui.renderItem(lootList[i], k, i1)
            }
        }

        inner class ButtonRenderer: CustomButtonRenderer {

            override fun renderButton(screen: MachineScreen, button: MachineScreen.MachineButton, gui: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {

                val texture = if (selectedIndex >= 0) SELECTED_TEXTURE else UNSELECTED_TEXTURE
                val v = if (button.isHoveredOrFocused) 20f else 0f

                gui.blit(texture, screen.guiLeft + 48, screen.guiTop + 36, 0f, v, 20, 20, 20, 40)
            }

        }

    }
}