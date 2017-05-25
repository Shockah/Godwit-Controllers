package pl.shockah.godwit.controllers

import pl.shockah.godwit.geom.Vec2

final class ControllerAnalogState {
	final ControllerAnalog analog
	final Vec2 value
	final Vec2 minAbsValue
	final Vec2 maxAbsValue
}