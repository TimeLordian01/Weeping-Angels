package me.swirtzly.minecraft.angels.network.messages;

import me.swirtzly.minecraft.angels.utils.ClientUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

/**
 * Created by Craig on 20/01/2019.
 */
public class MessageSFX {

    private ResourceLocation sound;

    public MessageSFX(ResourceLocation sound) {
        this.sound = sound;
    }

    public static void encode(MessageSFX message, PacketBuffer buffer) {
        buffer.writeResourceLocation(message.sound);
    }

    public static MessageSFX decode(PacketBuffer buffer) {
        return new MessageSFX(buffer.readResourceLocation());
    }

    public static class Handler {

        public static void handle(MessageSFX message, Supplier<NetworkEvent.Context> ctx) {
            Minecraft.getInstance().deferTask(() -> ClientUtil.playSound(ForgeRegistries.SOUND_EVENTS.getValue(message.sound), 0.5F));
            ctx.get().setPacketHandled(true);
        }
    }

}
