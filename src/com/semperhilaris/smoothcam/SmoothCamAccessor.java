
package com.semperhilaris.smoothcam;

import aurelienribon.tweenengine.TweenAccessor;

/** Exposing tweenable fields of {@link SmoothCamWorld} for Universal Tween Engine by Aurelien Ribon (see
 * http://www.aurelienribon.com/blog/projects/universal-tween-engine/)
 * @author David Froehlich <semperhilaris@gmail.com> */
public class SmoothCamAccessor implements TweenAccessor<SmoothCamWorld> {

	public static final int PAN = 1;
	public static final int ZOOM = 2;

	@Override
	public int getValues (SmoothCamWorld target, int tweenType, float[] returnValues) {
		switch (tweenType) {
		case PAN:
			returnValues[0] = target.getX();
			returnValues[1] = target.getY();
			return 2;
		case ZOOM:
			returnValues[0] = target.getZoom();
			return 1;
		default:
			assert false;
			return -1;
		}
	}

	@Override
	public void setValues (SmoothCamWorld target, int tweenType, float[] newValues) {
		switch (tweenType) {
		case PAN:
			target.setX(newValues[0]);
			target.setY(newValues[1]);
			break;
		case ZOOM:
			target.setZoom(newValues[0]);
			break;
		default:
			assert false;
			break;
		}
	}

}
