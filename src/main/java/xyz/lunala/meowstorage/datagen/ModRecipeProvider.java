package xyz.lunala.meowstorage.datagen;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.TntBlock;
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
        generateBarrels(pWriter);
        generateBackpacks(pWriter);
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

    protected static void smithingUpgrade(Consumer<FinishedRecipe> recipeOutput, ItemLike template, ItemLike base, ItemLike addition, Item result) {
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(template),
                        Ingredient.of(base),
                        Ingredient.of(addition),
                        RecipeCategory.MISC, result)
                .unlocks("has_" + addition, has(addition))
                .save(recipeOutput, Meowstorage.MODID + ":" + result + "_smithing");
    }

    protected static void chestFromMaterial(Consumer<FinishedRecipe> recipeOutput, ItemLike chest, ItemLike material, ItemLike base, String unlockName) {
        filledRecipe(recipeOutput, RecipeCategory.REDSTONE, chest, material, base, unlockName, chest.toString() + "_from_" + material.toString());
    }

    protected static void generateChests(Consumer<FinishedRecipe> recipeOutput) {
        Tuple<ItemLike, ItemLike>[] items = new Tuple[] {
                new Tuple(ItemInit.COPPER_CHEST_ITEM.get(), Items.COPPER_INGOT),
                new Tuple(ItemInit.IRON_CHEST_ITEM.get(), Items.IRON_INGOT),
                new Tuple(ItemInit.GOLD_CHEST_ITEM.get(), Items.GOLD_INGOT),
                new Tuple(ItemInit.DIAMOND_CHEST_ITEM.get(), Items.DIAMOND),
        };
        ItemLike base = Items.CHEST;

        for (Tuple<ItemLike, ItemLike> pair : items) {
            chestFromMaterial(recipeOutput, pair.getA(), pair.getB(), base, "has_" + pair.getA());
            base = pair.getA();
        }

        smithingUpgrade(recipeOutput, Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, ItemInit.DIAMOND_CHEST_ITEM.get(), Items.NETHERITE_INGOT, ItemInit.NETHERITE_CHEST_ITEM.get());
    }

    protected static void generateBarrels(Consumer<FinishedRecipe> recipeOutput) {
        Tuple<ItemLike, ItemLike>[] items = new Tuple[] {
                new Tuple(ItemInit.COPPER_BARREL_ITEM.get(), Items.COPPER_INGOT),
                new Tuple(ItemInit.IRON_BARREL_ITEM.get(), Items.IRON_INGOT),
                new Tuple(ItemInit.GOLD_BARREL_ITEM.get(), Items.GOLD_INGOT),
                new Tuple(ItemInit.DIAMOND_BARREL_ITEM.get(), Items.DIAMOND),
        };
        ItemLike base = Items.CHEST;

        for (Tuple<ItemLike, ItemLike> pair : items) {
            chestFromMaterial(recipeOutput, pair.getA(), pair.getB(), base, "has_" + pair.getA());
            base = pair.getA();
        }

        smithingUpgrade(recipeOutput, Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, ItemInit.DIAMOND_BARREL_ITEM.get(), Items.NETHERITE_INGOT, ItemInit.NETHERITE_BARREL_ITEM.get());
    }

    protected static void generateBackpacks(Consumer<FinishedRecipe> recipeOutput) {
        // Small Backpack
        backpackFrom(recipeOutput, Items.CHEST.asItem(), Items.LEATHER.asItem(), ItemInit.SMALL_BACKPACK_ITEM.get(), "small_backpack");
        // Mid Backpack
        backpackFrom(recipeOutput, ItemInit.SMALL_BACKPACK_ITEM.get(), ItemInit.COPPER_CHEST_ITEM.get(), ItemInit.MID_BACKPACK_ITEM.get(), "mid_backpack");
        // Big Backpack
        backpackFrom(recipeOutput, ItemInit.MID_BACKPACK_ITEM.get(), ItemInit.IRON_CHEST_ITEM.get(), ItemInit.BIG_BACKPACK_ITEM.get(), "big_backpack");
    }

    protected static void backpackFrom(Consumer<FinishedRecipe> recipeOutput, ItemLike centerPiece, ItemLike upgrade, ItemLike result, String recipeName) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, result)
                .pattern("CDC")
                .pattern("DAD")
                .pattern("DBD")
                .define('B', upgrade)
                .define('A', centerPiece)
                .define('C', Items.STRING)
                .define('D', Items.LEATHER)
                .unlockedBy("has_" + upgrade.asItem().getDefaultInstance().getDisplayName(), has(upgrade))
                .save(recipeOutput, Meowstorage.MODID + ":" + recipeName + "_your_mom");
        ;
    }
}
