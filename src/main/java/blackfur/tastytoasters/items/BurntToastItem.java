package blackfur.tastytoasters.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffect;
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

public class BurntToastItem extends Item {
    public BurntToastItem() {
        super(new FabricItemSettings()
                .group(ItemGroup.FOOD)
                .food(new FoodComponent.Builder()
                        .hunger(2)
                        .saturationModifier(0.3f)
                        .statusEffect(new StatusEffectInstance(
                                StatusEffect.byRawId(17),
                                60*20
                        ),1f)
                        .statusEffect(new StatusEffectInstance(
                                StatusEffect.byRawId(19),
                                15*20
                        ),0.9f)
                        .build()
                )
        );
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add( new TranslatableText("item.tastytoasters.burnt_toast.tooltip").formatted(Formatting.GRAY));
    }
}
