
package com.semperhilaris.smoothcam;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Elastic;
import aurelienribon.tweenengine.equations.Quad;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class SmoothCamTest implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private Body body;
	private SmoothCamSubject player;
	private SmoothCamWorld scw;
	private SmoothCamDebugRenderer scDebug;
	private TweenManager tweenManager;
	private Timeline timeline;
	private float w;
	private float h;
	public boolean isTweening;

	@Override
	public void create () {
		w = Gdx.graphics.getWidth() / 2;
		h = Gdx.graphics.getHeight() / 2;

		isTweening = false;

		debugRenderer = new Box2DDebugRenderer();

		camera = new OrthographicCamera(w, h);
		world = new World(new Vector2(0, 0), true);

		/* Initializing SmoothCam Debug Renderer */
		scDebug = new SmoothCamDebugRenderer();

		/* Creating the subject for the SmoothCamWorld */
		player = new SmoothCamSubject();

		/*
		 * Set the velocity radius for the subject. At max velocity, the camera will shift that much in the direction of the
		 * movement.
		 */
		player.setVelocityRadius(30f);

		/*
		 * Set the aiming radius for the subject.
		 */
		player.setAimingRadius(50f);

		/* Creating the SmoothCamWorld with the subject */
		scw = new SmoothCamWorld(player);

		/* Set the bounding box */
		scw.setBoundingBox(camera.viewportWidth * 0.8f, camera.viewportHeight * 0.8f);

		/* Initialize the TweenAccessor and TweenManager */
		Tween.registerAccessor(SmoothCamWorld.class, new SmoothCamAccessor());
		tweenManager = new TweenManager();

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.fixedRotation = true;
		bodyDef.linearDamping = 1.0f;
		bodyDef.position.set(0, 0);
		body = world.createBody(bodyDef);
		CircleShape circle = new CircleShape();
		circle.setRadius(6f);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 0.5f;
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.6f;
		body.createFixture(fixtureDef);
		circle.dispose();

		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.position.set(new Vector2(0, -50));
		Body groundBody = world.createBody(groundBodyDef);
		PolygonShape groundBox = new PolygonShape();
		groundBox.setAsBox(70f, 5.0f);
		groundBody.createFixture(groundBox, 0.0f);
		groundBox.dispose();

		/* Point of interest #1 */
		SmoothCamPoint testpoi = new SmoothCamPoint();
		testpoi.setPosition(0f, -50f);
		testpoi.setInnerRadius(70f);
		testpoi.setOuterRadius(200f);
		testpoi.setPolarity(SmoothCamPoint.ATTRACT);
		scw.addPoint(testpoi);

		/* Point of interest #2 */
		SmoothCamPoint testpoi2 = new SmoothCamPoint();
		testpoi2.setPosition(500f, 100f);
		testpoi2.setInnerRadius(50f);
		testpoi2.setOuterRadius(250f);
		testpoi2.setPolarity(SmoothCamPoint.ATTRACT);
		testpoi2.setZoom(-0.5f);
		scw.addPoint(testpoi2);

		/* Point of interest #3 */
		SmoothCamPoint testpoi3 = new SmoothCamPoint();
		testpoi3.setPosition(-30f, 400f);
		testpoi3.setInnerRadius(100f);
		testpoi3.setOuterRadius(140f);
		testpoi3.setPolarity(SmoothCamPoint.ATTRACT);
		scw.addPoint(testpoi3);

		/* Point of interest #4 */
		SmoothCamPoint testpoi4 = new SmoothCamPoint();
		testpoi4.setPosition(280f, 400f);
		testpoi4.setInnerRadius(60f);
		testpoi4.setOuterRadius(140f);
		testpoi4.setPolarity(SmoothCamPoint.ATTRACT);
		scw.addPoint(testpoi4);

		/* Point of interest #5 */
		SmoothCamPoint testpoi5 = new SmoothCamPoint();
		testpoi5.setPosition(-500f, 300f);
		testpoi5.setInnerRadius(260f);
		testpoi5.setOuterRadius(340f);
		testpoi5.setPolarity(SmoothCamPoint.ATTRACT);
		testpoi5.setZoom(0.5f);
		scw.addPoint(testpoi5);

		/* Point of interest #6 */
		SmoothCamPoint testpoi6 = new SmoothCamPoint();
		testpoi6.setPosition(-400f, -300f);
		testpoi6.setInnerRadius(160f);
		testpoi6.setOuterRadius(210f);
		testpoi6.setPolarity(SmoothCamPoint.REPULSE);
		scw.addPoint(testpoi6);

		batch = new SpriteBatch();

		/*
		 * start tween with "T"-key
		 */
		Gdx.input.setInputProcessor(new InputAdapter() {
			public boolean keyDown (int key) {
				if (key == Keys.T) {
					isTweening = !isTweening;
					if (isTweening) {
						/*
						 * Example Tween-Sequence: Zoom to 120%, Pan to point of interest #1 (0, -50), Wait 1 second, Pan back to the
						 * starting position, Zoom back to the initial value
						 */
						timeline = Timeline.createSequence()
							.push(Tween.to(scw, SmoothCamAccessor.ZOOM, 0.5f).target(1.2f).ease(Quad.OUT))
							.push(Tween.to(scw, SmoothCamAccessor.PAN, 1.5f).target(0, -50).ease(Elastic.INOUT)).pushPause(1.0f)
							.push(Tween.to(scw, SmoothCamAccessor.PAN, 1.5f).target(scw.getX(), scw.getY()).ease(Elastic.INOUT))
							.push(Tween.to(scw, SmoothCamAccessor.ZOOM, 0.5f).target(scw.getZoom()).ease(Quad.OUT)).start(tweenManager);
					} else {
						tweenManager.killAll();
					}
					return true;
				}
				if (key == Keys.X) {
					if (scw.fixedX) {
						scw.freeFixedX();
					} else {
						scw.setFixedX(scw.getX());
					}
					return true;
				}
				if (key == Keys.Y) {
					if (scw.fixedY) {
						scw.freeFixedY();
					} else {
						scw.setFixedY(scw.getY());
					}
					return true;
				}
				return false;
			}
		});
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	@Override
	public void render () {

		if (isTweening && timeline.isFinished()) {
			isTweening = false;
		}

		if (!isTweening) {
			if (Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer)) {
				float accelX = Gdx.input.getAccelerometerX();
				float accelY = Gdx.input.getAccelerometerY();
				body.applyLinearImpulse(new Vector2(accelY * 30f, accelX * -30f), body.getLocalCenter(), true);
			} else {
				if (Gdx.input.isKeyPressed(Keys.DPAD_LEFT))
					body.applyLinearImpulse(new Vector2(-150f, 0f), body.getLocalCenter(), true);
				if (Gdx.input.isKeyPressed(Keys.DPAD_RIGHT))
					body.applyLinearImpulse(new Vector2(150f, 0f), body.getLocalCenter(), true);
				if (Gdx.input.isKeyPressed(Keys.DPAD_UP))
					body.applyLinearImpulse(new Vector2(0f, 150f), body.getLocalCenter(), true);
				if (Gdx.input.isKeyPressed(Keys.DPAD_DOWN))
					body.applyLinearImpulse(new Vector2(0f, -150f), body.getLocalCenter(), true);

				if (Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.A)) {
					if (Gdx.input.isKeyPressed(Keys.D)) {
						player.setAiming(1, player.getAimingY());
					}
					if (Gdx.input.isKeyPressed(Keys.A)) {
						player.setAiming(-1, player.getAimingY());
					}
				}
				else {
					player.setAiming(0, player.getAimingY());
				}
				if (Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.S)) {
					if (Gdx.input.isKeyPressed(Keys.W)) {
						player.setAiming(player.getAimingX(), 1);
					}
					if (Gdx.input.isKeyPressed(Keys.S)) {
						player.setAiming(player.getAimingX(), -1);
					}
				}
				else {
					player.setAiming(player.getAimingX(), 0);
				}
			}
			/*
			 * Updating the position and velocity of the SmoothCamSubject using Box2D. In this example, maximum velocity of the body
			 * is around 122, so we have to divide by that value to get the relative value between -1 and 1 that we need for
			 * SmoothCamWorld. After that, update the SmoothCamWorld.
			 */
			world.step(1 / 60f, 6, 2);
			player.setPosition(body.getPosition().x, body.getPosition().y);
			player.setVelocity(body.getLinearVelocity().x / 122f, body.getLinearVelocity().y / 122f);
			scw.update();
		} else {
			/*
			 * Updating the Tween-Timeline
			 */
			tweenManager.update(Gdx.graphics.getDeltaTime());
		}

		/*
		 * Center the libGDX camera using the coordinates of the SmoothCamWorld
		 */
		camera.position.set(scw.getX(), scw.getY(), 0);
		camera.viewportWidth = w * scw.getZoom();
		camera.viewportHeight = h * scw.getZoom();
		camera.update();

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		debugRenderer.render(world, camera.combined);

		/* Rendering the debug shapes for the SmoothCamWorld */
		scDebug.render(scw, camera.combined);
	}

	@Override
	public void resize (int width, int height) {
	}

	@Override
	public void pause () {
	}

	@Override
	public void resume () {
	}

}
