package hogo.erudium.menus;

import hogo.erudium.ErudiumMod;
import hogo.erudium.block.ModBlocks;
import hogo.erudium.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeMenu {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ErudiumMod.MODID);
    public static final RegistryObject<CreativeModeTab> TAB = CREATIVE_MODE_TABS.register("erudium_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.erudium.erudium")).icon(() -> new ItemStack(ModItems.SMOKED_SAND.get()))
            .displayItems(((itemDisplayParameters, output) -> {
                output.accept(ModItems.SMOKED_SAND.get());
                output.accept(ModItems.COFFEE.get());
                output.accept(ModItems.PAN.get());
                output.accept(ModItems.COFFEE_BEANS.get());
                output.accept(ModItems.POKERFISH_EXTRACT.get());
                output.accept(ModItems.PTD_REMIX_MD.get());
                output.accept(ModBlocks.COFFEE_BEAN_BLOCK.get());
                output.accept(ModBlocks.POKERFISH_STUFFED_SAND.get());
                output.accept(ModItems.VOJTA_SPAWN_EGG.get());
                output.accept(ModItems.HONZA_SPAWN_EGG.get());
                output.accept(ModBlocks.COMPRESSOR.get());
                output.accept(ModItems.Kuroshoten.get());
                output.accept(ModItems.KUROSHOTEN_PROTOTYPE.get());
                output.accept(ModItems.TDD_REMIX_MD.get());
            }))
            .build());

}
