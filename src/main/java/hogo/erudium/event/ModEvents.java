package hogo.erudium.event;

import hogo.erudium.ErudiumMod;
import hogo.erudium.entity.ModVillagers;
import hogo.erudium.item.ModItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = ErudiumMod.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void addVTrades(VillagerTradesEvent e){
        if(e.getType() == ModVillagers.SIMOCKOVA.get()){
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = e.getTrades();
            trades.get(1).add(((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.POKERFISH_EXTRACT.get(), 2), new ItemStack(ModItems.COFFEE.get(), 1), 10, 15, 0.075f

            )));
        }
    }

    @SubscribeEvent
    public static void addWTrades(WandererTradesEvent e){
        e.getRareTrades().add(((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.COCOA_BEANS, 64), new ItemStack(ModItems.COFFEE_BEANS.get(), 16), 1,15,0.1f
        )));
    }

}
