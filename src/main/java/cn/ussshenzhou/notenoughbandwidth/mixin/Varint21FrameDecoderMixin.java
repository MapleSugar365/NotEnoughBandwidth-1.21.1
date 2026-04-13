package cn.ussshenzhou.notenoughbandwidth.mixin;

import cn.ussshenzhou.notenoughbandwidth.NotEnoughBandwidthConfig;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.EncoderException;
import net.minecraft.network.VarInt;
import net.minecraft.network.Varint21FrameDecoder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

/**
 * @author USS_Shenzhou
 */
@Mixin(Varint21FrameDecoder.class)
public class Varint21FrameDecoderMixin {

    @ModifyConstant(method = "<init>", constant = @Constant(intValue = 3))
    private int nebAllowBiggerPacket0(int constant) {
        return 4;
    }

    @ModifyConstant(method = "copyVarint", constant = @Constant(intValue = 3))
    private static int nebAllowBiggerPacket1(int constant) {
        return 4;
    }

    @Redirect(method = "decode", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/VarInt;read(Lio/netty/buffer/ByteBuf;)I"))
    private int nebCheckPacketSize(ByteBuf buf) {
        int length = VarInt.read(buf);
        int maxSize = NotEnoughBandwidthConfig.get().getMaxPacketSize();
        if (length > maxSize) {
            throw new EncoderException("NEB: Packet too large: size " + length + " is over " + maxSize);
        }
        return length;
    }

}