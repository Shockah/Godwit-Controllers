package pl.shockah.godwit.controllers.directinput

import com.badlogic.gdx.controllers.Controller
import groovy.transform.CompileStatic

@CompileStatic
class X360DirectInputControllerImplementationProvider implements DirectInputControllerImplementationProvider {
	@Override
	DirectInputController provide(Controller controller) {
		String osName = System.getProperty("os.name")
		if (osName.startsWith("Windows")) {
			if (controller.name.toLowerCase().contains("xbox"))
				return new X360DirectInputWindowsController(controller)
		} else if (osName == "Mac OS X") {
			if (controller.name.contains("360"))
				return new X360DirectInputOSXController(controller)
		}
		return null
	}
}