package hogo.erudium.rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import hogo.erudium.ErudiumMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import team.lodestar.lodestone.systems.postprocess.PostProcessor;

public class ModPostProcessor extends PostProcessor {

    public static final ModPostProcessor INSTANCE = new ModPostProcessor();

    @Override
    public ResourceLocation getPostChainLocation() {
        return ResourceLocation.fromNamespaceAndPath(ErudiumMod.MODID,"tint_post");
    }

    @Override
    public void beforeProcess(PoseStack viewModelStack) {

    }

    @Override
    public void afterProcess() {

    }
}
