package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;

public class Camera {
	
	private float distanceFromPlayer = 90;
	private float angleAroundPlayer = 0;
	
	private Vector3f position = new Vector3f(0,20,800);
	private float pitch;
	private float yaw;
	private float roll;
	
	private Player player;
	
	private Boolean fpv = false;
	private static final float SENSIVITY = 10;
	
	public Camera(Player player){
		this.player = player;
	}
	
	public void move(){
		calculateZoom();
		
		
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		
		if(Keyboard.isKeyDown(Keyboard.KEY_C)) {
			this.fpv = true;
		}
		
		if(fpv) {
			calculatePitchFpv();
			calculateAngleAroundPlayerFpv();
			calculateCameraPosition(horizontalDistance, verticalDistance+4.5f);
		}else {
			calculatePitch();
			calculateAngleAroundPlayer();
			calculateCameraPosition(horizontalDistance, verticalDistance+5.5f);

			//player.increaseRotation(0, (-Mouse.getDX())*SENSIVITY*DisplayManager.getFrameTimeSeconds(), 0);
		}
		
		
		
		
		
		this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public void setRoll(float roll) {
		this.roll = roll;
	}
	
	private void calculateCameraPosition(float horizDistance, float verticDistance) {
		float theta = player.getRotY() + angleAroundPlayer;
		float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ;
		position.y = player.getPosition().y+verticDistance;
	}
	
	private float calculateHorizontalDistance() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	
	private float calculateVerticalDistance() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
	
	private void calculateZoom() {
		float zoomLevel = Mouse.getDWheel() * 0.1f;
		distanceFromPlayer -= zoomLevel;
	}
	
	private void calculatePitch() {
		if(Mouse.isButtonDown(1)) {
			float pitchChange = Mouse.getDY() * 0.1f;
			pitch -= pitchChange;
		}else if(!Mouse.isButtonDown(1)) {
			pitch = 9;
		}
	}
	
	private void calculateAngleAroundPlayer() {
		if(Mouse.isButtonDown(1)) {
			float angleChange = Mouse.getDX() * 0.3f;
			angleAroundPlayer -= angleChange;
		}else if(!Mouse.isButtonDown(1)) {
			angleAroundPlayer = 0;
		}
	}
	
	private void calculatePitchFpv() {
			float pitchChange = Mouse.getDY() * 0.1f;
			pitch -= pitchChange;
		
	}
	
	private void calculateAngleAroundPlayerFpv() {
			float angleChange = Mouse.getDX() * 0.3f;
			angleAroundPlayer -= angleChange;
	}
	

}
