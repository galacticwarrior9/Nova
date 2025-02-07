package xyz.xenondevs.nova.util

import io.netty.buffer.Unpooled
import net.minecraft.core.BlockPos
import net.minecraft.core.NonNullList
import net.minecraft.core.Registry
import net.minecraft.core.Rotations
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientboundAddMobPacket
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.dedicated.DedicatedServer
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.network.ServerGamePacketListenerImpl
import net.minecraft.world.entity.EntityType
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.World
import org.bukkit.craftbukkit.v1_18_R2.CraftServer
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftEntity
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector
import java.util.*
import net.minecraft.world.item.ItemStack as NMSItemStack

val Entity.nmsEntity: net.minecraft.world.entity.Entity
    get() = (this as CraftEntity).handle

val Player.serverPlayer: ServerPlayer
    get() = (this as CraftPlayer).handle

val ItemStack.nmsStack: NMSItemStack
    get() = CraftItemStack.asNMSCopy(this)

val NMSItemStack.bukkitStack
    get() = CraftItemStack.asBukkitCopy(this)

val Location.blockPos: BlockPos
    get() = BlockPos(blockX, blockY, blockZ)

val World.serverLevel: ServerLevel
    get() = (this as CraftWorld).handle

val Player.connection: ServerGamePacketListenerImpl
    get() = serverPlayer.connection

val NamespacedKey.resourceLocation: ResourceLocation
    get() = ResourceLocation(toString())

fun Player.send(vararg packets: Packet<*>) {
    val connection = connection
    packets.forEach { connection.send(it) }
}

fun Rotations.copy(x: Float? = null, y: Float? = null, z: Float? = null) =
    Rotations(x ?: this.x, y ?: this.y, z ?: this.z)

fun Rotations.add(x: Float, y: Float, z: Float) =
    Rotations(this.x + x, this.y + y, this.z + z)

val minecraftServer: DedicatedServer = (Bukkit.getServer() as CraftServer).server

val serverTick: Int
    get() = minecraftServer.tickCount

fun <E> NonNullList(list: List<E>, default: E? = null): NonNullList<E> {
    val nonNullList = if (default == null)
        NonNullList.createWithCapacity(list.size)
    else NonNullList.withSize<E>(list.size, default)
    
    nonNullList.addAll(list)
    
    return nonNullList
}

object NMSUtils {
    
    fun createTeleportPacket(id: Int, location: Location): ClientboundTeleportEntityPacket {
        val buffer = FriendlyByteBuf(Unpooled.buffer())
        buffer.writeVarInt(id)
        buffer.writeDouble(location.x)
        buffer.writeDouble(location.y)
        buffer.writeDouble(location.z)
        buffer.writeByte(location.yaw.toPackedByte().toInt())
        buffer.writeByte(location.pitch.toPackedByte().toInt())
        buffer.writeBoolean(true)
        
        return ClientboundTeleportEntityPacket(buffer)
    }
    
    fun createAddMobPacket(id: Int, uuid: UUID, type: EntityType<*>, location: Location, velocity: Vector? = null): ClientboundAddMobPacket {
        val buffer = FriendlyByteBuf(Unpooled.buffer())
        
        val packedYaw = location.yaw.toPackedByte().toInt()
        buffer.writeVarInt(id)
        buffer.writeUUID(uuid)
        buffer.writeVarInt(Registry.ENTITY_TYPE.getId(type))
        buffer.writeDouble(location.x)
        buffer.writeDouble(location.y)
        buffer.writeDouble(location.z)
        buffer.writeByte(packedYaw)
        buffer.writeByte(location.pitch.toPackedByte().toInt())
        buffer.writeByte(packedYaw)
        buffer.writeShort(velocity?.x?.toFixedPoint()?.toInt() ?: 0)
        buffer.writeShort(velocity?.y?.toFixedPoint()?.toInt() ?: 0)
        buffer.writeShort(velocity?.z?.toFixedPoint()?.toInt() ?: 0)
        
        return ClientboundAddMobPacket(buffer)
    }
    
}
