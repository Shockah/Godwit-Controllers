package pl.shockah.godwit.controllers.directinput

import com.badlogic.gdx.controllers.Controller
import groovy.transform.CompileStatic

@CompileStatic
class GenericDirectInputControllerImplementationProvider implements DirectInputControllerImplementationProvider {
	@Override
	DirectInputController provide(Controller controller) {
		try {
			println controller.class.name
			if (Class.forName("com.badlogic.gdx.controllers.lwjgl3.Lwjgl3Controller").isInstance(controller))
				return new GenericDirectInputController(controller)
		} catch (Exception e) {
		}
		return null
	}
}