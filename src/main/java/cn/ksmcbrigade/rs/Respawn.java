package cn.ksmcbrigade.rs;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(Respawn.MOD_ID)
@Mod.EventBusSubscriber(modid = Respawn.MOD_ID)
public class Respawn {

    public static final String MOD_ID = "rs";


    public Respawn() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onDie(LivingDeathEvent event){
        if(event.getEntity() instanceof Player player){
            String x = String.valueOf(Math.round(player.getX()*1000.0D)/1000.0D);
            String y = String.valueOf(Math.round(player.getY()*1000.0D)/1000.0D);
            String z = String.valueOf(Math.round(player.getZ()*1000.0D)/1000.0D);
            Component component = Component.literal(x+", "+y+", "+z).withStyle(ChatFormatting.GREEN).withStyle((p_168608_) -> p_168608_.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp @s "+x+" "+y+" "+z)));
            player.sendSystemMessage(Component.translatable("message.de").append(component));
        }
    }
}
