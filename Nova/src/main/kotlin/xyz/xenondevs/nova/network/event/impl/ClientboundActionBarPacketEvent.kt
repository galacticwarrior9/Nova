package xyz.xenondevs.nova.network.event.impl

import net.md_5.bungee.api.chat.BaseComponent
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import xyz.xenondevs.nova.network.event.PacketEvent
import xyz.xenondevs.nova.util.data.MutableLazy
import xyz.xenondevs.nova.util.data.toBaseComponentArray
import xyz.xenondevs.nova.util.data.toComponent

class ClientboundActionBarPacketEvent(
    player: Player,
    packet: ClientboundSetActionBarTextPacket
) : PacketEvent<ClientboundSetActionBarTextPacket>(player, packet) {
    
    companion object {
        @JvmStatic
        private val handlers = HandlerList()
        
        @JvmStatic
        fun getHandlerList() = handlers
    }
    
    private var changed = false
    
    override val packet: ClientboundSetActionBarTextPacket
        get() {
            return if (changed)
                ClientboundSetActionBarTextPacket(text?.toComponent())
            else super.packet
        }
    
    var text: Array<BaseComponent>? by MutableLazy({ changed = true }) {
        packet.text?.toBaseComponentArray()
    }
    
    override fun getHandlers(): HandlerList {
        return Companion.handlers
    }
    
}