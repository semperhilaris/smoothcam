# Smooth 2D Game Camera

This is an attempt at implementing a smooth camera movement algorithm for 2D games.
It was inspired by this amazing demonstration of "Insanely Twisted Shadow Planet": http://www.youtube.com/watch?v=aAKwZt3aXQM

## Implemented Features

 - Camera looking ahead based on the movement of the player
 - Moving focus to Points of Interest with inner and outer area of influence
 - Bounding Box to keep the player on screen
 - Repulsive Points to push the camera away
 - Zooming
 - Integration with Universal Tween Engine (http://www.aurelienribon.com/blog/projects/universal-tween-engine/)
 - Player aiming
 - Camera Snapping to one axis

## Possible Future Features

 - Overlapping Points of Interest

## libGDX Demo

See it in action: http://youtu.be/kemOSApi9WU

A demo application and debug renderer for the libGDX framework are included. It uses Box2D to handle collision detection and player movement. Learn more about libGDX here: http://libgdx.badlogicgames.com/
