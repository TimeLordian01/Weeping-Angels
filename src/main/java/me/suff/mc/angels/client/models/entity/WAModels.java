package me.suff.mc.angels.client.models.entity;

import me.suff.mc.angels.WeepingAngels;
import me.suff.mc.angels.client.models.block.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fmlclient.registry.RenderingRegistry;

public class WAModels {

    //Angels
    public static ModelLayerLocation ANGEL_ANGELA = createAngelModelLocation("main");
    public static ModelLayerLocation ANGEL_VILLAGER = createAngelModelLocation("villager");
    public static ModelLayerLocation ANGEL_ED = createAngelModelLocation("ed");
    public static ModelLayerLocation ANGEL_CLASSIC = createAngelModelLocation("a_dizzle");
    public static ModelLayerLocation ANGEL_CHERUB = createAngelModelLocation("cherub");
    //Other
    public static ModelLayerLocation ANOMALY = new ModelLayerLocation(new ResourceLocation(WeepingAngels.MODID, "model"), "anomaly");
    public static ModelLayerLocation COFFIN = new ModelLayerLocation(new ResourceLocation(WeepingAngels.MODID, "model"), "coffin");
    public static ModelLayerLocation POLICE_BOX = new ModelLayerLocation(new ResourceLocation(WeepingAngels.MODID, "model"), "police_box");

    //Snow Angels
    public static ModelLayerLocation SNOW_ANGEL_ARM = new ModelLayerLocation(new ResourceLocation(WeepingAngels.MODID, "model"), "snow_angel_arm");
    public static ModelLayerLocation SNOW_ANGEL_BODY = new ModelLayerLocation(new ResourceLocation(WeepingAngels.MODID, "model"), "snow_angel_body");
    public static ModelLayerLocation SNOW_ANGEL_HEAD = new ModelLayerLocation(new ResourceLocation(WeepingAngels.MODID, "model"), "snow_angel_head");
    public static ModelLayerLocation SNOW_ANGEL_WING = new ModelLayerLocation(new ResourceLocation(WeepingAngels.MODID, "model"), "snow_angel_wing");

    private static ModelLayerLocation createAngelModelLocation(String weeping_angel) {
        return new ModelLayerLocation(new ResourceLocation(WeepingAngels.MODID, "model_" + weeping_angel), weeping_angel);
    }

    public static void init() {
        RenderingRegistry.registerLayerDefinition(ANGEL_ANGELA, ModelAngelaAngel::getModelData);
        RenderingRegistry.registerLayerDefinition(ANGEL_VILLAGER, ModelWeepingVillager::getModelData);
        RenderingRegistry.registerLayerDefinition(ANGEL_CLASSIC, ModelClassicAngel::getModelData);
        RenderingRegistry.registerLayerDefinition(ANGEL_CHERUB, ModelAngelChild::getModelData);
        RenderingRegistry.registerLayerDefinition(ANGEL_ED, ModelAngelEd::getModelData);
        RenderingRegistry.registerLayerDefinition(SNOW_ANGEL_ARM, SnowArmModel::getModelData);
        RenderingRegistry.registerLayerDefinition(SNOW_ANGEL_BODY, SnowBodyModel::getModelData);
        RenderingRegistry.registerLayerDefinition(SNOW_ANGEL_HEAD, SnowHeadModel::getModelData);
        RenderingRegistry.registerLayerDefinition(SNOW_ANGEL_WING, SnowWingsModel::getModelData);
        RenderingRegistry.registerLayerDefinition(COFFIN, CoffinModel::getModelData);
        RenderingRegistry.registerLayerDefinition(ANOMALY, PortalModel::getModelData);
        RenderingRegistry.registerLayerDefinition(POLICE_BOX, PoliceBoxModel::getModelData);

    }

}
