package org.silvercatcher.reforged.items.weapons;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter.Category;
import net.minecraftforge.oredict.ShapedOreRecipe;
import org.silvercatcher.reforged.ReforgedRegistry;
import org.silvercatcher.reforged.api.ExtendedItem;
import org.silvercatcher.reforged.entities.EntityBoomerang;
import org.silvercatcher.reforged.items.recipes.BoomerangEnchRecipe;
import org.silvercatcher.reforged.material.MaterialDefinition;
import org.silvercatcher.reforged.material.MaterialManager;
import org.silvercatcher.reforged.util.Helpers;

public class ItemBoomerang extends ExtendedItem {

    protected final MaterialDefinition materialDefinition;
    protected final boolean unbreakable;

    public ItemBoomerang(ToolMaterial material) {
        this(material, false);
    }

    public ItemBoomerang(ToolMaterial material, boolean unbreakable) {
        super();
        this.unbreakable = unbreakable;
        setMaxStackSize(1);
        materialDefinition = MaterialManager.getMaterialDefinition(material);
        setMaxDamage((int) (materialDefinition.getMaxUses() * 0.8f));
        setUnlocalizedName(materialDefinition.getPrefixedName("boomerang"));
    }

    /**
     * this is weak melee combat damage! for ranged combat damage, see
     * EntityBoomerang#getImpactDamage(Entity)
     */
    @Override
    public float getHitDamage() {

        return Math.max(1f, (0.5f + materialDefinition.getDamageVsEntity() / 2f));
    }

    @Override
    public int getItemEnchantability(ItemStack stack) {
        return materialDefinition.getEnchantability();
    }

    public ToolMaterial getMaterial() {
        return materialDefinition.getMaterial();
    }

    public MaterialDefinition getMaterialDefinition() {
        return materialDefinition;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        if (stack.getItem().isDamageable())
            stack.damageItem(1, attacker);
        return true;
    }

    @Override
    public boolean isDamageable() {
        return !unbreakable;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World worldIn, EntityPlayer playerIn,
                                                    EnumHand hand) {
        if (hand == EnumHand.MAIN_HAND) {
            // import, otherwise references will cause chaos!
            ItemStack throwStack = playerIn.getHeldItemMainhand().copy();
            if (playerIn.capabilities.isCreativeMode || Helpers.consumeInventoryItem(playerIn, this)) {
                Helpers.playSound(worldIn, playerIn, "boomerang_throw", 0.5F, 1.0);
                if (!worldIn.isRemote) {
                    EntityBoomerang boomerang = new EntityBoomerang(worldIn, playerIn, throwStack);
                    worldIn.spawnEntityInWorld(boomerang);
                }
            }
            return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItemMainhand());
        }
        return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItemOffhand());
    }

    @Override
    public void registerRecipes() {

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this), "xww", "  w", "  x", 'x',
                materialDefinition.getOreDictRepairMaterial(), 'w', "stickWood"));
        ReforgedRegistry.registerIRecipe("EnchantBoomerang", new BoomerangEnchRecipe(), BoomerangEnchRecipe.class,
                Category.SHAPELESS);
    }

}
