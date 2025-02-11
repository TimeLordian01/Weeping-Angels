package me.suff.mc.angels.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3d;
import me.suff.mc.angels.client.poses.WeepingAngelPose;
import me.suff.mc.angels.common.variants.AbstractVariant;
import me.suff.mc.angels.utils.Pair;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;

public interface IAngelModel {

    boolean toggleHurt(boolean hurtShow);

    ResourceLocation generateTex(WeepingAngelPose pose, AbstractVariant angelVariants);

    ResourceLocation getTextureForPose(Object weepingAngelEntity, WeepingAngelPose pose);

    WeepingAngelPose getAngelPose();

    void setAngelPose(WeepingAngelPose angelPose);

    Pair<ModelPart, Vector3d> getHeadData(HeadPlacement placement);

    Iterable<ModelPart> wings(PoseStack pose);
}
