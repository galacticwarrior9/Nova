package xyz.xenondevs.nova.util.reflection

import com.mojang.brigadier.tree.CommandNode
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.decoration.ArmorStand
import net.minecraft.world.item.crafting.RecipeManager
import org.bukkit.event.inventory.PrepareItemCraftEvent
import xyz.xenondevs.nova.util.reflection.ReflectionUtils.getCB
import xyz.xenondevs.nova.util.reflection.ReflectionUtils.getCBClass
import xyz.xenondevs.nova.util.reflection.ReflectionUtils.getField
import xyz.xenondevs.nova.util.reflection.ReflectionUtils.getMethod
import kotlin.jvm.internal.CallableReference
import kotlin.reflect.KProperty1

@Suppress("MemberVisibilityCanBePrivate", "UNCHECKED_CAST")
object ReflectionRegistry {
    
    val CB_PACKAGE_PATH = getCB()
    
    val CB_CRAFT_META_ITEM_CLASS = getCBClass("inventory.CraftMetaItem")
    val CB_CRAFT_META_APPLY_TO_METHOD = getMethod(CB_CRAFT_META_ITEM_CLASS, true, "applyToItem", CompoundTag::class.java)
    
    val ARMOR_STAND_ARMOR_ITEMS_FIELD = getField(ArmorStand::class.java, true, "SRF(net.minecraft.world.entity.decoration.ArmorStand armorItems)")
    val RECIPE_MANAGER_BY_NAME_FIELD = getField(RecipeManager::class.java, true, "SRF(net.minecraft.world.item.crafting.RecipeManager byName)")
    
    val COMMAND_NODE_CHILDREN_FIELD = getField(CommandNode::class.java, true, "children")
    val COMMAND_NODE_LITERALS_FIELD = getField(CommandNode::class.java, true, "literals")
    val COMMAND_NODE_ARGUMENTS_FIELD = getField(CommandNode::class.java, true, "arguments")
    
    val K_PROPERTY_1_GET_DELEGATE_METHOD = getMethod(KProperty1::class.java, false, "getDelegate", Any::class.java)
    val CALLABLE_REFERENCE_RECEIVER_FIELD = getField(CallableReference::class.java, true, "receiver")
    
    val PREPARE_ITEM_CRAFT_EVENT_MATRIX_FIELD = getField(PrepareItemCraftEvent::class.java, true, "matrix")
    
}
