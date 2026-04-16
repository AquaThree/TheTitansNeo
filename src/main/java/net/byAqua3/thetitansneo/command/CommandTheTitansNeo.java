package net.byAqua3.thetitansneo.command;

import java.util.Collection;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.animation.IAnimatedEntity;
import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;

public class CommandTheTitansNeo {
	
	private static int kill(CommandSourceStack source, Collection<? extends Entity> targets) {
        for (Entity entity : targets) {
            entity.kill();
            
            if (entity instanceof EntityTitan) {
				EntityTitan titan = (EntityTitan) entity;
				titan.setTitanHealth(0.0F);
			}
        }

        if (targets.size() == 1) {
            source.sendSuccess(() -> Component.translatable("commands.kill.success.single", targets.iterator().next().getDisplayName()), true);
        } else {
            source.sendSuccess(() -> Component.translatable("commands.kill.success.multiple", targets.size()), true);
        }
        return targets.size();
    }
	
	private static int getAnimationID(CommandSourceStack source, Collection<? extends Entity> targets) {
        for (Entity entity : targets) {
            if (entity instanceof IAnimatedEntity) {
            	IAnimatedEntity animatedEntity = (IAnimatedEntity) entity;
            	source.sendSuccess(() -> Component.literal(String.format("%s getAnimationID: %s", entity.getDisplayName().getString(), animatedEntity.getAnimationID())), true);
			}
        }
        return targets.size();
    }
	
	private static int setAnimationID(CommandSourceStack source, Collection<? extends Entity> targets, int animationID) {
        for (Entity entity : targets) {
            if (entity instanceof IAnimatedEntity) {
            	IAnimatedEntity animatedEntity = (IAnimatedEntity) entity;
            	animatedEntity.setAnimationID(animationID);
            	source.sendSuccess(() -> Component.literal(String.format("%s setAnimationID: %s", entity.getDisplayName().getString(), animationID)), true);
			}
        }
        return targets.size();
    }
	
	private static int getAnimationTick(CommandSourceStack source, Collection<? extends Entity> targets) {
        for (Entity entity : targets) {
            if (entity instanceof IAnimatedEntity) {
            	IAnimatedEntity animatedEntity = (IAnimatedEntity) entity;
            	source.sendSuccess(() -> Component.literal(String.format("%s getAnimationTick: %s", entity.getDisplayName().getString(), animatedEntity.getAnimationTick())), true);
			}
        }
        return targets.size();
    }

	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal(TheTitansNeo.MODID)
                .requires(sourceStack -> sourceStack.hasPermission(2))
                .then(
                		Commands.literal("kill")
                		.then(Commands.argument("targets", EntityArgument.entities())
                                .executes(context -> kill(context.getSource(), EntityArgument.getEntities(context, "targets")))
                        )
                )
                .then(
                		Commands.literal("animationid")
                		.then(
                				Commands.literal("get")
                				.then(Commands.argument("targets", EntityArgument.entities())
                						.executes(context -> getAnimationID(context.getSource(), EntityArgument.getEntities(context, "targets"))))
                        )
                		.then(
                				Commands.literal("set")
                				.then(Commands.argument("targets", EntityArgument.entities())
                					.then(Commands.argument("animationid", IntegerArgumentType.integer())
                						.executes(context -> setAnimationID(context.getSource(), EntityArgument.getEntities(context, "targets"), IntegerArgumentType.getInteger(context, "animationid")))))
                        )
                )
                .then(
                		Commands.literal("animationtick")
                		.then(
                				Commands.literal("get")
                				.then(Commands.argument("targets", EntityArgument.entities())
                						.executes(context -> getAnimationTick(context.getSource(), EntityArgument.getEntities(context, "targets"))))
                        )
                )
        );
    }
}
