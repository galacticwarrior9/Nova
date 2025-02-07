package xyz.xenondevs.nova.ui

import de.studiocode.invui.item.Item
import de.studiocode.invui.item.ItemProvider
import de.studiocode.invui.item.builder.ItemBuilder
import de.studiocode.invui.item.impl.BaseItem
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import xyz.xenondevs.nova.material.NovaMaterial
import java.util.function.Supplier
import kotlin.math.max
import kotlin.math.min
import kotlin.math.round

abstract class VerticalBar(height: Int) : Supplier<Item> {
    
    abstract val barMaterial: NovaMaterial
    
    private val barItems = Array(height) { createBarItem(it, height) }
    private var i = 0
    var percentage: Double = 0.0
        set(value) {
            field = value
            barItems.forEach(Item::notifyWindows)
        }
    
    override fun get(): Item {
        i = (i - 1).mod(barItems.size)
        return barItems[i]
    }
    
    protected open fun modifyItemBuilder(itemBuilder: ItemBuilder): ItemBuilder = itemBuilder
    
    protected open fun createBarItem(section: Int, totalSections: Int) = VerticalBarItem(section, totalSections)
    
    protected open inner class VerticalBarItem(
        private val section: Int,
        private val totalSections: Int,
    ) : BaseItem() {
        
        override fun getItemProvider(): ItemProvider {
            val displayPercentageStart = (1.0 / totalSections) * section
            val displayPercentage = max(min((percentage - displayPercentageStart) * totalSections, 1.0), 0.0)
            val state = round(displayPercentage * 16).toInt()
            
            return modifyItemBuilder(barMaterial.item.createItemBuilder(state))
        }
        
        override fun handleClick(clickType: ClickType, player: Player, event: InventoryClickEvent) = Unit
        
    }
    
}