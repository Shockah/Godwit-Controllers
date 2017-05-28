package pl.shockah.godwit.controllers

import com.badlogic.gdx.controllers.PovDirection
import groovy.transform.CompileStatic

@CompileStatic
final class Extensions {
	static PovDirection getOpposite(PovDirection self) {
		switch (self) {
			case PovDirection.north:
				return PovDirection.south
			case PovDirection.south:
				return PovDirection.north
			case PovDirection.east:
				return PovDirection.west
			case PovDirection.west:
				return PovDirection.east
			case PovDirection.northEast:
				return PovDirection.southWest
			case PovDirection.northWest:
				return PovDirection.southEast
			case PovDirection.southEast:
				return PovDirection.northWest
			case PovDirection.southWest:
				return PovDirection.northEast
			default:
				return self
		}
	}

	static Set<PovDirection> getNeighbors(PovDirection self) {
		switch (self) {
			case PovDirection.north:
				return [PovDirection.northEast, PovDirection.northWest] as Set<PovDirection>
			case PovDirection.south:
				return [PovDirection.southWest, PovDirection.southEast] as Set<PovDirection>
			case PovDirection.east:
				return [PovDirection.northEast, PovDirection.southEast] as Set<PovDirection>
			case PovDirection.west:
				return [PovDirection.northWest, PovDirection.southWest] as Set<PovDirection>
			case PovDirection.northEast:
				return [PovDirection.north, PovDirection.east] as Set<PovDirection>
			case PovDirection.northWest:
				return [PovDirection.north, PovDirection.west] as Set<PovDirection>
			case PovDirection.southEast:
				return [PovDirection.south, PovDirection.east] as Set<PovDirection>
			case PovDirection.southWest:
				return [PovDirection.south, PovDirection.west] as Set<PovDirection>
			default:
				throw new IllegalArgumentException()
		}
	}

	static Set<PovDirection> getWithNeighbors(PovDirection self) {
		return getNeighbors(self) + ([self] as Set<PovDirection>)
	}
}