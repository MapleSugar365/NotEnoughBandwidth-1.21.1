package cn.ussshenzhou.notenoughbandwidth.util;

import cn.ussshenzhou.notenoughbandwidth.ModConstants;
import cn.ussshenzhou.notenoughbandwidth.aggregation.PacketAggregationPacket;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;

/**
 * @author USS_Shenzhou
 */
public class ModNetworkRegistry {

    public static void networkPacketRegistry(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar(ModConstants.MOD_ID).executesOn(HandlerThread.NETWORK);

        registrar.playBidirectional(PacketAggregationPacket.TYPE, StreamCodec.ofMember(PacketAggregationPacket::encode, PacketAggregationPacket::new), PacketAggregationPacket::handler);
    }
}
