package xyz.lunala.meowstorage.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.lunala.meowstorage.Meowstorage;
import xyz.lunala.meowstorage.init.BlockInit;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        donutRecipe(pWriter, RecipeCategory.REDSTONE, BlockInit.BIG_CHEST.get(), Items.COPPER_INGOT,"has_copper_ingot", "big_chest_from_copper");
    }

    protected static void fullRecipe(Consumer<FinishedRecipe> recipeOutput, RecipeCategory category, ItemLike result, ItemLike input, String unlockName, String recipeName) {
        ShapedRecipeBuilder.shaped(category, result)
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', input)
                .unlockedBy(unlockName, has(input))
                .save(recipeOutput, Meowstorage.MODID + ":" + recipeName)
        ;
    }

    protected static void donutRecipe(Consumer<FinishedRecipe> recipeOutput, RecipeCategory category, ItemLike result, ItemLike input, String unlockName, String recipeName) {
        ShapedRecipeBuilder.shaped(category, result)
                .pattern("BBB")
                .pattern("B B")
                .pattern("BBB")
                .define('B', input)
                .unlockedBy(unlockName, has(input))
                .save(recipeOutput, Meowstorage.MODID + ":" + recipeName)
        ;
    }

    protected static void filledRecipe(Consumer<FinishedRecipe> recipeOutput, RecipeCategory category, ItemLike result, ItemLike input, ItemLike centerItem, String unlockName, String recipeName) {
        ShapedRecipeBuilder.shaped(category, result)
                .pattern("BBB")
                .pattern("BAB")
                .pattern("BBB")
                .define('B', input)
                .define('A', centerItem)
                .unlockedBy(unlockName, has(input))
                .save(recipeOutput, Meowstorage.MODID + ":" + recipeName)
        ;
    }

    protected static void compressedItemRecipe(Consumer<FinishedRecipe> recipeOutput, RecipeCategory category, ItemLike compressedBlock, ItemLike item, String unlockName, String recipeName) {

        fullRecipe(recipeOutput, category, compressedBlock, item, unlockName, recipeName + "_compressed");

        ShapelessRecipeBuilder.shapeless(category, item, 9)
                .requires(compressedBlock)
                .unlockedBy(recipeName, has(item))
                .save(recipeOutput, Meowstorage.MODID + recipeName + "_un_compressed");
        ;
    }
}
