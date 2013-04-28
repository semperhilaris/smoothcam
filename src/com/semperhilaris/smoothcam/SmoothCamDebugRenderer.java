
package com.semperhilaris.smoothcam;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;

/** libGDX-Debug-Renderer for a {@link SmoothCamWorld}.
 * @author David Froehlich <semperhilaris@gmail.com> */
public class SmoothCamDebugRenderer {
	protected ShapeRenderer renderer;

	/** Debug-Renderer for SmoothCamWorld */
	public SmoothCamDebugRenderer () {
		renderer = new ShapeRenderer();
	}

	/**
	 * Renders debug shapes for POIs, the subject and the focus point of a {@link SmoothCamWorld}
	 * @param world
	 * @param projMatrix
	 */
	public void render (SmoothCamWorld world, Matrix4 projMatrix) {
		SmoothCamPoint[] points = world.getPoints();
		renderer.setProjectionMatrix(projMatrix);
		renderer.begin(ShapeType.Circle);
		for (int i = 0; i < points.length; i++) {
			renderer.setColor(0, 1, 0, 1);
			renderer.circle(points[i].getX(), points[i].getY(), points[i].getInnerRadius());
			renderer.setColor(0, 0, 1, 1);
			renderer.circle(points[i].getX(), points[i].getY(), points[i].getOuterRadius());
		}
		renderer.setColor(0, 0, 0, 1);
		SmoothCamSubject subject = world.getSubject();
		renderer.circle(subject.getX(), subject.getY(), subject.getVelocityRadius());
		renderer.end();
		renderer.begin(ShapeType.Line);
		renderer.setColor(0, 0, 0, 1);
		renderer.line(world.getX() + 3f, world.getY(), world.getX() - 3f, world.getY());
		renderer.line(world.getX(), world.getY() + 3f, world.getX(), world.getY() - 3f);
		renderer.end();
	}
}
