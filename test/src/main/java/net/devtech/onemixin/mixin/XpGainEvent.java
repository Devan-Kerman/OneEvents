package net.devtech.onemixin.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class XpGainEvent {
	@Inject(method = "addExperience", at = @At("HEAD"), cancellable = true)
	private void addExperience(int experience, CallbackInfo ci) {
		if(!this.addExperience(experience))
			ci.cancel();
	}

	/**
	 * called when a player gains experience
	 * @param exp the amount of xp points gained
	 * @return true of the player should gain the experience
	 */
	private boolean addExperience(int exp) {
		return true;
	}
}
