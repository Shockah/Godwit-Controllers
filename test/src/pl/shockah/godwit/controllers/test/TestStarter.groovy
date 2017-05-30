package pl.shockah.godwit.controllers.test

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.badlogic.gdx.controllers.PovDirection
import com.badlogic.gdx.graphics.Color
import groovy.transform.CompileStatic
import pl.shockah.godwit.Entity
import pl.shockah.godwit.Godwit
import pl.shockah.godwit.GodwitAdapter
import pl.shockah.godwit.State
import pl.shockah.godwit.controllers.*
import pl.shockah.godwit.controllers.directinput.DirectInputControllerProvider
import pl.shockah.godwit.controllers.directinput.X360DirectInputControllerImplementationProvider
import pl.shockah.godwit.geom.Circle
import pl.shockah.godwit.geom.Rectangle
import pl.shockah.godwit.geom.Shape
import pl.shockah.godwit.geom.Vec2
import pl.shockah.godwit.gl.Gfx

@CompileStatic
final class TestStarter extends State {
	static void main(String[] args) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration()
		State state = new TestStarter()
		new Lwjgl3Application(new GodwitAdapter(state), config)
	}

	@Override
	protected void onCreate() {
		super.onCreate()

		DirectInputControllerProvider directInputProvider = new DirectInputControllerProvider()
		directInputProvider.register(new X360DirectInputControllerImplementationProvider())
		Controllers.register(directInputProvider)

		//Controllers.register(new XInputControllerProvider())

		new PlayerEntity(Rectangle.centered(Godwit.instance.gfx.size / 2f, 24f)).create(this)
		new ComponentTesterEntity().create(this)
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

	private static class ComponentTesterEntity extends Entity {
		Controller controller

		@Override
		protected void onUpdate() {
			super.onUpdate()

			if (controller && !controller.connected)
				controller = null
			if (!controller && !Controllers.connectedControllers.isEmpty())
				controller = Controllers.connectedControllers.first()
		}

		@Override
		protected void onRender(Gfx gfx) {
			super.onRender(gfx)

			if (!controller)
				return

			controller.povs.values().eachWithIndex{ ControllerPov entry, int i ->
				Vec2 base = new Vec2(16 + i * 64, 16)

				gfx.color = entry.state.direction in PovDirection.north.getWithNeighbors() ? Color.WHITE : Color.BLACK
				gfx.draw(new Rectangle(base + new Vec2(16, 0), 16, 16), true)
				gfx.color = entry.state.direction in PovDirection.south.getWithNeighbors() ? Color.WHITE : Color.BLACK
				gfx.draw(new Rectangle(base + new Vec2(16, 32), 16, 16), true)
				gfx.color = entry.state.direction in PovDirection.west.getWithNeighbors() ? Color.WHITE : Color.BLACK
				gfx.draw(new Rectangle(base + new Vec2(0, 16), 16, 16), true)
				gfx.color = entry.state.direction in PovDirection.east.getWithNeighbors() ? Color.WHITE : Color.BLACK
				gfx.draw(new Rectangle(base + new Vec2(32, 16), 16, 16), true)
			}

			controller.analogs.values().eachWithIndex{ ControllerAnalog entry, int i ->
				Vec2 base = new Vec2(16 + i * 64, 16 + 64)

				gfx.color = Color.BLACK
				gfx.draw(new Rectangle(base, 48, 48), false)
				gfx.draw(new Circle(base + new Vec2(24, 24) + entry.state.value * 24, 6), true)
			}

			controller.axes.values().eachWithIndex{ ControllerAxis entry, int i ->
				Vec2 base = new Vec2(16 + i * 32, 16 + 128)

				gfx.color = Color.BLACK
				gfx.draw(new Rectangle(base, 16, 48), false)
				gfx.draw(new Circle(base + new Vec2(8, 24 + entry.state.value * 24), 6), true)
			}

			controller.buttons.values().eachWithIndex{ ControllerButton entry, int i ->
				Vec2 base = new Vec2(16 + (i % 4) * 32, 16 + 192 + (i / 4 as int) * 32)

				gfx.color = entry.state.isDown ? Color.WHITE : Color.BLACK
				gfx.draw(new Circle(base + new Vec2(12, 12), 12), true)
			}
		}
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