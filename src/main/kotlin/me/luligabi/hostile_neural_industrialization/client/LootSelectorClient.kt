package me.luligabi.hostile_neural_industrialization.client

import aztech.modern_industrialization.machines.gui.ClientComponentRenderer
import aztech.modern_industrialization.machines.gui.ClientComponentRenderer.ButtonContainer
import aztech.modern_industrialization.machines.gui.GuiComponentClient
import aztech.modern_industrialization.machines.gui.MachineScreen
import aztech.modern_industrialization.util.Rectangle
import aztech.modern_industrialization.util.TextHelper
import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.mono.loot_selector.LootSelector
import me.luligabi.hostile_neural_industrialization.common.misc.network.SelectLootPacket
import me.luligabi.hostile_neural_industrialization.common.util.HNIText
import me.luligabi.hostile_neural_industrialization.mixin.ScreenAccessor
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack

class LootSelectorClient(buf: RegistryFriendlyByteBuf) : GuiComponentClient {

    private companion object {

        val BUTTONS = HNI.id("textures/gui/loot_selector/buttons.png")
        val BACKGROUND = HNI.id("textures/gui/loot_selector/background.png")

        // TODO Accesswiden from StonecutterScreen
        val RECIPE_SELECTED_SPRITE = ResourceLocation.withDefaultNamespace("container/stonecutter/recipe_selected")
        val RECIPE_HIGHLIGHTED_SPRITE =
            ResourceLocation.withDefaultNamespace("container/stonecutter/recipe_highlighted")
        val RECIPE_SPRITE = ResourceLocation.withDefaultNamespace("container/stonecutter/recipe")
    }

    private var selectedId: ResourceLocation? = null
    private var lootList: List<ItemStack> = emptyList()

    init {
        readCurrentData(buf)
    }

    override fun readCurrentData(buf: RegistryFriendlyByteBuf) {

        val id = buf.readResourceLocation()
        val list = ItemStack.LIST_STREAM_CODEC.decode(buf)

        selectedId = if (id == LootSelector.NONE) null else id
        lootList = list
    }

    override fun createRenderer(screen: MachineScreen) = Renderer(screen)

    inner class Renderer(private val screen: MachineScreen) : ClientComponentRenderer {

        private var isPanelOpen = false

        private val panelWidth = 109
        private val panelHeight = 94

        override fun addButtons(container: ButtonContainer) {
            screen.addButton(
                -24, 17, 20, 20,
                { _ -> isPanelOpen = !isPanelOpen },
                {
                    listOf(
                        HNIText.LOOT_SELECTOR_TITLE.text(),
                        HNIText.LOOT_SELECTOR_DESCRIPTION.text().setStyle(TextHelper.GRAY_TEXT)
                    )
                },
                { screen, button, gui, _, _, _ ->

                    val selectedId = this@LootSelectorClient.selectedId
                    val hasInputItem = !screen.menu.inventory.itemStacks[0].isEmpty


                    val u = when {
                        selectedId == null -> if (hasInputItem) 20f else 0f
                        else -> 40f
                    }
                    val v = if (button.isHoveredOrFocused) 20f else 0f

                    gui.blit(BUTTONS, button.x, button.y, u, v, button.width, button.height, 60, 40)
                    selectedId?.let {
                        gui.renderItem(ItemStack(BuiltInRegistries.ITEM.get(it)), button.x + 2, button.y + 2)
                    }

                }
            )

            this@LootSelectorClient.lootList.forEachIndexed { i, stack ->

                val col = i % 6
                val row = i / 6

                val x = -101 + col * 16
                val y = 42 + row * 18

                screen.addButton(
                    x, y, 16, 18,
                    { syncId -> SelectLootPacket(syncId, BuiltInRegistries.ITEM.getKey(stack.item)).sendToServer() },
                    {
                        listOf(
                            (HNIText.LOOT_SELECTOR_MEMBER_NAME
                                .arg(stack.count)
                                .arg(stack.hoverName))
                                .withStyle(TextHelper.GRAY_TEXT)
                        )
                    },
                    { _, button, gui, _, _, _ ->

                        val texture = when {
                            BuiltInRegistries.ITEM.getKey(stack.item) == this@LootSelectorClient.selectedId -> RECIPE_SELECTED_SPRITE
                            button.isHovered -> RECIPE_HIGHLIGHTED_SPRITE
                            else -> RECIPE_SPRITE
                        }

                        gui.blitSprite(texture, button.x, button.y, 16, 18)
                        gui.renderItem(stack, button.x, button.y)
                        gui.renderItemDecorations(Minecraft.getInstance().font, stack, button.x, button.y)
                    },
                    { isPanelOpen }
                )

            }

        }

        fun refreshSelectionButtons() {
            DelayedClientTask.runLater(2) {
                (screen as ScreenAccessor).invokeRebuildWidgets() // TODO overkill, could check for the specific buttons
            }
        }

        override fun renderBackground(gui: GuiGraphics, leftPos: Int, topPos: Int) {
            val box = getBox(leftPos, topPos)
            gui.blit(MachineScreen.BACKGROUND, box.x(), box.y(), 0, 0, box.w(), box.h() - 4)
            gui.blit(MachineScreen.BACKGROUND, box.x(), box.y() + box.h() - 4, 0, 252, box.w(), 4)

            if (isPanelOpen) {
                gui.blit(BACKGROUND, box.x() + 7, box.y() + 31, 0f, 0f, 98, 56, 98, 56)
            }

        }

        private fun getBox(leftPos: Int, topPos: Int): Rectangle {
            return if (isPanelOpen) {
                Rectangle(
                    leftPos - panelWidth, topPos + 10,
                    panelWidth, panelHeight
                )
            } else {
                Rectangle(leftPos - 31, topPos + 10, 31, 34)
            }
        }

        override fun addExtraBoxes(rectangles: MutableList<Rectangle>, leftPos: Int, topPos: Int) {
            rectangles.add(getBox(leftPos, topPos))
        }

    }

}