
package com.semperhilaris.smoothcam;

/** The subject in a {@link SmoothCamWorld}. All camera movements will be calculated relative to this point.
 * @author David Froehlich <semperhilaris@gmail.com> */
public class SmoothCamSubject extends SmoothCamPoint {
	private float velocityX = 0;
	private float velocityY = 0;
	private float velocityRadius = 0;
	private float aimingX = 0;
	private float aimingY = 0;
	private float aimingRadius = 0;

	public SmoothCamSubject () {
		super();
	}

	public void setAimingRadius (float aimingRadius) {
		this.aimingRadius = aimingRadius;
	}

	public void setAiming (float x, float y) {
		if (x > 1) {
			x = 1;
		}
		if (x < -1) {
			x = -1;
		}
		if (y > 1) {
			y = 1;
		}
		if (y < -1) {
			y = -1;
		}
		aimingX = x;
		aimingY = y;
	}

	public void setVelocityRadius (float velocityRadius) {
		this.velocityRadius = velocityRadius;
	}

	/** Sets the horizontal and vertical velocity of the subject. Values should be between -1 and 1, where 0 means no velocity. The
	 * camera will shift inside the velocity radius of the subject relative to it's velocity.
	 * @param x
	 * @param y */
	public void setVelocity (float x, float y) {
		if (x > 1) {
			x = 1;
		}
		if (x < -1) {
			x = -1;
		}
		if (y > 1) {
			y = 1;
		}
		if (y < -1) {
			y = -1;
		}
		velocityX = x;
		velocityY = y;
	}

	public float getAimingRadius () {
		return aimingRadius;
	}

	public float getAimingX () {
		return aimingX;
	}

	public float getAimingY () {
		return aimingY;
	}

	public float getVelocityRadius () {
		return velocityRadius;
	}

	public float getVelocityX () {
		return velocityX;
	}

	public float getVelocityY () {
		return velocityY;
	}
}
