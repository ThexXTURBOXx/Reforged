package org.silvercatcher.reforged.items.weapons;

import com.google.common.collect.Multimap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.api.IZombieEquippable;
import org.silvercatcher.reforged.api.ItemExtension;
import org.silvercatcher.reforged.material.MaterialDefinition;
import org.silvercatcher.reforged.material.MaterialManager;

public class ItemKatana extends ItemSword implements ItemExtension, IZombieEquippable {

    protected final MaterialDefinition materialDefinition;
    protected final boolean unbreakable;

    public ItemKatana(ToolMaterial material) {
        this(material, false);
    }

    public ItemKatana(ToolMaterial material, boolean unbreakable) {
        super(material);

        this.unbreakable = unbreakable;
        materialDefinition = MaterialManager.getMaterialDefinition(material);

        setUnlocalizedName(materialDefinition.getPrefixedName("katana"));
        setMaxDamage(materialDefinition.getMaxUses());
        setMaxStackSize(1);
        setCreativeTab(ReforgedMod.tabReforged);
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(ItemStack stack) {
        return ItemExtension.super.getAttributeModifiers(stack);
    }

    @Override
    public float getHitDamage() {
        return materialDefinition.getDamageVsEntity() + 4f;
    }

    @Override
    public int getItemEnchantability(ItemStack stack) {
        return materialDefinition.getEnchantability();
    }

    public ToolMaterial getMaterial() {

        return materialDefinition.getMaterial();
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {

        int armorvalue = 0;

        for (int i = 3; i < 6; i++) {

            ItemStack armorStack = target.getItemStackFromSlot(EntityEquipmentSlot.values()[i]);
            if (armorStack != null && armorStack.getItem() instanceof ItemArmor) {
                armorvalue += ((ItemArmor) armorStack.getItem()).damageReduceAmount;
            }
        }

        if (armorvalue < 12) {
            target.hurtResistantTime = 0;
            target.attackEntityFrom(getDamage(attacker), getHitDamage() / 2f);
        }

        if (armorvalue > 6) {
            if (stack.getItem().isDamageable())
                stack.damageItem(2, target);
        }

        if (stack.stackSize >= 1 && stack.getItem().isDamageable())
            stack.damageItem(1, attacker);

        return true;
    }

    @Override
    public boolean isDamageable() {
        return !unbreakable;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
                                    EntityLivingBase entityLiving) {
        if (stack.getItem().isDamageable() && state.getBlockHardness(worldIn, pos) != 0.0D) {
            stack.damageItem(2, entityLiving);
        }
        return true;
    }

    @Override
    public void registerRecipes() {

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this), "  m", " m ", "s  ", 'm',
                materialDefinition.getOreDictRepairMaterial(), 's', "stickWood"));
    }

    @Override
    public float zombieSpawnChance() {
        switch (materialDefinition.getMaterial()) {
        case GOLD:
            return 1;
        case IRON:
            return 2;
        case STONE:
            return 3;
        case WOOD:
            return 4;
        default:
            return 0;
        }
    }
}
