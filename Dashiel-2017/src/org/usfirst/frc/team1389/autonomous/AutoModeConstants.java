package org.usfirst.frc.team1389.autonomous;

import org.usfirst.frc.team1389.robot.RobotConstants;

import com.team1389.auto.AutoModeBase;

/**
 * constants for distance to destinations in auton (all in rotations)
 * 
 * @author Quunii
 *
 */
public abstract class AutoModeConstants extends AutoModeBase{
	public static final double SIDE_GEAR_STRAIGHT = 74.0625, SIDE_GEAR_TURN = 60, SIDE_GEAR_APPROACH = 83.6875;
	public static final double ACTIVE_STOP_SHORT = 6;
	public static final double CENTER_GEAR_DIST = 114.3;
	public static final double BASELINE_DIST = 93.3;// TODO find baseline dist in
													// inches;

	public static double getRotations(double distInches) {
		return distInches / (RobotConstants.WheelDiameter * Math.PI);
	}

	public static double getTimeToTravel(double inches, double percentVoltage) {
		double inchesPerSec = RobotConstants.MaxVelocity * percentVoltage;
		return inches / inchesPerSec;
	}
}
