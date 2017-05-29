package pl.shockah.godwit.controllers

import com.badlogic.gdx.controllers.PovDirection
import groovy.transform.CompileStatic

@CompileStatic
final class Extensions {
	private static final PovDirection[] CLOCK = [
	        PovDirection.north, PovDirection.northEast, PovDirection.east, PovDirection.southEast,
	        PovDirection.south, PovDirection.southWest, PovDirection.west, PovDirection.northWest
	] as PovDirection[]

	static PovDirection getNextClockwise(PovDirection self) {
		return getNextWithClockwiseOffset(self, 1)
	}

	static PovDirection getNextCounterClockwise(PovDirection self) {
		return getNextWithClockwiseOffset(self, -1)
	}

	private static PovDirection getNextWithClockwiseOffset(PovDirection self, int offset) {
		if (self in CLOCK) {
			int index = CLOCK.findIndexOf { it == self } + offset
			if (index >= CLOCK.length)
				index -= CLOCK.length
			if (index < 0)
				index += CLOCK.length
			return CLOCK[index]
		}
		throw new IllegalArgumentException()
	}

	static PovDirection getOpposite(PovDirection self) {
		if (self == PovDirection.center)
			return self
		return getNextWithClockwiseOffset(self, 2)
	}

	static Set<PovDirection> getNeighbors(PovDirection self) {
		return [getNextCounterClockwise(self), getNextClockwise(self)] as Set<PovDirection>
	}

	static Set<PovDirection> getWithNeighbors(PovDirection self) {
		return getNeighbors(self) + ([self] as Set<PovDirection>)
	}
}