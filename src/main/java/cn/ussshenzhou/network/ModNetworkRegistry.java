package cn.ussshenzhou.network;

import cn.ussshenzhou.notenoughbandwidth.ModConstants;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

/**
 * @author USS_Shenzhou
 */
public class ModNetworkRegistry {

    public static void networkPacketRegistry(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar(ModConstants.MOD_ID);

        registrar.playToServer(StatQuery.TYPE, StreamCodec.ofMember((query, buf) -> {
                }, StatQuery::new),
                StatQuery::handle);
        registrar.playToClient(StatRespond.TYPE, StatRespond.STREAM_CODEC, StatRespond::handle);
    }
}
