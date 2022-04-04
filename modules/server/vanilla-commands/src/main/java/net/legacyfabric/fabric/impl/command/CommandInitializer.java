/*
 * Copyright (c) 2020 - 2021 Legacy Fabric
 * Copyright (c) 2016 - 2021 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.legacyfabric.fabric.impl.command;

import net.legacyfabric.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

import net.minecraft.server.command.CommandManager;
import net.minecraft.server.MinecraftServer;

import net.fabricmc.api.ModInitializer;

public class CommandInitializer implements ModInitializer {
	public static MinecraftServer getServerInstance;
	@Override
	public void onInitialize() {
		ServerLifecycleEvents.SERVER_STARTING.register(server -> {
			getServerInstance = server;
			boolean dedicated = server.isDedicated();
			CommandManager manager = (CommandManager) server.getCommandManager();
			CommandRegistryImpl.getCommandMap().forEach((command, commandSide) -> {
				if ((dedicated && commandSide.isDedicated()) || (!dedicated && commandSide.isIntegrated())) {
					manager.registerCommand(command);
				}
			});
		});
	}
}