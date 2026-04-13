package cn.ussshenzhou.notenoughbandwidth.stat;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;
import net.neoforged.neoforge.common.NeoForge;
import org.lwjgl.glfw.GLFW;

/**
 * @author USS_Shenzhou
 */
@OnlyIn(Dist.CLIENT)
public class ModKey {
    public static final KeyMapping STAT = new KeyMapping(
            "key.neb.stat_screen", KeyConflictContext.UNIVERSAL, KeyModifier.ALT,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_F8, "key.category.neb"
    );

    public static void onRegisterKey(RegisterKeyMappingsEvent event) {
        event.register(STAT);
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (STAT.consumeClick()){
            Minecraft.getInstance().setScreen(new StatScreen());
        }
    }

    public static void register() {
        NeoForge.EVENT_BUS.addListener(ModKey::onKeyInput);
    }
}
