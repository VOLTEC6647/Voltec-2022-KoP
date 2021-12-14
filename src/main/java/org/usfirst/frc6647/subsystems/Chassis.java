package org.usfirst.frc6647.subsystems;

import java.util.logging.Logger;

import org.usfirst.frc6647.robot.Robot;
import org.usfirst.lib6647.loops.ILooper;
import org.usfirst.lib6647.loops.Loop;
import org.usfirst.lib6647.loops.LoopType;
import org.usfirst.lib6647.oi.JController;
import org.usfirst.lib6647.subsystem.SuperSubsystem;
import org.usfirst.lib6647.subsystem.hypercomponents.HyperVictor;
import org.usfirst.lib6647.subsystem.supercomponents.SuperVictor;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;

/**
 * The {@link Robot}'s {@link Chassis Chassis/Drive} {@link SuperSubsystem}
 * implementation, with arcade drive.
 */
public class Chassis extends SuperSubsystem implements SuperVictor {
	/** Driver1's {@link JController} instance. */
	private final JController joystick;

	/** {@link HyperVictor HyperVictors} used by this {@link Chassis subsystem}. */
	private final HyperVictor frontLeft, frontRight, backLeft, backRight;

	/**
	 * Should only need to create a single of instance of {@link Chassis this
	 * class}; inside the {@link RobotContainer}.
	 */
	public Chassis() {
		super("chassis");

		// All SuperComponents must be initialized like this. The 'robotMap' Object is
		// inherited from the SuperSubsystem class, while the second argument is simply
		// this Subsystem's name.
		initVictors(robotMap, getName());

		// Additional initialiation and configuration.
		joystick = Robot.getInstance().getContainer().getJoystick("driver1");

		frontLeft = getVictor("frontLeft");
		frontRight = getVictor("frontRight");
		backLeft = getVictor("backLeft");
		backRight = getVictor("backRight");
		// ...
	}

	@Override
	public void outputToShuffleboard() {
		try {
			layout.add(frontLeft).withWidget(BuiltInWidgets.kSpeedController);
			layout.add(frontRight).withWidget(BuiltInWidgets.kSpeedController);
			layout.add(backLeft).withWidget(BuiltInWidgets.kSpeedController);
			layout.add(backRight).withWidget(BuiltInWidgets.kSpeedController);
		} catch (NullPointerException e) {
			var error = String.format("[!] COULD NOT OUTPUT SUBSYSTEM '%1$s':%n\t%2$s.", getName(),
					e.getLocalizedMessage());

			Logger.getGlobal().warning(() -> error);
			DriverStation.reportWarning(error, false);
		}
	}

	/**
	 * Use {@link HyperVictor victors} as an arcade drive.
	 * 
	 * @param forward  The drive's forward speed
	 * @param rotation The drive's rotation speed
	 */
	public void arcadeDrive(double forward, double rotation) {
		frontLeft.setWithRamp(forward, -rotation);
		backLeft.setWithRamp(forward, -rotation);
		frontRight.setWithRamp(forward, rotation);
		backRight.setWithRamp(forward, rotation);
	}

	/**
	 * Method to individually feed each of the {@link Chassis}' {@link HyperVictor
	 * victors}, to prevent them from turning off due to idling.
	 */
	public void feedDrive() {
		frontLeft.feed();
		frontRight.feed();
		backLeft.feed();
		backRight.feed();
	}

	/**
	 * Method to individually stop each of the {@link Chassis}' {@link HyperVictor
	 * victors}.
	 */
	public void stopDrive() {
		frontLeft.stopMotor();
		frontRight.stopMotor();
		backLeft.stopMotor();
		backRight.stopMotor();
	}

	@Override
	public void registerLoops(ILooper looper) {
		looper.register(new Loop() { // Arcade drive loop.
			@Override
			public void onFirstStart(double timestamp) {
				// Calibrate gyroscope, reset encoders, etc.
			}

			@Override
			public void onStart(double timestamp) {
				synchronized (Chassis.this) {
					Logger.getGlobal().info(() -> "Started arcade drive at: " + timestamp + "!");
				}
			}

			@Override
			public void onLoop(double timestamp) {
				synchronized (Chassis.this) {
					arcadeDrive(joystick.getY(Hand.kLeft), joystick.getX(Hand.kRight));
					feedDrive();
				}
			}

			@Override
			public void onStop(double timestamp) {
				stopDrive();

				Logger.getGlobal().info(() -> "Stopped arcade drive at: " + timestamp + ".");
			}

			@Override
			public LoopType getType() {
				return LoopType.TELEOP;
			}
		});
	}
}