package hogo.wynn.compressor;

import hogo.wynn.block.ModBlocks;
import hogo.wynn.block.entity.ModBlockEntities;
import hogo.wynn.item.ModItems;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class CompressorEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(10, ItemStack.EMPTY);

    private static final int INPUT1 = 0;
    private static final int INPUT2 = 1;
    private static final int OUTPUT = 2;
    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 300;

    public CompressorEntity( BlockPos pos, BlockState state) {
        super(ModBlockEntities.COMPRESSOR_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index){
                    case 0 -> CompressorEntity.this.progress;
                    case 1 -> CompressorEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0 -> CompressorEntity.this.progress = value;
                    case 1 -> CompressorEntity.this.maxProgress = value;
                };
            }

            @Override
            public int size() {
                return 3;
            }
        };
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Compressor");
    }



    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("compressor.progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("compressor.progress");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new CompressorScreenHandeler(syncId, playerInventory, this, this.propertyDelegate);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if(world.isClient()) {
            return;
        }

        if(isOutputSlotEmptyOrReceivable()) {
            if(this.hasRecipe()) {
                this.increaseCraftProgress();
                markDirty(world, pos, state);

                if(hasCraftingFinished()) {
                    this.craftItem();
                    this.resetProgress();
                }
            } else {
                this.resetProgress();
            }
        } else {
            this.resetProgress();
            markDirty(world, pos, state);
        }
    }

    private boolean hasCraftingFinished() {
        return progress >= maxProgress;
    }

    private void craftItem() {

        if(Ingredient.ofItems(Blocks.SAND).test(this.getStack(0)) && Ingredient.ofItems(ModItems.POKERFISH_EXTRACT).test(this.getStack(1)) && this.getStack(0).getCount() >=16){
            this.removeStack(0,16);
            this.removeStack(1,1);
            this.setStack(OUTPUT, new ItemStack(ModBlocks.POKERFISH_STUFFED_SAND, getStack(OUTPUT).getCount() + 16));

        }
        if(Ingredient.ofItems(ModItems.COFFEE_BEANS).test(this.getStack(0)) && this.getStack(0).getCount() == 64){
            this.removeStack(0,64);
            this.setStack(OUTPUT, new ItemStack(ModBlocks.COFFEE_BEAN_BLOCK, getStack(OUTPUT).getCount() + 1));

        }



    }

    private void resetProgress() {
        this.progress = 0;
    }

    private void increaseCraftProgress() {
        progress++;
    }

    private boolean hasRecipe() {
        if(Ingredient.ofItems(Blocks.SAND).test(this.getStack(0)) && Ingredient.ofItems(ModItems.POKERFISH_EXTRACT).test(this.getStack(1)) && this.getStack(0).getCount() >=16) return true;
        if(Ingredient.ofItems(ModItems.COFFEE_BEANS).test(this.getStack(0)) && this.getStack(0).getCount() == 64) return true;
        return false;
    }


    private boolean canInsertAmountIntoOutputSlot(ItemStack result) {
        return this.getStack(OUTPUT).getCount() + result.getCount() <= getStack(OUTPUT).getMaxCount();
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.getStack(OUTPUT).getItem() == item || this.getStack(OUTPUT).isEmpty();
    }



    private boolean isOutputSlotEmptyOrReceivable() {
        return this.getStack(OUTPUT).isEmpty() || this.getStack(OUTPUT).getCount() < this.getStack(OUTPUT).getMaxCount();
    }
    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
}
