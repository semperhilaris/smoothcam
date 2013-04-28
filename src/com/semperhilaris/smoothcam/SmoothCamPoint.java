
package com.semperhilaris.smoothcam;

/** A point of interest (POI) in a {@link SmoothCamWorld}. A point consists of X/Y coordinates and two radiuses (outer and inner),
 * which are used to move the focus of the camera.
 * @author David Froehlich <semperhilaris@gmail.com> */
public class SmoothCamPoint {
	private float x;
	private float y;
	private float outerRadius = 0;
	private float innerRadius = 0;
	private float radiusDistance = 0;

	public SmoothCamPoint () {

	}

	public SmoothCamPoint (float x, float y, float outerRadius, float innerRadius) {
		setPosition(x, y);
		setOuterRadius(outerRadius);
		setInnerRadius(innerRadius);
	}

	public void setOuterRadius (float radius) {
		outerRadius = radius;
		updateRadiusRatio();
	}

	public void setInnerRadius (float radius) {
		innerRadius = radius;
		updateRadiusRatio();
	}

	public void updateRadiusRatio () {
		if (innerRadius > 0 && outerRadius > 0) {
			radiusDistance = outerRadius - innerRadius;
		}
	}

	public void setPosition (float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX () {
		return x;
	}

	public float getY () {
		return y;
	}

	public float getOuterRadius () {
		return outerRadius;
	}

	public float getInnerRadius () {
		return innerRadius;
	}

	public float getRadiusDistance () {
		return radiusDistance;
	}

}
