package org.usfirst.frc.team1389.operation;

import java.util.function.Supplier;

import org.usfirst.frc.team1389.robot.RobotSoftware;
import org.usfirst.frc.team1389.robot.controls.ControlBoard;
import org.usfirst.frc.team1389.systems.BallIntakeSystem;
import org.usfirst.frc.team1389.systems.ClimberSystem;
import org.usfirst.frc.team1389.systems.GearIntakeSystem;
import org.usfirst.frc.team1389.systems.OctoMecanumSystem;
import org.usfirst.frc.team1389.watchers.DebugDash;

//import com.ctre.CANTalon.FeedbackDevice;
import com.team1389.system.Subsystem;
import com.team1389.system.SystemManager;

public class TeleopMain {
	SystemManager manager;
	ControlBoard controls;
	RobotSoftware robot;

	public TeleopMain(RobotSoftware robot) {
		this.robot = robot;
	}

	public void init() {
		controls = ControlBoard.getInstance();
		// GearIntakeSystem gearIntake = setupGearIntake();
		Subsystem drive = setupDrive();
		// Subsystem ballIntake = setUpBallIntake(() -> gearIntake.getState());
		Subsystem climbing = setUpClimbing();
		manager = new SystemManager(drive);
		manager.init();
		DebugDash.getInstance().watch(
				manager.getSystemWatchables().put(robot.armElevator.getAbsoluteIn().getWatchable("absolute pos"),
						robot.pdp.getCurrentIn().getWatchable("total"),
						robot.rearLeft.getPositionInput().getWatchable("Left encoder"),
						robot.rearRight.getPositionInput().getWatchable("Right encoder")));
	}

	private Subsystem setupDrive() {
		return new OctoMecanumSystem(robot.voltageDrive, robot.pistons, robot.gyroInput, controls.driveXAxis(),
				controls.driveYAxis(), controls.driveYaw(), controls.driveTrim(), controls.driveModeBtn(),
				controls.driveModifierBtn());
	}

	// private GearIntakeSystem setupGearIntake() {
	//
	// TeleopGearIntakeSystem Supplier = new TeleopGearIntakeSystem(robot.armAngle, robot.armVel,
	// robot.armElevator.getVoltageOutput(), robot.gearIntake.getVoltageOutput(),
	// robot.gearIntakeCurrent,
	// controls.i_aButton.get(), controls.i_yButton.get(), controls.i_xButton.get(),
	// controls.i_bButton.get(),
	// controls.i_leftVertAxis.get(),
	// robot.armElevator.getSensorTracker(FeedbackDevice.CtreMagEncoder_Absolute).invert());
	// return Supplier;
	// }

	private Subsystem setUpBallIntake(Supplier<GearIntakeSystem.State> state) {
		return new BallIntakeSystem(controls.ballIntakeBtn(), state, robot.ballIntake.getVoltageOutput());
	}

	private ClimberSystem setUpClimbing() {
		return new ClimberSystem(controls.climberThrottle(), robot.climberCurrent, robot.climber.getVoltageOutput());
	}

	public void periodic() {
		manager.update();

	}

}
