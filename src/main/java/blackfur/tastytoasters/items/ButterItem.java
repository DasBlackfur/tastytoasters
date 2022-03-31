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

public class ButterItem extends Item {
    public ButterItem() {
        super(new FabricItemSettings()
                .group(ItemGroup.FOOD)
                .food(new FoodComponent.Builder()
                        .hunger(1)
                        .saturationModifier(0.1f)
                        .alwaysEdible()
                        .snack()
                        .build()
                )
        );
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add( new TranslatableText("item.tastytoasters.butter.tooltip.1").formatted(Formatting.GRAY));
        tooltip.add( new TranslatableText("item.tastytoasters.butter.tooltip.2").formatted(Formatting.GRAY));
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        for (StatusEffectInstance effect : user.getStatusEffects()) {
            user.removeStatusEffect(effect.getEffectType());
        }
        return this.isFood() ? user.eatFood(world, stack) : stack;
    }
}
