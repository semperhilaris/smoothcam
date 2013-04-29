
package com.semperhilaris.smoothcam;

/** Simple Bounding Box for SmoothCam
 * @author David Froehlich <semperhilaris@gmail.com> */
public class SmoothCamBoundingBox {
	public float w;
	public float h;
	public float w2;
	public float h2;

	public SmoothCamBoundingBox (float w, float h) {
		setBounds(w, h);
	}

	public void setBounds (float w, float h) {
		this.w = w;
		this.h = h;
		this.w2 = w / 2;
		this.h2 = h / 2;
	}
}
