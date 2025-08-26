package hogo.erudium.block.compressor;

import hogo.erudium.ErudiumMod;
import hogo.erudium.block.compressor.screen.CompressorMenu;
import hogo.erudium.block.entity.ModBlockEntities;
import hogo.erudium.recipe.CompressorRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class CompressorEntity extends BlockEntity implements MenuProvider {

    public final ItemStackHandler itemStackHandler = new ItemStackHandler(3);
    public static final int INPUT1 = 0;
    public static final int INPUT2 = 1;
    public static final int OUTPUT = 2;
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 300;

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemStackHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    public CompressorEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.COMPRESSOR_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> CompressorEntity.this.progress;
                    case 1 -> CompressorEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> CompressorEntity.this.progress = pValue;
                    case 1 -> CompressorEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }


    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemStackHandler.getSlots());
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, itemStackHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.erudium.compressor");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new CompressorMenu(pContainerId,pPlayerInventory,this,this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {

        pTag.put("inventory", itemStackHandler.serializeNBT());
        pTag.putInt("compressor_progress", progress);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemStackHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("compresor_progress");
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (hasRecipe()) {
            increaseCraftingProgress();
            setChanged(pLevel, pPos, pState);

            if (hasProgressFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void resetProgress() {
        progress = 0;
    }

    private void craftItem() {
        Optional<CompressorRecipe> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return;

        CompressorRecipe r = recipe.get();
        ItemStack result = r.getResultItem(null);

        // Go through each recipe ingredient and match to the correct slot
        for (Map.Entry<Ingredient, Integer> entry : r.getIngredientsMap().entrySet()) {
            Ingredient ingredient = entry.getKey();
            int requiredCount = entry.getValue();

            // Check first input slot
            if (ingredient.test(itemStackHandler.getStackInSlot(INPUT1))) {
                itemStackHandler.extractItem(INPUT1, requiredCount, false);
            }
            // Check second input slot
            else if (ingredient.test(itemStackHandler.getStackInSlot(INPUT2))) {
                itemStackHandler.extractItem(INPUT2, requiredCount, false);
            }
        }

        // Put crafted result into output slot
        itemStackHandler.setStackInSlot(OUTPUT, new ItemStack(
                result.getItem(),
                itemStackHandler.getStackInSlot(OUTPUT).getCount() + result.getCount()
        ));
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    private boolean hasRecipe() {

        Optional<CompressorRecipe> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return false;
        ItemStack result = recipe.get().getResultItem(null);
        return canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private Optional<CompressorRecipe> getCurrentRecipe() {
        // Only use input slots for matching
        SimpleContainer container = new SimpleContainer(2);
        container.setItem(0, this.itemStackHandler.getStackInSlot(INPUT1));
        container.setItem(1, this.itemStackHandler.getStackInSlot(INPUT2));

        return this.level.getRecipeManager().getRecipeFor(CompressorRecipe.Type.INSTANCE, container, level);
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {

        return this.itemStackHandler.getStackInSlot(OUTPUT).isEmpty() || this.itemStackHandler.getStackInSlot(OUTPUT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemStackHandler.getStackInSlot(OUTPUT).getCount() + count <= this.itemStackHandler.getStackInSlot(OUTPUT).getMaxStackSize();
    }
    private boolean canInsertIntoOutputSlot(ItemStack result) {
        ItemStack outputStack = this.itemStackHandler.getStackInSlot(OUTPUT);

        // Empty slot → always OK
        if (outputStack.isEmpty()) {
            return true;
        }

        // If same item, check capacity
        if (outputStack.is(result.getItem())) {
            return outputStack.getCount() + result.getCount() <= outputStack.getMaxStackSize();
        }

        // Different item → can't insert
        return false;
    }
}
