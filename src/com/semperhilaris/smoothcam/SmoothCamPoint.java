
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
	private int polarity = 1;
	public static final int ATTRACT = 1;
	public static final int REPULSE = -1;

	public SmoothCamPoint () {

	}

	/** A Point of Interest in a {@link SmoothCamWorld}
	 * @param x Position X
	 * @param y Position Y
	 * @param outerRadius the distance from the center of the point where the camera starts shifting focus
	 * @param innerRadius the distance from the center of the point where the influence on the camera is in full effect
	 * @param polarity weather the point should attract or repulse the camera */
	public SmoothCamPoint (float x, float y, float outerRadius, float innerRadius, int polarity) {
		setPosition(x, y);
		setOuterRadius(outerRadius);
		setInnerRadius(innerRadius);
		setPolarity(polarity);
	}

	public void setPolarity (int polarity) {
		if (polarity < 0) {
			this.polarity = REPULSE;
		} else {
			this.polarity = ATTRACT;
		}
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

	public int getPolarity () {
		return polarity;
	}
}
