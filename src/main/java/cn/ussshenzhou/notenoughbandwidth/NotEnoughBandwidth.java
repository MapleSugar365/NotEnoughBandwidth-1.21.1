package cn.ussshenzhou.notenoughbandwidth;

import cn.ussshenzhou.notenoughbandwidth.config.ConfigHelper;
import cn.ussshenzhou.notenoughbandwidth.stat.ModKey;
import cn.ussshenzhou.notenoughbandwidth.util.ModNetworkRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

/**
 * @author USS_Shenzhou
 */
@Mod(ModConstants.MOD_ID)
public class NotEnoughBandwidth {

    public NotEnoughBandwidth(IEventBus modEventBus) {
        ConfigHelper.loadConfig(new NotEnoughBandwidthConfig());
        modEventBus.addListener(ModNetworkRegistry::networkPacketRegistry);
        modEventBus.addListener(cn.ussshenzhou.network.ModNetworkRegistry::networkPacketRegistry);
        
        if (FMLEnvironment.dist == Dist.CLIENT) {
            modEventBus.addListener(ModKey::onRegisterKey);
            ModKey.register();
        }
    }

}
