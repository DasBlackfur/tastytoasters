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
import net.minecraft.util.Rarity;
import net.minecraft.world.World;

import java.util.List;

public class GoldenToastItem extends Item {
    public GoldenToastItem() {
        super(new FabricItemSettings()
                .group(ItemGroup.FOOD)
                .food(new FoodComponent.Builder()
                        .hunger(8)
                        .saturationModifier(2.4f)
                        .statusEffect(new StatusEffectInstance(
                                StatusEffect.byRawId(22),
                                120*20
                        ), 1.0f)
                        .statusEffect(new StatusEffectInstance(
                                StatusEffect.byRawId(10),
                                5*20,
                                2
                                ),
                                1.0f)
                        .build()
                )
                .rarity(Rarity.RARE)
        );
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(new TranslatableText("item.tastytoasters.golden_toast.tooltip").formatted(Formatting.GOLD));
    }
}
