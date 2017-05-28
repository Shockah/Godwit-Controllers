package pl.shockah.godwit.controllers

import groovy.transform.CompileStatic

@CompileStatic
final class ControllerAxisState {
	final ControllerAxis axis
	final float value
	final float minAbsValue
	final float maxAbsValue

	ControllerAxisState(ControllerAxis axis, float value, float minAbsValue, float maxAbsValue) {
		this.axis = axis
		this.value = value
		this.minAbsValue = minAbsValue
		this.maxAbsValue = maxAbsValue
	}
}