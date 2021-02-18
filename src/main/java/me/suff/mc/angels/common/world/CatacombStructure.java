package me.suff.mc.angels.common.world;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import me.suff.mc.angels.util.Constants;
import net.minecraft.entity.EntityType;
import net.minecraft.structure.MarginedStructureStart;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

import java.util.List;

public class CatacombStructure extends StructureFeature< DefaultFeatureConfig > {
    public CatacombStructure(Codec< DefaultFeatureConfig > codec) {
        super(codec);
    }

    @Override
    public StructureStartFactory< DefaultFeatureConfig > getStructureStartFactory() {
        return CatacombStructure.Start::new;
    }

    private static final List< SpawnSettings.SpawnEntry > STRUCTURE_MONSTERS = ImmutableList.of(
            new SpawnSettings.SpawnEntry(EntityType.ILLUSIONER, 100, 4, 9),
            new SpawnSettings.SpawnEntry(EntityType.VINDICATOR, 100, 4, 9)
    );

    @Override
    public List< SpawnSettings.SpawnEntry > getMonsterSpawns() {
        return STRUCTURE_MONSTERS;
    }

    private static final List< SpawnSettings.SpawnEntry > STRUCTURE_CREATURES = ImmutableList.of(
            new SpawnSettings.SpawnEntry(EntityType.SHEEP, 30, 10, 15),
            new SpawnSettings.SpawnEntry(EntityType.RABBIT, 100, 1, 2)
    );

    @Override
    public List< SpawnSettings.SpawnEntry > getCreatureSpawns() {
        return STRUCTURE_CREATURES;
    }

    public static class Start extends MarginedStructureStart< DefaultFeatureConfig > {
        public Start(StructureFeature< DefaultFeatureConfig > structureIn, int chunkX, int chunkZ, BlockBox blockBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, blockBox, referenceIn, seedIn);
        }
        protected static final String[] variants = new String[]{"flat", "clean", "broken", "normal"};

        @Override
        public void init(DynamicRegistryManager dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager structureManager, int chunkX, int chunkZ, Biome biome, DefaultFeatureConfig defaultFeatureConfig) {

            int x = (chunkX << 4) + 7;
            int z = (chunkZ << 4) + 7;
            BlockPos.Mutable blockpos = new BlockPos.Mutable(x, MathHelper.clamp(random.nextInt(55), 33, 55), z);
            String choosen = variants[random.nextInt(variants.length)];
            StructurePoolBasedGenerator.method_30419(dynamicRegistryManager, new StructurePoolFeatureConfig(() -> dynamicRegistryManager.get(Registry.TEMPLATE_POOL_WORLDGEN).get(new Identifier(Constants.MODID,  "catacombs/" + choosen + "/catacomb")), 10),
                    PoolStructurePiece::new,
                    chunkGenerator,
                    structureManager,
                    blockpos, // Position of the structure. Y value is ignored if last parameter is set to true.
                    this.children, // The list that will be populated with the jigsaw pieces after this method.
                    this.random,
                    false, // Special boundary adjustments for villages. It's... hard to explain. Keep this false and make your pieces not be partially intersecting.
                    // Either not intersecting or fully contained will make children pieces spawn just fine. It's easier that way.
                    false); // Place at heightmap (top land). Set this to false for structure to be place at the passed in blockpos's Y value instead.
            // Definitely keep this false when placing structures in the nether as otherwise, heightmap placing will put the structure on the Bedrock roof.
            this.setBoundingBoxFromChildren();
        }

    }
}