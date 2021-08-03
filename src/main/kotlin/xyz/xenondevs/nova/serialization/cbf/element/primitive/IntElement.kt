package xyz.xenondevs.nova.serialization.cbf.element.primitive

import io.netty.buffer.ByteBuf
import xyz.xenondevs.nova.serialization.cbf.BackedElement
import xyz.xenondevs.nova.serialization.cbf.BinaryDeserializer
import xyz.xenondevs.nova.serialization.cbf.Element

class IntElement(override val value: Int) : BackedElement<Int> {
    
    override fun getTypeId() = 3.toByte()
    
    override fun write(buf: ByteBuf) {
        buf.writeInt(value)
    }
    
    override fun toString() = value.toString()
    
}

object IntDeserializer : BinaryDeserializer<IntElement> {
    override fun read(buf: ByteBuf) = IntElement(buf.readInt())
}