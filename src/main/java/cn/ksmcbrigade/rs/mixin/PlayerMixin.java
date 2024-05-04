package cn.ksmcbrigade.rs.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.Random;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

    protected PlayerMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Inject(method = "findRespawnPositionAndUseSpawnBlock",at = @At("RETURN"), cancellable = true)
    private static void get(ServerLevel p_36131_, BlockPos p_36132_, float p_36133_, boolean p_36134_, boolean p_36135_, CallbackInfoReturnable<Optional<Vec3>> cir){
        Vec3 ZRet = cir.getReturnValue().isPresent()? cir.getReturnValue().get() : null;
        System.out.println(ZRet!=null);
        if(ZRet!=null){
            BlockPos ret = new BlockPos((int) ZRet.x, (int) ZRet.y, (int) ZRet.z).above();
            while (true){
                System.out.println(ret.getX()+","+ret.getY()+","+ret.getZ()+": "+ p_36131_.getBlockState(ret).getBlock());
                if ((p_36131_.getBlockState(ret).getBlock() instanceof LiquidBlock) || (p_36131_.getBlockState(ret).getBlock() instanceof AbstractCauldronBlock) || (!(p_36131_.getBlockState(ret).getBlock() instanceof BushBlock) && !(p_36131_.getBlockState(ret).getBlock() instanceof AirBlock))) {
                    ret = ret.above().above();
                    ret = rs$gt(ret);
                } else if (p_36131_.getBlockState(ret).getRenderShape().equals(RenderShape.MODEL)) {
                    System.out.println("else");
                    ret = ret.above().above();
                    ret = rs$gt(ret);
                } else{
                    break;
                }
            }
            ret.below();
            cir.setReturnValue(Optional.of(new Vec3(ret.getX(),ret.getY(),ret.getZ())));
        }
    }

    @Unique
    private static BlockPos rs$gt(BlockPos pos){
        return switch (new Random().nextInt(0, 3)) {
            case 0 -> rs$gt2(pos.east());
            case 1 -> rs$gt2(pos.south());
            case 2 -> rs$gt2(pos.west());
            case 3 -> rs$gt2(pos.north());
            default -> pos.east().north();
        };
    }

    @Unique
    private static BlockPos rs$gt2(BlockPos pos){
        return switch (new Random().nextInt(0, 3)) {
            case 0 -> pos.east().east();
            case 1 -> pos.south().south();
            case 2 -> pos.west().west();
            case 3 -> pos.north().north();
            default -> pos.east().north();
        };
    }
}
