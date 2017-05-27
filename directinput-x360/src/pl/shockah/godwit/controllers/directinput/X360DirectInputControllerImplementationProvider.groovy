package pl.shockah.godwit.controllers.directinput

import com.badlogic.gdx.controllers.Controller
import groovy.transform.CompileStatic

@CompileStatic
class X360DirectInputControllerImplementationProvider implements DirectInputControllerImplementationProvider {
	@Override
	DirectInputController provide(Controller controller) {
		if (controller.name.toLowerCase().split(" +").contains("xbox"))
			return new X360DirectInputController(controller)
		else
			return null
	}
}