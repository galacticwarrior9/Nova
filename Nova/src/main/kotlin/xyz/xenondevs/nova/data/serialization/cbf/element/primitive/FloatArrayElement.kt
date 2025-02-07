package xyz.xenondevs.nova.data.serialization.cbf.element.primitive

import io.netty.buffer.ByteBuf
import xyz.xenondevs.nova.data.serialization.cbf.BackedElement
import xyz.xenondevs.nova.data.serialization.cbf.BinaryDeserializer

class FloatArrayElement(override val value: FloatArray) : BackedElement<FloatArray>() {
    
    override fun getTypeId() = 13
    
    override fun write(buf: ByteBuf) {
        require(value.size <= 65535) { "Float array is too large!" }
        buf.writeShort(value.size)
        value.forEach(buf::writeFloat)
    }
    
    override fun toString(): String {
        return value.contentToString()
    }
    
    override fun equals(other: Any?): Boolean {
        return other is FloatArrayElement && value.contentEquals(other.value)
    }
    
    override fun hashCode(): Int {
        return value.contentHashCode()
    }
    
}

object FloatArrayDeserializer : BinaryDeserializer<FloatArrayElement> {
    override fun read(buf: ByteBuf): FloatArrayElement {
        val array = FloatArray(buf.readUnsignedShort())
        repeat(array.size) {
            array[it] = buf.readFloat()
        }
        return FloatArrayElement(array)
    }
}