package pl.shockah.godwit.controllers

import groovy.transform.CompileStatic
import pl.shockah.godwit.geom.Vec2

@CompileStatic
final class ControllerAnalogState {
	final ControllerAnalog analog
	final Vec2 value
	final Vec2 minAbsValue
	final Vec2 maxAbsValue
}