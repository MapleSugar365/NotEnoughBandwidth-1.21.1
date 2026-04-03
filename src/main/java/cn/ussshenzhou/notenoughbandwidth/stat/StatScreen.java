package cn.ussshenzhou.notenoughbandwidth.stat;

import cn.ussshenzhou.network.StatQuery;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.PacketDistributor;

import static cn.ussshenzhou.notenoughbandwidth.stat.SimpleStatManager.*;

/**
 * @author USS_Shenzhou
 */
public class StatScreen extends Screen {
    private final String client = "Client";
    private final String actual = "Actual Transmission";
    private String actualC = "";
    private String raw = "Raw Payload";
    private String rawC = "";
    private String ratioC = "";

    private final String server = "Server";
    private String actualS = "-";
    private String rawS = "-";
    private String ratioS = "-";

    private int tick = 0;

    public StatScreen() {
        super(Component.empty());
    }

    @Override
    public void tick() {
        super.tick();
        if (tick % 10 == 0) {
            PacketDistributor.sendToServer(new StatQuery());
            actualC = "↓ Inbound  "
                    + getReadableSpeed((int) LOCAL.inboundSpeedBaked().averageIn1s())
                    + "  Total  "
                    + getReadableSize(LOCAL.inboundBytesBaked().get())
                    + "    ↑ Outbound  "
                    + getReadableSpeed((int) LOCAL.outboundSpeedBaked().averageIn1s())
                    + "  Total  "
                    + getReadableSize(LOCAL.outboundBytesBaked().get());
            rawC = "↓ Inbound  "
                    + getReadableSpeed((int) LOCAL.inboundSpeedRaw().averageIn1s())
                    + "  Total  "
                    + getReadableSize(LOCAL.inboundBytesRaw().get())
                    + "    ↑ Outbound  "
                    + getReadableSpeed((int) LOCAL.outboundSpeedRaw().averageIn1s())
                    + "  Total  "
                    + getReadableSize(LOCAL.outboundBytesRaw().get());
            ratioC = "Ratio                            "
                    + String.format("%.2f", 100d * LOCAL.inboundBytesBaked().get() / LOCAL.inboundBytesRaw().get())
                    + "%                                        "
                    + String.format("%.2f", 100d * LOCAL.outboundBytesBaked().get() / LOCAL.outboundBytesRaw().get())
                    + "%";

            actualS = "↓ Inbound  "
                    + getReadableSpeed((int) inboundSpeedBakedServer)
                    + "  Total  "
                    + getReadableSize(inboundBytesBakedServer)
                    + "    ↑ Outbound  "
                    + getReadableSpeed((int) outboundSpeedBakedServer)
                    + "  Total  "
                    + getReadableSize(outboundBytesBakedServer);
            rawS = "↓ Inbound  "
                    + getReadableSpeed((int) inboundSpeedRawServer)
                    + "  Total  "
                    + getReadableSize(inboundBytesRawServer)
                    + "    ↑ Outbound  "
                    + getReadableSpeed((int) outboundSpeedRawServer)
                    + "  Total  "
                    + getReadableSize(outboundBytesRawServer);
            ratioS = "Ratio                            "
                    + String.format("%.2f", 100d * inboundBytesBakedServer / inboundBytesRawServer)
                    + "%                                        "
                    + String.format("%.2f", 100d * outboundBytesBakedServer / outboundBytesRawServer)
                    + "%";

        }
        tick++;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float a) {
        super.render(graphics, mouseX, mouseY, a);
        graphics.fill(0,0,width,height,0x80000000);
        var font = this.minecraft.font;
        graphics.drawString(font, Component.literal(client), 10, 5, 0xFFFFFF);
        graphics.drawString(font, Component.literal(actual), 10, 30, 0xFFFFFF);
        graphics.drawString(font, Component.literal(actualC), 10, 40, 0xFFFFFF);
        graphics.drawString(font, Component.literal(raw), 10, 60, 0xFFFFFF);
        graphics.drawString(font, Component.literal(rawC), 10, 70, 0xFFFFFF);
        graphics.drawString(font, Component.literal(ratioC), 10, 90, 0xFFFFFF);

        if (hasSufficientPermissions()) {
            graphics.drawString(font, Component.literal(server), 10, 120, 0xFFFFFF);
            graphics.drawString(font, Component.literal(actual), 10, 140, 0xFFFFFF);
            graphics.drawString(font, Component.literal(actualS), 10, 150, 0xFFFFFF);
            graphics.drawString(font, Component.literal(raw), 10, 170, 0xFFFFFF);
            graphics.drawString(font, Component.literal(rawS), 10, 180, 0xFFFFFF);
            graphics.drawString(font, Component.literal(ratioS), 10, 200, 0xFFFFFF);
        }
    }

    private boolean hasSufficientPermissions() {
        if (this.minecraft.player == null) return false;
        return this.minecraft.player.hasPermissions(2);
    }

    private String getReadableSpeed(int bytes) {
        if (bytes < 1000) {
            return bytes + " §7Bytes/S§r";
        } else if (bytes < 1000 * 1000) {
            return String.format("%.1f §7KiB/S§r", bytes / 1024f);
        } else {
            return String.format("%.2f §7MiB/S§r", bytes / (1024 * 1024f));
        }
    }

    private String getReadableSize(long bytes) {
        if (bytes < 1000) {
            return bytes + " §7Bytes§r";
        } else if (bytes < 1000 * 1000) {
            return String.format("%.1f §7KiB§r", bytes / 1024d);
        } else if (bytes < 1000 * 1000 * 1000) {
            return String.format("%.2f §7MiB§r", bytes / (1024 * 1024d));
        } else {
            return String.format("%.2f §7GiB§r", bytes / (1024 * 1024 * 1024d));
        }
    }
}
