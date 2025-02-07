package xyz.xenondevs.nova.data.serialization.cbf.element.primitive

import io.netty.buffer.ByteBuf
import xyz.xenondevs.nova.data.serialization.cbf.BackedElement
import xyz.xenondevs.nova.data.serialization.cbf.BinaryDeserializer

class CharElement(override val value: Char) : BackedElement<Char>() {
    
    override fun getTypeId() = 4
    
    override fun write(buf: ByteBuf) {
        buf.writeChar(value.code)
    }
    
    override fun toString() = value.toString()
    
}

object CharDeserializer : BinaryDeserializer<CharElement> {
    override fun read(buf: ByteBuf) = CharElement(buf.readChar())
}