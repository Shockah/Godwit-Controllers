package pl.shockah.godwit.controllers.lwjgl3

import com.badlogic.gdx.controllers.Controller
import groovy.transform.CompileStatic

@CompileStatic
class GenericLwjgl3ControllerImplementationProvider implements Lwjgl3ControllerImplementationProvider {
	@Override
	Lwjgl3Controller provide(Controller controller) {
		try {
			println controller.class.name
			if (Class.forName("com.badlogic.gdx.controllers.lwjgl3.Lwjgl3Controller").isInstance(controller))
				return new GenericLwjgl3Controller(controller)
		} catch (Exception e) {
		}
		return null
	}
}