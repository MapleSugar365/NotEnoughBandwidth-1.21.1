package cn.ussshenzhou.notenoughbandwidth.util;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public class PacketUtil {
    public static ResourceLocation getTrueType(Packet<?> packet) {
        if (packet instanceof ServerboundCustomPayloadPacket(CustomPacketPayload payload)) {
            return payload.type().id();
        } else if (packet instanceof ClientboundCustomPayloadPacket(CustomPacketPayload payload)) {
            return payload.type().id();
        } else {
            return packet.type().id();
        }
    }

    public static Object getTruePacket(Packet<?> packet) {
        if (packet instanceof ServerboundCustomPayloadPacket(CustomPacketPayload payload)) {
            return payload;
        } else if (packet instanceof ClientboundCustomPayloadPacket(CustomPacketPayload payload)) {
            return payload;
        } else {
            return packet;
        }
    }
}
