package blackfur.tastytoasters.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

public class ButteredToastItem extends Item {
    public ButteredToastItem() {
        super(new FabricItemSettings()
                .group(ItemGroup.FOOD)
                .food(new FoodComponent.Builder()
                        .hunger(6)
                        .saturationModifier(1.6f)
                        .alwaysEdible()
                        .build()
                )
        );
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add( new TranslatableText("item.tastytoasters.buttered_toast.tooltip").formatted(Formatting.GRAY));
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        for (StatusEffectInstance effect : user.getStatusEffects()) {
            if (!effect.getEffectType().isBeneficial()) {
                user.removeStatusEffect(effect.getEffectType());
            }
        }
        return this.isFood() ? user.eatFood(world, stack) : stack;
    }


}
