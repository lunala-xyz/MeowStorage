package xyz.lunala.meowstorage.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import xyz.lunala.meowstorage.Meowstorage;
import xyz.lunala.meowstorage.init.BlockInit;
import xyz.lunala.meowstorage.init.ItemInit;

import java.util.Dictionary;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        generateChests(pWriter);
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

    protected static void chestFromMaterial(Consumer<FinishedRecipe> recipeOutput, ItemLike chest, ItemLike material, ItemLike base, String unlockName) {
        filledRecipe(recipeOutput, RecipeCategory.REDSTONE, chest, material, base,unlockName, chest.toString() + "_from_" + material.toString());
    }

    protected static void generateChests(Consumer<FinishedRecipe> recipeOutput) {
        Tuple<ItemLike, ItemLike>[] items = new Tuple[] {
                new Tuple(ItemInit.COPPER_CHEST_ITEM.get(), Items.COPPER_INGOT),
                new Tuple(ItemInit.IRON_CHEST_ITEM.get(), Items.IRON_INGOT),
                new Tuple(ItemInit.GOLD_CHEST_ITEM.get(), Items.GOLD_INGOT),
                new Tuple(ItemInit.DIAMOND_CHEST_ITEM.get(), Items.DIAMOND),
                new Tuple(ItemInit.NETHERITE_CHEST_ITEM.get(), Items.NETHERITE_INGOT),
        };
        ItemLike base = Items.CHEST;

        for (Tuple<ItemLike, ItemLike> pair : items) {
            chestFromMaterial(recipeOutput, pair.getA(), pair.getB(), base, "has_" + pair.getA());
            base = pair.getA();
        }

    }
}
