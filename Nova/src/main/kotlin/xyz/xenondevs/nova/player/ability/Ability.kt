package xyz.xenondevs.nova.player.ability

import org.bukkit.entity.Player

internal abstract class Ability(val player: Player) {
    
    abstract fun handleRemove()
    
    abstract fun handleTick(tick: Int)
    
}