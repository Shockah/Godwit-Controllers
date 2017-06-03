package pl.shockah.godwit.controllers.lwjgl3

import com.badlogic.gdx.controllers.Controller
import groovy.transform.CompileStatic

@CompileStatic
class X360Lwjgl3ControllerImplementationProvider implements Lwjgl3ControllerImplementationProvider {
	@Override
	Lwjgl3Controller provide(Controller controller) {
		String osName = System.getProperty("os.name")
		if (osName.startsWith("Windows")) {
			if (controller.name.toLowerCase().contains("xbox"))
				return new X360Lwjgl3WindowsController(controller)
		} else if (osName == "Mac OS X") {
			if (controller.name.contains("360"))
				return new X360Lwjgl3OSXController(controller)
		}
		return null
	}
}