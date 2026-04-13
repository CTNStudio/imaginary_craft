package ctn.imaginarycraft.api.epicfight.capabilities;

import org.jetbrains.annotations.ApiStatus;
import yesman.epicfight.api.ex_cap.modules.core.data.MoveSet;

public class ModMoveSet extends MoveSet {
	public ModMoveSet(MoveSetBuilder builder) {
		super(builder);
	}

	public static class MoveSetBuilder extends MoveSet.MoveSetBuilder {
		@ApiStatus.Internal
		public static MoveSetBuilder of(MoveSet.MoveSetBuilder builder) {
			return (MoveSetBuilder) builder;
		}
	}
}
