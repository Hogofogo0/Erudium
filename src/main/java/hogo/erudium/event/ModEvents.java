package hogo.erudium.event;

import hogo.erudium.ErudiumMod;
import hogo.erudium.entity.ModVillagers;
import hogo.erudium.item.ModItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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


    private static final Map<UUID, Long> lastCoffeeTime = new HashMap<>();
    private static final Map<UUID, Boolean> atePfx = new HashMap<>();
    private static final Map<UUID, Boolean> hadPoison = new HashMap<>();

    private static final long TIME_WINDOW_MS = 30_000; // 30 sekund

    @SubscribeEvent
    public static void onItemUseFinish(LivingEntityUseItemEvent.Finish event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        ItemStack item = event.getItem();

        // Pokud vypil kávu
        if (item.is(ForgeRegistries.ITEMS.getValue(ResourceLocation.parse("erudium:coffee")))) {
            lastCoffeeTime.put(player.getUUID(), System.currentTimeMillis());
            atePfx.put(player.getUUID(), false);
        }

        // Pokud snědl PFX
        if (item.is(ForgeRegistries.ITEMS.getValue(ResourceLocation.parse("erudium:pfx")))) {
            if (lastCoffeeTime.containsKey(player.getUUID())) {
                long timeSinceCoffee = System.currentTimeMillis() - lastCoffeeTime.get(player.getUUID());
                if (timeSinceCoffee <= TIME_WINDOW_MS) {
                    atePfx.put(player.getUUID(), true);
                } else {
                    lastCoffeeTime.remove(player.getUUID());
                    atePfx.remove(player.getUUID());
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        // Pouštíme jen na serveru
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide) {
            return;
        }

        ServerPlayer player = (ServerPlayer) event.player;

        boolean hasPoison = player.hasEffect(MobEffects.POISON);
        boolean hadPoisonBefore = hadPoison.getOrDefault(player.getUUID(), false);

        // Pokud poison právě zmizel
        if (!hasPoison && hadPoisonBefore) {
            if (atePfx.getOrDefault(player.getUUID(), false)) {
                awardAdvancement(player);
            }
            // Resetujeme
            lastCoffeeTime.remove(player.getUUID());
            atePfx.remove(player.getUUID());
        }

        // Uložíme aktuální stav poisonu
        hadPoison.put(player.getUUID(), hasPoison);
    }

    private static void awardAdvancement(ServerPlayer player) {
        Advancement adv = player.server.getAdvancements().getAdvancement(
                ResourceLocation.fromNamespaceAndPath(ErudiumMod.MODID, "kqarel_mode"));
        if (adv != null) {
            player.getAdvancements().award(adv, "done");
        }
    }





}
