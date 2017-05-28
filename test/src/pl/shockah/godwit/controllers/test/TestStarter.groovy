package pl.shockah.godwit.controllers.test

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.graphics.Color
import groovy.transform.CompileStatic
import pl.shockah.godwit.Entity
import pl.shockah.godwit.Godwit
import pl.shockah.godwit.GodwitAdapter
import pl.shockah.godwit.State
import pl.shockah.godwit.controllers.Controller
import pl.shockah.godwit.controllers.ControllerAnalog
import pl.shockah.godwit.controllers.Controllers
import pl.shockah.godwit.controllers.directinput.DirectInputControllerProvider
import pl.shockah.godwit.controllers.directinput.X360DirectInputControllerImplementationProvider
import pl.shockah.godwit.geom.Rectangle
import pl.shockah.godwit.geom.Shape
import pl.shockah.godwit.gl.Gfx

@CompileStatic
final class TestStarter extends State {
	static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration()
		State state = new TestStarter()
		new LwjglApplication(new GodwitAdapter(state), config)
	}

	@Override
	protected void onCreate() {
		super.onCreate()

		DirectInputControllerProvider directInputProvider = new DirectInputControllerProvider()
		directInputProvider.register(new X360DirectInputControllerImplementationProvider())
		Controllers.register(directInputProvider)

		//Controllers.register(new XInputControllerProvider())

		new PlayerEntity(Rectangle.centered(Godwit.instance.gfx.size / 2f, 24f)).create(this)
	}

	@Override
	protected void onUpdate() {
		Controllers.update {
			super.onUpdate()
		}
	}

	@Override
	protected void onRender(Gfx gfx) {
		gfx.clear(Color.DARK_GRAY)
		super.onRender(gfx)
	}

	private static class PlayerEntity extends Entity {
		Shape shape
		ControllerAnalog analog

		PlayerEntity(Shape shape) {
			this.shape = shape
		}

		@Override
		protected void onUpdate() {
			super.onUpdate()

			if (analog && !analog.controller.connected)
				analog = null
			if (!analog && !Controllers.connectedControllers.isEmpty()) {
				Controller controller = Controllers.connectedControllers.first()
				if (!controller.analogs.isEmpty()) {
					analog = controller.analogs.values().first()
					analog.x.deadzone = 0.15f
					analog.y.deadzone = 0.15f
				}
			}

			if (analog)
				shape.translate(analog.state.value)
		}

		@Override
		protected void onRender(Gfx gfx) {
			super.onRender(gfx)
			gfx.color = analog ? Color.BLACK : Color.GRAY
			gfx.draw(shape, true)
		}
	}
}