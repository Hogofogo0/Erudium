package hogo.erudium.datagen;

import hogo.erudium.ErudiumMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.PoiTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModPoiTypeTagsProvider extends PoiTypeTagsProvider {
    public ModPoiTypeTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> p_256617_, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, p_256617_, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider){
        tag(PoiTypeTags.ACQUIRABLE_JOB_SITE).addOptional(ResourceLocation.fromNamespaceAndPath(ErudiumMod.MODID,"simockova_poi"));
    }
}
