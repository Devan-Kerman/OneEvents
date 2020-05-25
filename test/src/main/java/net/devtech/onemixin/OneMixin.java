package net.devtech.onemixin;

import net.devtech.onemixin.scheduler.Scheduler;
import net.fabricmc.api.ModInitializer;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class OneMixin implements ModInitializer {
	@Override
	public void onInitialize() {
		Scheduler scheduler = new Scheduler();
		scheduler.queue(m -> System.out.println("I am a one time task! Tick: " + m.getTicks()), 1);
		scheduler.queue(m -> System.out.println("I am a one time task! Tick: " + m.getTicks()), 0);
		scheduler.repeatN(m -> System.out.println("I will repeat n times! Tick: " + m.getTicks()), 1, 1, 1);
		scheduler.repeatWhile(m -> System.out.println("I will repeat a few times"), i -> i < 15, 5, 3);
	}


}
