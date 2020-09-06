/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
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

package net.fabricmc.fabric.api.server;

import java.util.stream.Stream;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public final class PlayerStream {
	private PlayerStream() {
	}

	public static Stream<ServerPlayerEntity> all(MinecraftServer server) {
		if (server.getPlayerManager() != null) {
			return server.getPlayerManager().getPlayers().stream();
		} else {
			return Stream.empty();
		}
	}

	public static Stream<PlayerEntity> world(World world) {
		if (!(world instanceof ServerWorld)) {
			throw new RuntimeException("Only supported on ServerWorld!");
		}

		return ((ServerWorld) world).playerEntities.stream();
	}

	public static Stream<PlayerEntity> watching(BlockEntity entity) {
		return watching(entity.getWorld(), entity.getPos());
	}

	public static Stream<PlayerEntity> watching(World world, BlockPos pos) {
		if (!(world instanceof ServerWorld)) {
			throw new RuntimeException("Only supported on ServerWorld!");
		}

		return world(world).filter((player -> ((ServerWorld) world).getChunkManager().method_6005((ServerPlayerEntity) player, pos.getX(), pos.getZ())));
	}

	public static Stream<PlayerEntity> around(World world, Vec3d vector, double radius) {
		double radiusSq = radius * radius;
		return world(world).filter((p) -> p.getDistanceTo(vector.x, vector.y, vector.z) <= radiusSq);
	}

	public static Stream<PlayerEntity> around(World world, BlockPos pos, double radius) {
		double radiusSq = radius * radius;
		return world(world).filter((p) -> p.getDistanceTo(pos.getX(), pos.getY(), pos.getZ()) <= radiusSq);
	}
}
