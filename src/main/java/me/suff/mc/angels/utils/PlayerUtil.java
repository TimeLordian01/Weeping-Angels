package me.suff.mc.angels.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.suff.mc.angels.client.renders.Donator;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import static org.apache.http.HttpHeaders.USER_AGENT;

public class PlayerUtil {

    public static boolean isInHand(InteractionHand hand, LivingEntity holder, Item item) {
        ItemStack heldItem = holder.getItemInHand(hand);
        return heldItem.getItem() == item;
    }

    public static boolean isInMainHand(LivingEntity holder, Item item) {
        return isInHand(InteractionHand.MAIN_HAND, holder, item);
    }

    /**
     * Checks if player has item in offhand
     */
    public static boolean isInOffHand(LivingEntity holder, Item item) {
        return isInHand(InteractionHand.OFF_HAND, holder, item);
    }

    /**
     * Checks if player has item in either hand
     */
    public static boolean isInEitherHand(LivingEntity holder, Item item) {
        return isInMainHand(holder, item) || isInOffHand(holder, item);
    }

    // MAIN_HAND xor OFF_HAND
    public static boolean isInOneHand(LivingEntity holder, Item item) {
        boolean mainHand = (isInMainHand(holder, item) && !isInOffHand(holder, item));
        boolean offHand = (isInOffHand(holder, item) && !isInMainHand(holder, item));
        return mainHand || offHand;
    }


    public static void sendMessageToPlayer(Player player, TranslatableComponent textComponent, boolean isHotBar) {
        if (player.level.isClientSide) return;
        player.displayClientMessage(textComponent, isHotBar);
    }

    public static Donator[] getDonators() {
        ArrayList<Donator> donators = new ArrayList<>();
        JsonObject result = null;
        try {
            result = getResponse(new URL("https://api.who-craft.com/get/vips"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] categories = new String[]{"devs", "donators"};

        for (String category : categories) {
            for (JsonElement devs : result.getAsJsonArray(category)) {
                JsonObject dev = devs.getAsJsonObject();
                donators.add(new Donator(dev));
            }
        }

        return donators.toArray(new Donator[0]);
    }

    public static JsonObject getResponse(URL url) throws IOException {
        HttpsURLConnection uc = (HttpsURLConnection) url.openConnection();
        uc.connect();
        uc = (HttpsURLConnection) url.openConnection();
        uc.addRequestProperty("User-Agent", USER_AGENT);
        InputStream inputStream = uc.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        return GsonHelper.parse(br);
    }
}
